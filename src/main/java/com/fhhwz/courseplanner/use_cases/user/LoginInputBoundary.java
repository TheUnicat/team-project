package com.fhhwz.courseplanner.use_cases.user;

/**
 * Input boundary for the login use case. The controller calls this.
 */
public interface LoginInputBoundary {
    /**
     * Attempts to log in with the given credentials.
     *
     * @param inputData the username and password the user entered
     */
    void execute(LoginInputData inputData);
}
