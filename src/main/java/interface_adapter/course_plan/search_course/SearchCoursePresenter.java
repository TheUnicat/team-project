package interface_adapter.course_plan.search_course;

import interface_adapter.course_plan.CoursePlanState;
import interface_adapter.course_plan.CoursePlanViewModel;
import use_case.search_course.SearchCourseOutputBoundary;
import use_case.search_course.SearchCourseOutputData;

public class SearchCoursePresenter
        implements SearchCourseOutputBoundary {

    private final CoursePlanViewModel viewModel;

    public SearchCoursePresenter(
            CoursePlanViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(
            SearchCourseOutputData outputData) {
        final CoursePlanState state = viewModel.getState();

        state.setSelectedCourseId(outputData.getCourseId());
        state.setSuccessMessage(
                outputData.getCourseId() + " was found.");
        state.setErrorMessage("");

        viewModel.notifyStateChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final CoursePlanState state = viewModel.getState();

        state.setSelectedCourseId("");
        state.setSuccessMessage("");
        state.setErrorMessage(errorMessage);

        viewModel.notifyStateChanged();
    }
}
