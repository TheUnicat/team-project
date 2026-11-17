package com.fhhwz.courseplanner.use_cases.user;

/**
 * Output boundary for the login use case. The presenter implements this.
 */
public interface LoginOutputBoundary {
    /**
     * Called when the credentials are correct.
     *
     * @param outputData information about the user who logged in
     */
    void prepareSuccessView(LoginOutputData outputData);

    /**
     * Called when the login fails.
     *
     * @param errorMessage a message explaining why
     */
    void prepareFailView(String errorMessage);
}
