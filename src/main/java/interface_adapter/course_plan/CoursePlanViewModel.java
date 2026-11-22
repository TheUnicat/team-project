package interface_adapter.course_plan;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CoursePlanViewModel {
    public static final String VIEW_NAME = "course plan";
    public static final String STATE_PROPERTY = "state";

    private final PropertyChangeSupport support =
            new PropertyChangeSupport(this);

    private CoursePlanState state =
            new CoursePlanState();

    public CoursePlanState getState() {
        return state;
    }

    public void setState(CoursePlanState state) {
        this.state = state;
    }

    public void firePropertyChanged() {
        support.firePropertyChange(
                STATE_PROPERTY,
                null,
                state);
    }

    public void addPropertyChangeListener(
            PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}