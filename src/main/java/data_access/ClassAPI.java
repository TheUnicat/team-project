package data_access;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This year's sections of a course, from the UofT Timetable Builder API (api.easi.utoronto.ca/ttb).
 * Only the terms UofT has currently published are available.
 * Each call makes one HTTP request (about 0.3-0.5 s), so call it from a background thread.
 */
public class ClassAPI {
    private static final String URL = "https://api.easi.utoronto.ca/ttb/getCoursesByCodeAndSectionCode/";
    private static final int NO_RESULTS = 4404;   // UofT's "no results" code, sent with HTTP 404

    /**
     * The course in one term. sectionCode is F (fall), S (winter) or Y (full year).
     */
    public record Offering(String code, String sectionCode, List<String> sessions, List<Section> sections) {
    }

    /**
     * One lecture or tutorial or practical
     */
    public record Section(String name, String type, List<String> instructors,
                          int currentEnrolment, int maxEnrolment, int currentWaitlist,
                          boolean cancelled, List<Meeting> meetings) {
    }

    /**
     * One weekly meeting. building is a code like "BA"
     */
    public record Meeting(DayOfWeek day, LocalTime start, LocalTime end, String building, String sessionCode) {
    }

    private final HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Every current offering of the course
     */
    public List<Offering> getOfferings(String courseCode) {
        String code = URLEncoder.encode(courseCode.trim().toUpperCase(), StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder(URI.create(URL + code))
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();
        JsonNode body;
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            body = mapper.readTree(response.body());
            if (response.statusCode() == 404 && body.path("status").path(0).path("code").asInt() == NO_RESULTS) {
                return List.of();
            }
            if (response.statusCode() != 200) {
                throw new IOException("Timetable answered HTTP " + response.statusCode() + " for " + courseCode);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new UncheckedIOException(new IOException("Request interrupted", e));
        }

        List<Offering> offerings = new ArrayList<>();
        for (JsonNode c : body.path("payload").path("pageableCourse").path("courses")) {
            List<String> sessions = new ArrayList<>();
            c.path("sessions").forEach(s -> sessions.add(s.asText()));
            List<Section> sections = new ArrayList<>();
            c.path("sections").forEach(s -> sections.add(toSection(s)));
            offerings.add(new Offering(c.path("code").asText(), c.path("sectionCode").asText(), sessions, sections));
        }
        return offerings;
    }

    private static Section toSection(JsonNode s) {
        List<String> instructors = new ArrayList<>();
        s.path("instructors").forEach(i ->
                instructors.add((i.path("firstName").asText() + " " + i.path("lastName").asText()).trim()));
        List<Meeting> meetings = new ArrayList<>();
        for (JsonNode m : s.path("meetingTimes")) {
            int day = m.path("start").path("day").asInt();
            if (day < 1 || day > 7) {
                continue;   // no fixed time, e.g. online asynchronous
            }
            meetings.add(new Meeting(
                    DayOfWeek.of(day),   // the API numbers days 1 = Monday ... 7 = Sunday
                    timeOf(m.path("start")),
                    timeOf(m.path("end")),
                    m.path("building").path("buildingCode").asText(""),
                    m.path("sessionCode").asText()));
        }
        return new Section(s.path("name").asText(), s.path("type").asText(), instructors,
                s.path("currentEnrolment").asInt(), s.path("maxEnrolment").asInt(), s.path("currentWaitlist").asInt(),
                "Y".equals(s.path("cancelInd").asText()), meetings);
    }

    /**
     * The API gives times as milliseconds since midnight
     */
    private static LocalTime timeOf(JsonNode t) {
        return LocalTime.ofSecondOfDay(t.path("millisofday").asLong() / 1000);
    }
}