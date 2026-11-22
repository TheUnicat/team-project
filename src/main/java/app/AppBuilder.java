package app;

import data_access.InMemoryCoursePlanDataAccessObject;
import interface_adapter.course_plan.CoursePlanViewModel;
import interface_adapter.course_plan.add_course.AddCourseController;
import interface_adapter.course_plan.add_course.AddCoursePresenter;
import use_case.add_course.AddCourseInteractor;
import view.CoursePlanView;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;

public class AppBuilder {

    private final JPanel rootPanel;
    private final InMemoryCoursePlanDataAccessObject dataAccess;

    private CoursePlanView coursePlanView;

    public AppBuilder() {
        rootPanel = new JPanel(new BorderLayout());
        dataAccess =
                new InMemoryCoursePlanDataAccessObject();
    }

    public AppBuilder addCoursePlanView() {
        final CoursePlanViewModel viewModel =
                new CoursePlanViewModel();

        final AddCoursePresenter presenter =
                new AddCoursePresenter(viewModel);

        final AddCourseInteractor interactor =
                new AddCourseInteractor(
                        dataAccess,
                        presenter);

        final AddCourseController controller =
                new AddCourseController(interactor);

        coursePlanView =
                new CoursePlanView(
                        viewModel,
                        controller);

        rootPanel.add(
                coursePlanView,
                BorderLayout.CENTER);

        return this;
    }

    public JFrame build() {

        final JFrame application =
                new JFrame("Course Planner");

        application.setDefaultCloseOperation(
                WindowConstants.EXIT_ON_CLOSE);
        application.setContentPane(rootPanel);
        application.setSize(850, 500);
        application.setLocationRelativeTo(null);

        return application;
    }
}