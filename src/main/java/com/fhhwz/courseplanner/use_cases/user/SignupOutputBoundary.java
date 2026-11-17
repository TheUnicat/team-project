package com.fhhwz.courseplanner.use_cases.user;

/**
 * Output boundary for the sign-up use case. The presenter implements this.
 */
public interface SignupOutputBoundary {
    /**
     * Called when the account was created.
     *
     * @param outputData information about the new account
     */
    void prepareSuccessView(SignupOutputData outputData);

    /**
     * Called when the account could not be created.
     *
     * @param errorMessage a message explaining why
     */
    void prepareFailView(String errorMessage);
}
