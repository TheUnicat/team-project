package use_case.export_timetable_pdf;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface TimetablePdfGateway {
    void save(Path destination, List<String> plannedCourses) throws IOException;
}
