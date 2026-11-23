package use_case.search_course;

public class SearchCourseInputData {
    private final String courseId;

    public SearchCourseInputData(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }
}
