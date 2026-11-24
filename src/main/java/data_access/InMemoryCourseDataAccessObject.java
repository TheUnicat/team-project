package data_access;

import entity.Course;
import entity.CoursePlan;
import entity.PlannedCourse;
import use_case.add_course.AddCourseDataAccessInterface;
import use_case.search_course.SearchCourseDataAccessInterface;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory storage for the course catalog and course plan.
 * This implementation does not persist data between program runs.
 */
public class InMemoryCourseDataAccessObject
        implements AddCourseDataAccessInterface,
        SearchCourseDataAccessInterface {

    private final CoursePlan coursePlan;
    private final Map<String, Course> courses;

    public InMemoryCourseDataAccessObject() {
        this.coursePlan = new CoursePlan();
        this.courses = new HashMap<>();

        addCourse(new Course("CSC207", 2, false));
        addCourse(new Course("CSC209", 2, false));
        addCourse(new Course("CSC263", 2, false));
        addCourse(new Course("CSC301", 3, false));
    }

    private void addCourse(Course course) {
        courses.put(course.getCourseId(), course);
    }

    @Override
    public Optional<Course> findCourseById(String courseId) {
        return Optional.ofNullable(
                courses.get(
                        courseId.toUpperCase(Locale.ROOT)));
    }

    @Override
    public void save(PlannedCourse plannedCourse) {
        coursePlan.addCourse(plannedCourse);
    }

    public CoursePlan getCoursePlan() {
        return coursePlan;
    }
}
