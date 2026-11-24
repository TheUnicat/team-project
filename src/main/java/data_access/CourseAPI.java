package data_access;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Course;
import use_case.search_course.SearchCourseDataAccessInterface;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Courses and programs from UofT (artsci / utsc / utm / engineering .calendar.utoronto.ca).
 * Each method makes one or two HTTP requests (about 0.3-0.8 s each), so call from a background thread.
 */
public class CourseAPI implements SearchCourseDataAccessInterface {
    /** Because this is the maximum size allowed by the api */
    public static final int PAGE_SIZE = 50;

    /** Four campuses */
    public enum Campus {
        ARTS_AND_SCIENCE("artsci", "body", "body", "field_completion_requirements"),
        SCARBOROUGH("utsc", "body", "body", "field_completion_requirements"),
        MISSISSAUGA("utm", "field_desc", "field_description", "field_completion_req"),
        ENGINEERING("engineering", "field_desc", "field_description", "field_completion_req");

        private final String host;
        private final String courseDescriptionField;
        private final String programDescriptionField;
        private final String completionField;

        Campus(String host, String courseDescriptionField, String programDescriptionField, String completionField) {
            this.host = host;
            this.courseDescriptionField = courseDescriptionField;
            this.programDescriptionField = programDescriptionField;
            this.completionField = completionField;
        }
    }

    /**
     * Only code and name are filled in search results.
     * Stays a record until the calendar's requirement text is turned into entity.Requirement objects.
     */
    public static final class ProgramInfo {
        private final String code;
        private final String name;
        private final String description;
        private final String enrolmentRequirements;
        private final String completionRequirements;

        public ProgramInfo(String code, String name, String description,
                           String enrolmentRequirements, String completionRequirements) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.enrolmentRequirements = enrolmentRequirements;
            this.completionRequirements = completionRequirements;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getEnrolmentRequirements() {
            return enrolmentRequirements;
        }

        public String getCompletionRequirements() {
            return completionRequirements;
        }
    }

    private final HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
    private final ObjectMapper mapper = new ObjectMapper();

    // ------------------------------------------------------------------ courses

    /** One course with its description, e.g. "CSC148H1". Empty if no calendar has it. */
    @Override
    public Optional<Course> findCourseById(String courseId) {
        String c = courseId.trim().toUpperCase();
        for (Campus campus : campusesForCourse(c)) {
            JsonNode data = get(campus, "courses", Map.of("filter[title]", c)).path("data");
            if (!data.isEmpty()) {
                return Optional.of(toCourse(data.get(0), campus));
            }
        }
        return Optional.empty();
    }

    /**
     * Courses whose code starts with the query ("CSC", "CSC1") or title contains it ("calculus"),
     * sorted by code, without descriptions. page starts at 0.
     */
    public List<Course> searchCourses(Campus campus, String query, int page) {
        Map<String, String> q = new LinkedHashMap<>();
        String text = query.trim();
        if (!text.isEmpty()) {
            q.put("filter[any][group][conjunction]", "OR");
            q.put("filter[code][condition][path]", "title");
            q.put("filter[code][condition][operator]", "STARTS_WITH");
            q.put("filter[code][condition][value]", text.toUpperCase());
            q.put("filter[code][condition][memberOf]", "any");
            q.put("filter[name][condition][path]", "field_course_title.value");
            q.put("filter[name][condition][operator]", "CONTAINS");
            q.put("filter[name][condition][value]", text);
            q.put("filter[name][condition][memberOf]", "any");
        }
        q.put("fields[node--courses]", "title,field_course_title,field_prerequisite,field_corequisite");
        q.put("sort", "title");
        q.put("page[offset]", String.valueOf(page * PAGE_SIZE));
        q.put("page[limit]", String.valueOf(PAGE_SIZE));

        List<Course> courses = new ArrayList<>();
        for (JsonNode item : get(campus, "courses", q).path("data")) {
            courses.add(toCourse(item, campus));
        }
        return courses;
    }

    // ------------------------------------------------------------------ programs

    /** Programs whose name contains the query */
    public List<ProgramInfo> searchPrograms(Campus campus, String query, int page) {
        Map<String, String> q = new LinkedHashMap<>();
        if (!query.isBlank()) {
            q.put("filter[name][condition][path]", "title");
            q.put("filter[name][condition][operator]", "CONTAINS");
            q.put("filter[name][condition][value]", query.trim());
        }
        q.put("fields[node--programs]", "title,field_post_code");
        q.put("sort", "title");
        q.put("page[offset]", String.valueOf(page * PAGE_SIZE));
        q.put("page[limit]", String.valueOf(PAGE_SIZE));

        List<ProgramInfo> programs = new ArrayList<>();
        for (JsonNode item : get(campus, "programs", q).path("data")) {
            programs.add(toProgram(item, campus));
        }
        return programs;
    }

    /** One program with requirements */
    public Optional<ProgramInfo> getProgram(String code) {
        String c = code.trim().toUpperCase();
        Campus campus = campusForProgram(c);
        JsonNode data = get(campus, "programs", Map.of("filter[field_post_code]", c)).path("data");
        return data.isEmpty() ? Optional.empty() : Optional.of(toProgram(data.get(0), campus));
    }

    // ------------------------------------------------------------------ converting calendar data

    /** Builds a Course. The calendar does not store year, credits or full-year; they come from the code. */
    private static Course toCourse(JsonNode item, Campus campus) {
        String code = attr(item, "title");
        String prerequisite = attr(item, "field_prerequisite");
        String corequisite = attr(item, "field_corequisite");
        boolean fullYear = code.length() > 6 && code.charAt(6) == 'Y';   // CSC148H1 = half year, MAT133Y1 = full year
        return new Course(
                code,
                attr(item, "field_course_title"),
                yearOf(code),
                fullYear,
                fullYear ? 1.0 : 0.5,
                stripTags(prerequisite),
                courseCodesIn(prerequisite),
                stripTags(corequisite),
                courseCodesIn(corequisite),
                stripTags(attr(item, campus.courseDescriptionField)));
    }

    /**
     * The year level is the code's 4th character: a digit at St. George and Mississauga (CSC148H1 is 1st year),
     * a letter at Scarborough (CSCA48H3: A = 1st year ... D = 4th year).
     */
    private static int yearOf(String code) {
        if (code.length() < 4) {
            return 0;
        }
        char c = code.charAt(3);
        if (Character.isDigit(c)) {
            return Math.max(1, c - '0');
        }
        if (c >= 'A' && c <= 'D') {
            return c - 'A' + 1;
        }
        return 0;
    }

    private static final Pattern COURSE_CODE = Pattern.compile("\\b[A-Z]{3}[A-Z0-9]\\d{2}[HY]\\d\\b");

    /** Course codes mentioned in a requirement field, in order, without repeats, e.g. [CSC108H1]. */
    private static List<String> courseCodesIn(String html) {
        LinkedHashSet<String> codes = new LinkedHashSet<>();
        Matcher m = COURSE_CODE.matcher(html.replaceAll("<[^>]+>", " "));
        while (m.find()) {
            codes.add(m.group());
        }
        return new ArrayList<>(codes);
    }

    /** The calendar's HTML without tags, e.g. "<a href='/course/CSC108H1'>CSC108H1</a>/ ..." becomes "CSC108H1/ ...". */
    private static String stripTags(String html) {
        return html.replaceAll("<[^>]+>", "")
                .replace("&nbsp;", " ").replace("&#8203;", "").replace("&amp;", "&")
                .trim();
    }

    private static ProgramInfo toProgram(JsonNode item, Campus campus) {
        String code = attr(item, "field_post_code");
        return new ProgramInfo(
                code,
                programName(attr(item, "title"), code),
                attr(item, campus.programDescriptionField),
                attr(item, "field_enrolment_requirements"),
                attr(item, campus.completionField));
    }

    /** Calendar titles end with the code, e.g. "Greek Minor (Arts Program) - ASMIN2123"; remove it. */
    private static String programName(String title, String code) {
        for (String suffix : new String[]{" - " + code, " (" + code + ")"}) {
            if (!code.isEmpty() && title.endsWith(suffix)) {
                return title.substring(0, title.length() - suffix.length()).trim();
            }
        }
        return title;
    }

    // ------------------------------------------------------------------ choosing the calendar

    /** Course codes end in 3 at Scarborough, 5 at Mississauga, 1 at St. George (Arts & Science or Engineering). */
    private static List<Campus> campusesForCourse(String code) {
        if (code.endsWith("3")) {
            return List.of(Campus.SCARBOROUGH);
        }
        if (code.endsWith("5")) {
            return List.of(Campus.MISSISSAUGA);
        }
        return List.of(Campus.ARTS_AND_SCIENCE, Campus.ENGINEERING);
    }

    /** Program codes start with AS (Arts & Science), SC (Scarborough), ER (Mississauga) or AE (Engineering). */
    private static Campus campusForProgram(String code) {
        if (code.startsWith("SC")) {
            return Campus.SCARBOROUGH;
        }
        if (code.startsWith("ER")) {
            return Campus.MISSISSAUGA;
        }
        if (code.startsWith("AE")) {
            return Campus.ENGINEERING;
        }
        return Campus.ARTS_AND_SCIENCE;
    }

    // ------------------------------------------------------------------ HTTP and JSON

    /**
     * One attribute as text. The calendars store text as {"value": "..."}, a plain string,
     * a list of strings, null, or the string "null".
     */
    private static String attr(JsonNode item, String field) {
        JsonNode v = item.path("attributes").path(field);
        if (v.isObject()) {
            v = v.path("value");
        }
        if (v.isArray()) {
            List<String> parts = new ArrayList<>();
            v.forEach(x -> parts.add(x.asText()));
            return String.join(", ", parts);
        }
        if (!v.isTextual() || v.asText().equals("null")) {
            return "";
        }
        return v.asText().trim();
    }

    private JsonNode get(Campus campus, String type, Map<String, String> query) {
        String url = "https://" + campus.host + ".calendar.utoronto.ca/jsonapi/node/" + type + "?" + encode(query);
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .header("Accept", "application/vnd.api+json")
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException("Calendar answered HTTP " + response.statusCode() + " for " + url);
            }
            return mapper.readTree(response.body());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new UncheckedIOException(new IOException("Request interrupted", e));
        }
    }

    private static String encode(Map<String, String> query) {
        return query.entrySet().stream()
                .map(e -> URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8) + "="
                        + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }
}