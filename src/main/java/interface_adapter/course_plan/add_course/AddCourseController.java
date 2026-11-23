package interface_adapter.course_plan.add_course;

import entity.CourseSession;
import use_case.add_course.AddCourseInputBoundary;
import use_case.add_course.AddCourseInputData;

public class AddCourseController {
    private final AddCourseInputBoundary addCourseInteractor;

    public AddCourseController(AddCourseInputBoundary addCourseInteractor) {
        this.addCourseInteractor = addCourseInteractor;
    }

    public void execute(String courseId, int studyYear, CourseSession session) {
        final AddCourseInputData inputData = new AddCourseInputData(courseId, studyYear, session);
        addCourseInteractor.execute(inputData);
    }
}