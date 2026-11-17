package com.fhhwz.courseplanner.interface_adapters.user;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Everything the sign-up view displays. The presenter updates it and calls
 * {@link #firePropertyChanged()}; the view listens and redraws.
 */
public class SignupViewModel {
    public static final String VIEW_NAME = "sign up";
    public static final String STATE_PROPERTY = "state";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private String errorMessage = "";

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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
