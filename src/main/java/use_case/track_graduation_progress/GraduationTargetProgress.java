package use_case.track_graduation_progress;

import java.util.List;

/**
 * The graduation progress for one selected degree or program.
 */
public class GraduationTargetProgress {
    private final String targetId;
    private final boolean requirementsAvailable;
    private final List<RequirementProgress> requirements;

    public GraduationTargetProgress(
            String targetId,
            boolean requirementsAvailable,
            List<RequirementProgress> requirements) {
        this.targetId = targetId;
        this.requirementsAvailable = requirementsAvailable;
        this.requirements = List.copyOf(requirements);
    }

    public String getTargetId() {
        return targetId;
    }

    public boolean areRequirementsAvailable() {
        return requirementsAvailable;
    }

    public List<RequirementProgress> getRequirements() {
        return requirements;
    }
}