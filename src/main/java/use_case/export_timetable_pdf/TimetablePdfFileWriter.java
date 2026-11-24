package use_case.export_timetable_pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TimetablePdfFileWriter implements TimetablePdfGateway {
    private static final Charset PDF_TEXT_ENCODING = Charset.forName("windows-1252");
    private static final int COURSES_PER_PAGE = 40;

    @Override
    public void save(Path destination, List<String> plannedCourses) throws IOException {
        final byte[] document = createPdf(plannedCourses);
        final Path target = destination.toAbsolutePath();
        final Path temporary = Files.createTempFile(target.getParent(), ".timetable-", ".pdf");
        try {
            Files.write(temporary, document);
            Files.move(temporary, target, StandardCopyOption.REPLACE_EXISTING);
        }
        finally {
            Files.deleteIfExists(temporary);
        }
    }

    private byte[] createPdf(List<String> courses) {
        final ByteArrayOutputStream pdf = new ByteArrayOutputStream();
        final List<Integer> offsets = new ArrayList<>();
        final int pageCount = Math.max(1, (courses.size() + COURSES_PER_PAGE - 1) / COURSES_PER_PAGE);
        final StringBuilder pageReferences = new StringBuilder();

        // The catalog, page tree, and shared font occupy objects 1 through 3.
        for (int page = 0; page < pageCount; page++) {
            pageReferences.append(4 + 2 * page).append(" 0 R ");
        }

        write(pdf, "%PDF-1.4\n");
        writeObject(pdf, offsets, "<< /Type /Catalog /Pages 2 0 R >>");
        writeObject(pdf, offsets, "<< /Type /Pages /Count " + pageCount
                + " /Kids [" + pageReferences + "] >>");
        writeObject(pdf, offsets, "<< /Type /Font /Subtype /Type1"
                + " /BaseFont /Helvetica /Encoding /WinAnsiEncoding >>");

        for (int page = 0; page < pageCount; page++) {
            final int contentObject = 5 + 2 * page;
            writeObject(pdf, offsets, "<< /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792]"
                    + " /Resources << /Font << /F1 3 0 R >> >>"
                    + " /Contents " + contentObject + " 0 R >>");

            final int firstCourse = page * COURSES_PER_PAGE;
            final int end = Math.min(firstCourse + COURSES_PER_PAGE, courses.size());
            final String content = pageContent(courses.subList(firstCourse, end));
            writeObject(pdf, offsets, "<< /Length " + content.getBytes(PDF_TEXT_ENCODING).length
                    + " >>\nstream\n" + content + "endstream");
        }

        final int crossReferenceOffset = pdf.size();
        write(pdf, "xref\n0 " + (offsets.size() + 1) + "\n0000000000 65535 f \n");
        for (int offset : offsets) {
            write(pdf, String.format(Locale.ROOT, "%010d 00000 n \n", offset));
        }
        write(pdf, "trailer\n<< /Size " + (offsets.size() + 1)
                + " /Root 1 0 R >>\nstartxref\n" + crossReferenceOffset + "\n%%EOF\n");
        return pdf.toByteArray();
    }

    private String pageContent(List<String> courses) {
        final StringBuilder content = new StringBuilder(
                "BT\n/F1 20 Tf\n54 738 Td\n(Course Timetable) Tj\n"
                        + "/F1 12 Tf\n16 TL\n0 -32 Td\n");
        final List<String> rows = courses.isEmpty() ? List.of("No courses planned.") : courses;
        for (String row : rows) {
            final String escaped = row.replace("\\", "\\\\")
                    .replace("(", "\\(").replace(")", "\\)")
                    .replace('\n', ' ').replace('\r', ' ');
            content.append('(').append(escaped).append(") Tj\nT*\n");
        }
        return content.append("ET\n").toString();
    }

    private void writeObject(ByteArrayOutputStream pdf, List<Integer> offsets, String body) {
        offsets.add(pdf.size());
        write(pdf, offsets.size() + " 0 obj\n" + body + "\nendobj\n");
    }

    private void write(ByteArrayOutputStream pdf, String value) {
        pdf.writeBytes(value.getBytes(PDF_TEXT_ENCODING));
    }
}
