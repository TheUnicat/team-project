package use_case.search_course;

public interface SearchCourseOutputBoundary {
    void prepareSuccessView(SearchCourseOutputData outputData);

    void prepareFailView(String errorMessage);
}
