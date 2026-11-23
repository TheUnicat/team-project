package use_case.search_course;

import entity.Course;

import java.util.Optional;

public class SearchCourseInteractor
        implements SearchCourseInputBoundary {

    private final SearchCourseDataAccessInterface dataAccess;
    private final SearchCourseOutputBoundary presenter;

    public SearchCourseInteractor(
            SearchCourseDataAccessInterface dataAccess,
            SearchCourseOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(SearchCourseInputData inputData) {
        final String courseId = inputData.getCourseId()
                .trim()
                .toUpperCase();

        if (courseId.isEmpty()) {
            presenter.prepareFailView("Please enter a course ID.");
            return;
        }

        final Optional<Course> course =
                dataAccess.findCourseById(courseId);

        if (course.isEmpty()) {
            presenter.prepareFailView(
                    "Course " + courseId + " was not found.");
            return;
        }

        presenter.prepareSuccessView(
                new SearchCourseOutputData(
                        course.get().getCourseId()));
    }
}
