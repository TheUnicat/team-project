package com.fhhwz.courseplanner.interface_adapters.user;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Remembers which screen is showing. Controllers and presenters change it;
 * the Swing view manager listens and switches screens.
 */
public class ViewManagerModel {
    public static final String ACTIVE_VIEW_PROPERTY = "activeView";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String activeView = "";

    public String getActiveView() {
        return activeView;
    }

    /**
     * Switches to another screen and notifies listeners.
     *
     * @param viewName the name of the screen to show
     */
    public void setActiveView(String viewName) {
        final String old = this.activeView;
        this.activeView = viewName;
        support.firePropertyChange(ACTIVE_VIEW_PROPERTY, old, viewName);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
