package use_case.track_graduation_progress;

/**
 * The evaluation result for a single required course.
 */
public class RequirementProgress {
    public enum Status {
        SATISFIED,
        EXPECTED,
        MISSING
    }

    private final String courseId;
    private final Status status;

    public RequirementProgress(String courseId, Status status) {
        this.courseId = courseId;
        this.status = status;
    }

    public String getCourseId() {
        return courseId;
    }

    public Status getStatus() {
        return status;
    }
}