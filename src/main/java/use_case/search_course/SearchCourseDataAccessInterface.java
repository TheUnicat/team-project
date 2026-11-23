package use_case.search_course;

import entity.Course;

import java.util.Optional;

public interface SearchCourseDataAccessInterface {
    Optional<Course> findCourseById(String courseId);
}
