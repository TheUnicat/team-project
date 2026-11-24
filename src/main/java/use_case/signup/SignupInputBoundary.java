package use_case.signup;

/**
 * Input boundary for the sign-up use case.
 */
public interface SignupInputBoundary {
    /**
     * Attempts to create an account.
     *
     * @param inputData the username and passwords the user entered
     */
    void execute(SignupInputData inputData);
}
