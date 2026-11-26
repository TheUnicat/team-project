package data_access;

import entity.CoursePlan;
import entity.PlannedCourse;
import entity.Degree;
import entity.Program;
import use_case.track_graduation_progress.TrackGraduationProgressDataAccessInterface;

import java.util.Map;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;

/**
 * Reads planned course IDs for graduation progress from the course plan
 * shared with the Add Course use case.
 */
public class InMemoryGraduationProgressDataAccessObject
        implements TrackGraduationProgressDataAccessInterface{
    private final CoursePlan coursePlan;
    private final Map<String, Degree> degreesById;
    private final Map<String, Program> programsById;
    private final Set<String> completedCourseIds;

    public InMemoryGraduationProgressDataAccessObject(
            CoursePlan coursePlan,
            Map<String, Degree> degreesById,
            Map<String, Program> programsById,
            Set<String> completedCourseIds) {
        this.coursePlan = coursePlan;
        this.degreesById = Map.copyOf(degreesById);
        this.programsById = Map.copyOf(programsById);
        this.completedCourseIds = Set.copyOf(completedCourseIds);
    }
    /**
     * Collects the course IDs currently in the shared plan.
     *
     * @return a new set of unique planned course IDs, empty if the plan is empty
     */
    public Set<String> getPlannedCourseIds() {
        Set<String> courseIds = new HashSet<>();
        for (PlannedCourse course : coursePlan.getPlannedCourses()) {
            courseIds.add(course.getCourseId());
        }
        return courseIds;
    }

    @Override
    public Optional<Degree> findDegreeById(String degreeId) {
        return Optional.ofNullable(degreesById.get(degreeId));
    }

    @Override
    public Optional<Program> findProgramById(String programId) {
        return Optional.ofNullable(programsById.get(programId));
    }

    @Override
    public Set<String> getCompletedCourseIds() {
        return completedCourseIds;
    }
}