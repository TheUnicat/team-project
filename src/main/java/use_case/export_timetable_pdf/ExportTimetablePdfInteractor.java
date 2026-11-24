package use_case.export_timetable_pdf;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ExportTimetablePdfInteractor {
    private final TimetablePdfGateway pdfGateway;

    public ExportTimetablePdfInteractor(TimetablePdfGateway pdfGateway) {
        this.pdfGateway = pdfGateway;
    }

    public void execute(Path destination, List<String> plannedCourses) throws IOException {
        pdfGateway.save(destination, plannedCourses);
    }
}
