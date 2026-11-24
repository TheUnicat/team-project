package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Remembers which screen is showing.
 */
public class ViewManagerModel {
    public static final String ACTIVE_VIEW_PROPERTY = "activeView";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String activeView = "";

    public String getActiveView() {
        return activeView;
    }

    public void setActiveView(String viewName) {
        final String old = this.activeView;
        this.activeView = viewName;
        support.firePropertyChange(ACTIVE_VIEW_PROPERTY, old, viewName);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
