package interface_adapter.course_plan.search_course;

import use_case.search_course.SearchCourseInputBoundary;
import use_case.search_course.SearchCourseInputData;

public class SearchCourseController {
    private final SearchCourseInputBoundary interactor;

    public SearchCourseController(
            SearchCourseInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String courseId) {
        interactor.execute(
                new SearchCourseInputData(courseId));
    }
}
