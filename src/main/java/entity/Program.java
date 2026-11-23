package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * An academic program and its completion requirements.
 */
public class Program {
    private final String programId;
    private final String name;
    private final ProgramType type;
    private final List<Requirement> requirements;

    /**
     * Creates an academic program with the given requirements.
     *
     * @param programId the unique ID of the program
     * @param name the name of the program
     * @param type the type of the program
     * @param requirements the requirements for completing the program
     */
    public Program(String programId, String name, ProgramType type,
                   List<Requirement> requirements) {
        this.programId = programId;
        this.name = name;
        this.type = type;
        this.requirements = new ArrayList<>(requirements);
    }

    /** @return the program ID */
    public String getProgramId() {
        return programId;
    }

    /** @return the program name */
    public String getName() {
        return name;
    }

    /** @return the program type */
    public ProgramType getType() {
        return type;
    }

    /** @return a copy of the program's requirements */
    public List<Requirement> getRequirements() {
        return new ArrayList<>(requirements);
    }
}