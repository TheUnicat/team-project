package com.fhhwz.courseplanner.use_cases.user;

import com.fhhwz.courseplanner.entities.user.User;

/**
 * The storage operations the sign-up use case needs.
 */
public interface SignupUserDataAccessInterface {
    /**
     * Checks whether a user with this name exists.
     *
     * @param username the name to look up
     * @return true if the name is already taken
     */
    boolean existsByName(String username);

    /**
     * Stores a new user.
     *
     * @param user the user to save
     */
    void save(User user);
}
