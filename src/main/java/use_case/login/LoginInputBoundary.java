package use_case.login;

/**
 * Input boundary for the login use case.
 */
public interface LoginInputBoundary {
    /**
     * Attempts to log in with the given credentials.
     *
     * @param inputData the username and password the user entered
     */
    void execute(LoginInputData inputData);
}
