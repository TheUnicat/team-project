package use_case.add_course;

import entity.PlannedCourse;

public interface AddCourseDataAccessInterface {
    void save(PlannedCourse plannedCourse);
}