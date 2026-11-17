package com.fhhwz.courseplanner.frameworks_and_drivers.user;

import com.fhhwz.courseplanner.entities.user.User;
import com.fhhwz.courseplanner.use_cases.user.LoginUserDataAccessInterface;
import com.fhhwz.courseplanner.use_cases.user.SignupUserDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores users in memory only; everything is lost when the program exits.
 * Useful for tests and for running the UI before the SQLite storage is ready.
 * One class serves both use cases through their separate interfaces.
 */
public class InMemoryUserDataAccessObject
        implements LoginUserDataAccessInterface, SignupUserDataAccessInterface {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public boolean existsByName(String username) {
        return users.containsKey(username);
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }

    @Override
    public void save(User user) {
        users.put(user.getUsername(), user);
    }
}
