package use_case.track_graduation_progress;

import entity.Degree;
import entity.Program;

import java.util.Optional;
import java.util.Set;

/**
 * Provides the data needed to evaluate graduation progress.
 */
public interface TrackGraduationProgressDataAccessInterface {
    Optional<Degree> findDegreeById(String degreeId);

    Optional<Program> findProgramById(String programId);

    Set<String> getCompletedCourseIds();

    Set<String> getPlannedCourseIds();
}