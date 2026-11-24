package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * A UofT course, e.g. CSC148H1.
 * Prerequisites and corequisites are stored as course codes (for checking) plus the calendar's
 * original sentence (for display), because the sentence also says how the codes combine,
 * e.g. "CSC111H1/ CSC148H1" means either one is enough.
 */
public class Course {
    private final String courseId;
    private final String title;
    private final int year;
    private final boolean isFullYear;
    private final double credits;
    private final String prerequisiteText;
    private final List<String> prerequisites;
    private final String corequisiteText;
    private final List<String> corequisites;
    private final String description;

    public Course(String courseId, String title, int year, boolean isFullYear, double credits,
                  String prerequisiteText, List<String> prerequisites,
                  String corequisiteText, List<String> corequisites,
                  String description) {
        this.courseId = courseId;
        this.title = title;
        this.year = year;
        this.isFullYear = isFullYear;
        this.credits = credits;
        this.prerequisiteText = prerequisiteText;
        this.prerequisites = new ArrayList<>(prerequisites);
        this.corequisiteText = corequisiteText;
        this.corequisites = new ArrayList<>(corequisites);
        this.description = description;
    }

    /** Short form for test data: no title, requisites or description. */
    public Course(String courseId, int year, boolean isFullYear, double credits) {
        this(courseId, "", year, isFullYear, credits, "", List.of(), "", List.of(), "");
    }

    public String getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public boolean getIsFullYear() {
        return isFullYear;
    }

    public double getCredits() {
        return credits;
    }

    /** The calendar's prerequisite sentence, e.g. "CSC108H1/ (equivalent programming experience)". */
    public String getPrerequisiteText() {
        return prerequisiteText;
    }

    /** Course codes named in the prerequisites, e.g. [CSC108H1]. */
    public List<String> getPrerequisites() {
        return new ArrayList<>(prerequisites);
    }

    public String getCorequisiteText() {
        return corequisiteText;
    }

    public List<String> getCorequisites() {
        return new ArrayList<>(corequisites);
    }

    /** "" when the course came from a search list; filled when looked up by its code. */
    public String getDescription() {
        return description;
    }
}