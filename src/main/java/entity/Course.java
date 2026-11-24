package entity;
import java.util.*;

public class Course {
    private final String courseId;
    private final int year;
    private final boolean isFullYear;
    private List<Course> prerequisites;
    private List<Course> corequisites;

    public Course(String courseId, int year, boolean isFullYear) {
        this.courseId = courseId;
        this.year = year;
        this.isFullYear = isFullYear;
    }

    public void addPrerequisite(Course prereq) {
        this.prerequisites.add(prereq);
    }

    public void addCorequisite(Course coreq) {
        this.corequisites.add(coreq);
    }

    public int getYear() {
        return year;
    }

    public boolean getIsFullYear() { return isFullYear; }

    public String getCourseId() {
        return courseId;
    }

    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    public List<Course> getCorequisites() {
        return corequisites;
    }
}
