package use_case.add_course;

import entity.CourseSession;

public class AddCourseInputData {
    private final String courseId;
    private final int studyYear;
    private final CourseSession session;

    public AddCourseInputData(
            String courseId,
            int studyYear,
            CourseSession session) {
        this.courseId = courseId;
        this.studyYear = studyYear;
        this.session = session;
    }

    public String getCourseId() {
        return courseId;
    }

    public int getStudyYear() {
        return studyYear;
    }

    public CourseSession getSession() {
        return session;
    }
}