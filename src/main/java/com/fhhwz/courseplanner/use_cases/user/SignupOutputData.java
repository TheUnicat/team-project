package com.fhhwz.courseplanner.use_cases.user;

/**
 * The result of a successful sign-up.
 */
public class SignupOutputData {
    private final String username;

    public SignupOutputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
