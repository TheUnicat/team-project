package use_case.track_graduation_progress;

import entity.Degree;
import entity.Program;
import entity.Requirement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Evaluates graduation progress for the selected degree and programs.
 */
public class TrackGraduationProgressInteractor
        implements TrackGraduationProgressInputBoundary {

    private final TrackGraduationProgressDataAccessInterface dataAccess;
    private final TrackGraduationProgressOutputBoundary presenter;

    public TrackGraduationProgressInteractor(
            TrackGraduationProgressDataAccessInterface dataAccess,
            TrackGraduationProgressOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(TrackGraduationProgressInputData inputData) {
        Set<String> completedCourseIds = dataAccess.getCompletedCourseIds();
        Set<String> plannedCourseIds = dataAccess.getPlannedCourseIds();

        String degreeId = inputData.getDegreeId();
        Optional<List<Requirement>> degreeRequirements =
                dataAccess.findDegreeById(degreeId).map(Degree::getRequirements);
        GraduationTargetProgress degreeProgress = evaluateTarget(
                degreeId, degreeRequirements, completedCourseIds, plannedCourseIds);

        List<GraduationTargetProgress> programProgress = new ArrayList<>();
        for (String programId : inputData.getProgramIds()) { //Evaluate each program and present the reusult
            Optional<List<Requirement>> programRequirements =
                    dataAccess.findProgramById(programId).map(Program::getRequirements);
            programProgress.add(evaluateTarget(
                    programId, programRequirements, completedCourseIds, plannedCourseIds));
        }

        TrackGraduationProgressOutputData outputData =
                new TrackGraduationProgressOutputData(degreeProgress, programProgress);
        presenter.prepareSuccessView(outputData);
    }

    private GraduationTargetProgress evaluateTarget(
            String targetId,
            Optional<List<Requirement>> requirements,
            Set<String> completedCourseIds,
            Set<String> plannedCourseIds) {
        if (requirements.isEmpty()) {
            return new GraduationTargetProgress(targetId, false, List.of());
        }

        List<RequirementProgress> results = new ArrayList<>();
        for (Requirement requirement : requirements.get()) {
            String courseId = requirement.getCourseId();
            RequirementProgress.Status status;

            if (completedCourseIds.contains(courseId)) {
                status = RequirementProgress.Status.SATISFIED;
            } else if (plannedCourseIds.contains(courseId)) {
                status = RequirementProgress.Status.EXPECTED;
            } else {
                status = RequirementProgress.Status.MISSING;
            }

            results.add(new RequirementProgress(courseId, status));
        }

        return new GraduationTargetProgress(targetId, true, results);
    }
}