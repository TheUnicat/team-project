package interface_adapter.course_plan.add_course;

import interface_adapter.course_plan.CoursePlanState;
import interface_adapter.course_plan.CoursePlanViewModel;
import use_case.add_course.AddCourseOutputBoundary;
import use_case.add_course.AddCourseOutputData;

public class AddCoursePresenter
        implements AddCourseOutputBoundary {

    private final CoursePlanViewModel viewModel;

    public AddCoursePresenter(
            CoursePlanViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(
            AddCourseOutputData outputData) {
        final CoursePlanState state =
                viewModel.getState();

        final String displayText =
                outputData.getCourseId()
                        + " | Year "
                        + outputData.getStudyYear()
                        + " | "
                        + outputData.getSession();

        state.addPlannedCourse(displayText);
        state.setSuccessMessage(
                outputData.getCourseId()
                        + " was added successfully.");
        state.setErrorMessage("");

        viewModel.notifyStateChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final CoursePlanState state =
                viewModel.getState();

        state.setSuccessMessage("");
        state.setErrorMessage(errorMessage);

        viewModel.notifyStateChanged();
    }
}
