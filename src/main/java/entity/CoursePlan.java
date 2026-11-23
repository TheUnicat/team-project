package entity;

import java.util.ArrayList;
import java.util.List;

public class CoursePlan {
    private final List<PlannedCourse> plannedCourses;

    public CoursePlan() {
        this.plannedCourses = new ArrayList<>();
    }

    public void addCourse(PlannedCourse plannedCourse) {
        plannedCourses.add(plannedCourse);
    }

    public void removeCourse(String courseId) {
        plannedCourses.removeIf(
                course -> course.getCourseId()
                        .equals(courseId));
    }

    public boolean containsCourse(String courseId) {
        for (PlannedCourse course : plannedCourses) {
            if (course.getCourseId().equals(courseId)) {
                return true;
            }
        }

        return false;
    }

    public List<PlannedCourse> getPlannedCourses() {
        return new ArrayList<>(plannedCourses);
    }
}