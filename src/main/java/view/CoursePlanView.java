package view;

import entity.CourseSession;
import interface_adapter.course_plan.CoursePlanState;
import interface_adapter.course_plan.CoursePlanViewModel;
import interface_adapter.course_plan.add_course.AddCourseController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CoursePlanView extends JPanel
        implements PropertyChangeListener {

    private static final Color SUCCESS_COLOR =
            new Color(0, 128, 0);

    private final CoursePlanViewModel viewModel;
    private final AddCourseController controller;

    private final JComboBox<String> courseBox =
            new JComboBox<>(new String[]{
                    "CSC207",
                    "CSC209",
                    "CSC263",
                    "CSC301"
            });

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

    public CoursePlanView(
            CoursePlanViewModel viewModel,
            AddCourseController controller) {
        this.viewModel = viewModel;
        this.controller = controller;

        viewModel.addPropertyChangeListener(this);

        buildLayout();

        addButton.addActionListener(
                event -> submit());
    }

    private void buildLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20));

        final JPanel controls =
                new JPanel(new FlowLayout());

        controls.add(new JLabel("Course:"));
        controls.add(courseBox);

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
                (String) courseBox.getSelectedItem();

        final int studyYear =
                (Integer) yearBox.getSelectedItem();

        final CourseSession session =
                (CourseSession) sessionBox.getSelectedItem();

        controller.execute(
                courseId,
                studyYear,
                session);
    }

    @Override
    public void propertyChange(
            PropertyChangeEvent event) {
        final CoursePlanState state =
                viewModel.getState();

        planListModel.clear();

        for (String course :
                state.getPlannedCourses()) {
            planListModel.addElement(course);
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
}