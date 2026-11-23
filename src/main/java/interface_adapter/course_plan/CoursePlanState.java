package interface_adapter.course_plan;

import java.util.ArrayList;
import java.util.List;

public class CoursePlanState {
    private final List<String> plannedCourses =
            new ArrayList<>();

    private String successMessage = "";
    private String errorMessage = "";
    private String selectedCourseId = "";

    public List<String> getPlannedCourses() {
        return new ArrayList<>(plannedCourses);
    }

    public void addPlannedCourse(String course) {
        plannedCourses.add(course);
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSelectedCourseId() {
        return selectedCourseId;
    }

    public void setSelectedCourseId(String selectedCourseId) {
        this.selectedCourseId = selectedCourseId;
    }
}
