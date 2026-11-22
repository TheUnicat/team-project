package entity;

public enum CourseSession {
    FALL("Fall"),
    WINTER("Winter"),
    FALL_WINTER("Fall-Winter"),
    SUMMER_FIRST("Summer - First Half"),
    SUMMER_SECOND("Summer - Second Half"),
    SUMMER_FULL("Summer - Full");

    private final String displayName;

    CourseSession(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}