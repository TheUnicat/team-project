package view;

import entity.CourseSession;
import interface_adapter.course_plan.CoursePlanState;
import interface_adapter.course_plan.CoursePlanViewModel;
import interface_adapter.course_plan.CoursePlanViewModelListener;
import interface_adapter.course_plan.add_course.AddCourseController;
import interface_adapter.course_plan.search_course.SearchCourseController;

import javax.swing.*;
import java.awt.*;

public class CoursePlanView extends JPanel
        implements CoursePlanViewModelListener {

    private static final Color SUCCESS_COLOR =
            new Color(0, 128, 0);

    private final CoursePlanViewModel viewModel;
    private AddCourseController addCourseController;
    private SearchCourseController searchCourseController;

    private final JTextField courseIdField =
            new JTextField(10);

    private final JButton searchButton =
            new JButton("Search");

    private final JLabel selectedCourseLabel =
            new JLabel("No course selected");

    private final JComboBox<Integer> yearBox =
            new JComboBox<>(new Integer[]{
                    1, 2, 3, 4, 5
            });

    private final JComboBox<CourseSession> sessionBox =
            new JComboBox<>(CourseSession.values());

    private final JButton addButton =
            new JButton("Add Course");

    private final DefaultListModel<String> planListModel =
            new DefaultListModel<>();

    private final JList<String> planList =
            new JList<>(planListModel);

    private final JLabel statusLabel =
            new JLabel(" ");

    public CoursePlanView(CoursePlanViewModel viewModel) {
        this.viewModel = viewModel;

        viewModel.addListener(this);

        buildLayout();

        addButton.setEnabled(false);

        searchButton.addActionListener(
                event -> search());
        courseIdField.addActionListener(
                event -> search());
        addButton.addActionListener(
                event -> submit());
    }

    private void buildLayout() {
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(850, 500));
        setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20));

        final JPanel controls =
                new JPanel(new FlowLayout());

        controls.add(new JLabel("Course ID:"));
        controls.add(courseIdField);
        controls.add(searchButton);
        controls.add(selectedCourseLabel);

        controls.add(new JLabel("Year:"));
        controls.add(yearBox);

        controls.add(new JLabel("Session:"));
        controls.add(sessionBox);

        controls.add(addButton);

        final JPanel planPanel =
                new JPanel(new BorderLayout());

        planPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Course Plan"));

        planPanel.add(
                new JScrollPane(planList),
                BorderLayout.CENTER);

        add(controls, BorderLayout.NORTH);
        add(planPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    private void submit() {
        final String courseId =
                viewModel.getState().getSelectedCourseId();

        final int studyYear =
                (Integer) yearBox.getSelectedItem();

        final CourseSession session =
                (CourseSession) sessionBox.getSelectedItem();

        addCourseController.execute(
                courseId,
                studyYear,
                session);
    }

    private void search() {
        searchCourseController.execute(
                courseIdField.getText());
    }

    @Override
    public void stateChanged() {
        final CoursePlanState state =
                viewModel.getState();

        planListModel.clear();

        for (String course :
                state.getPlannedCourses()) {
            planListModel.addElement(course);
        }

        final String selectedCourseId =
                state.getSelectedCourseId();

        if (selectedCourseId.isEmpty()) {
            selectedCourseLabel.setText(
                    "No course selected");
            addButton.setEnabled(false);
        }
        else {
            selectedCourseLabel.setText(
                    "Selected: " + selectedCourseId);
            addButton.setEnabled(true);
        }

        if (!state.getErrorMessage().isEmpty()) {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText(
                    state.getErrorMessage());
        }
        else {
            statusLabel.setForeground(SUCCESS_COLOR);
            statusLabel.setText(
                    state.getSuccessMessage());
        }
    }

    public void setAddCourseController(
            AddCourseController addCourseController) {
        this.addCourseController = addCourseController;
    }

    public void setSearchCourseController(
            SearchCourseController searchCourseController) {
        this.searchCourseController = searchCourseController;
    }
}
