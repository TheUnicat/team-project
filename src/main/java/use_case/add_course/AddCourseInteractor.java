package use_case.add_course;

import entity.PlannedCourse;

public class AddCourseInteractor
        implements AddCourseInputBoundary {

    private final AddCourseDataAccessInterface dataAccess;
    private final AddCourseOutputBoundary presenter;

    public AddCourseInteractor(
            AddCourseDataAccessInterface dataAccess,
            AddCourseOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(AddCourseInputData inputData) {

        final PlannedCourse plannedCourse =
                new PlannedCourse(
                        inputData.getCourseId(),
                        inputData.getStudyYear(),
                        inputData.getSession());

        dataAccess.save(plannedCourse);

        final AddCourseOutputData outputData =
                new AddCourseOutputData(
                        inputData.getCourseId(),
                        inputData.getStudyYear(),
                        inputData.getSession());

        presenter.prepareSuccessView(outputData);
    }
}