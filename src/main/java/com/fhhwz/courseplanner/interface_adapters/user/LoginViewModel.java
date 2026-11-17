package com.fhhwz.courseplanner.interface_adapters.user;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Everything the login view displays. Presenters update it and call
 * {@link #firePropertyChanged()}; the view listens and redraws
 * (Observer pattern via {@link PropertyChangeSupport}).
 */
public class LoginViewModel {
    public static final String VIEW_NAME = "log in";
    public static final String STATE_PROPERTY = "state";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private String username = "";
    private String errorMessage = "";
    private String infoMessage = "";
    private String loggedInUser = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Returns a neutral message for the user, such as confirming that their
     * account was created, or an empty string if there is none.
     *
     * @return the message to show
     */
    public String getInfoMessage() {
        return infoMessage;
    }

    public void setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
    }

    /**
     * Returns the name of the user who just logged in, or an empty string if
     * nobody has logged in yet.
     *
     * @return the logged-in username
     */
    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    /**
     * Tells every listener that the view model has changed.
     */
    public void firePropertyChanged() {
        support.firePropertyChange(STATE_PROPERTY, null, this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
