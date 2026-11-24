package use_case.track_graduation_progress;

import java.util.List;

/**
 * The progress results for the selected degree and programs.
 */
public class TrackGraduationProgressOutputData {
    private final GraduationTargetProgress degreeProgress;
    private final List<GraduationTargetProgress> programProgress;

    public TrackGraduationProgressOutputData(
            GraduationTargetProgress degreeProgress,
            List<GraduationTargetProgress> programProgress) {
        this.degreeProgress = degreeProgress;
        this.programProgress = List.copyOf(programProgress);
    }

    public GraduationTargetProgress getDegreeProgress() {
        return degreeProgress;
    }

    public List<GraduationTargetProgress> getProgramProgress() {
        return programProgress;
    }
}