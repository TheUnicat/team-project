package use_case.track_graduation_progress;

/**
 * Starts a graduation progress evaluation.
 */
public interface TrackGraduationProgressInputBoundary {
    /**
     * Evaluates progress for the selected degree and programs.
     *
     * @param inputData the student's selected degree and program IDs
     */
    void execute(TrackGraduationProgressInputData inputData);
}