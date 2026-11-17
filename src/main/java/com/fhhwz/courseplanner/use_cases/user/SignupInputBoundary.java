package com.fhhwz.courseplanner.use_cases.user;

/**
 * Input boundary for the sign-up use case. The controller calls this.
 */
public interface SignupInputBoundary {
    /**
     * Attempts to create an account.
     *
     * @param inputData the username and passwords the user entered
     */
    void execute(SignupInputData inputData);
}
