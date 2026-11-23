package use_case.search_course;

public class SearchCourseOutputData {
    private final String courseId;

    public SearchCourseOutputData(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }
}
