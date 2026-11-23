package use_case.add_course;

public interface AddCourseOutputBoundary {

    void prepareSuccessView(AddCourseOutputData outputData);

    void prepareFailView(String errorMessage);
}