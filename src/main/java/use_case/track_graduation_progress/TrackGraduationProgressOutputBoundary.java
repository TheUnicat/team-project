package use_case.track_graduation_progress;

/**
 * Presents the results of a graduation progress evaluation.
 */
public interface TrackGraduationProgressOutputBoundary {
    /**
     * Presents the progress for the selected degree and programs.
     *
     * @param outputData the results of the evaluation
     */
    void prepareSuccessView(TrackGraduationProgressOutputData outputData);
}