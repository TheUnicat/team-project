package interface_adapter.course_plan;

import java.util.ArrayList;
import java.util.List;

public class CoursePlanViewModel {
    public static final String VIEW_NAME = "course plan";

    private final List<CoursePlanViewModelListener> listeners =
            new ArrayList<>();

    private CoursePlanState state =
            new CoursePlanState();

    public CoursePlanState getState() {
        return state;
    }

    public void setState(CoursePlanState state) {
        this.state = state;
    }

    public void addListener(
            CoursePlanViewModelListener listener) {
        listeners.add(listener);
    }

    public void notifyStateChanged() {
        for (CoursePlanViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }
}
