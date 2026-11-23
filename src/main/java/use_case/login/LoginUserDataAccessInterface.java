package use_case.login;

import entity.User;

/**
 * The storage operations the login use case needs.
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
