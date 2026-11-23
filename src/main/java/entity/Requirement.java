package entity;
/**
 * A graduation requirement that is satisfied by completing a specific course.
 */
public class Requirement {
    private final String courseId;
    /**
     * Creates a requirement for the specified course.
     *
     * @param courseId the ID of the course that must be completed
     */
    public Requirement(String courseId) {
        this.courseId = courseId;
    }
    /**
     * Returns the ID of the required course.
     *
     * @return the ID of the course that must be completed
     */
    public String getCourseId() {
        return courseId;
    }
}
