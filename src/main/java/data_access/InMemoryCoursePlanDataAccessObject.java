package data_access;

import entity.CoursePlan;
import entity.PlannedCourse;
import use_case.add_course.AddCourseDataAccessInterface;

public class InMemoryCoursePlanDataAccessObject
        implements AddCourseDataAccessInterface {

    private final CoursePlan coursePlan;

    public InMemoryCoursePlanDataAccessObject() {
        this.coursePlan = new CoursePlan();
    }

    @Override
    public void save(PlannedCourse plannedCourse) {
        coursePlan.addCourse(plannedCourse);
    }

    public CoursePlan getCoursePlan() {
        return coursePlan;
    }
}