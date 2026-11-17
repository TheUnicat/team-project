package com.fhhwz.courseplanner.use_cases.user;

import com.fhhwz.courseplanner.entities.user.User;

/**
 * The storage operations the login use case needs. The data access layer
 * (in-memory now, SQLite later) implements this, so the interactor never
 * depends on how users are stored.
 */
public interface LoginUserDataAccessInterface {
    /**
     * Checks whether a user with this name exists.
     *
     * @param username the name to look up
     * @return true if the user exists
     */
    boolean existsByName(String username);

    /**
     * Returns the user with this name.
     *
     * @param username the name to look up; must exist
     * @return the stored user
     */
    User get(String username);
}
