package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * An academic degree and its graduation requirements.
 */
public class Degree {
    private final String degreeId;
    private final String name;
    private final List<Requirement> requirements;

    /**
     * Creates a degree with the given requirements.
     *
     * @param degreeId the ID of the degree
     * @param name the name of the degree
     * @param requirements the requirements for completing the degree
     */
    public Degree(String degreeId, String name, List<Requirement> requirements) {
        this.degreeId = degreeId;
        this.name = name;
        this.requirements = new ArrayList<>(requirements);
    }

    /** @return the degree ID */
    public String getDegreeId() {
        return degreeId;
    }

    /** @return the degree name */
    public String getName() {
        return name;
    }

    /** @return a copy of the degree's requirements */
    public List<Requirement> getRequirements() {
        return new ArrayList<>(requirements);
    }
}