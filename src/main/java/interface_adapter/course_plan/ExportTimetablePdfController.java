package interface_adapter.course_plan;

import use_case.export_timetable_pdf.ExportTimetablePdfInteractor;

import java.io.IOException;
import java.nio.file.Path;

public class ExportTimetablePdfController {
    private final ExportTimetablePdfInteractor interactor;
    private final CoursePlanViewModel viewModel;

    public ExportTimetablePdfController(
            ExportTimetablePdfInteractor interactor, CoursePlanViewModel viewModel) {
        this.interactor = interactor;
        this.viewModel = viewModel;
    }

    public void execute(Path destination) throws IOException {
        interactor.execute(destination, viewModel.getState().getPlannedCourses());
    }
}
