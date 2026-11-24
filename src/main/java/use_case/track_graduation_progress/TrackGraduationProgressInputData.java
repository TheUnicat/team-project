package use_case.track_graduation_progress;

import java.util.List;

/**
 * The degree and programs selected for graduation progress tracking.
 */
public class TrackGraduationProgressInputData {

    private final String degreeId;
    private final List<String> programIds;

    public TrackGraduationProgressInputData(String degreeId, List<String> programIds) {
        this.degreeId = degreeId;
        this.programIds = List.copyOf(programIds);
    }

    public String getDegreeId() {
        return degreeId;
    }

    public List<String> getProgramIds() {
        return programIds;
    }
}
