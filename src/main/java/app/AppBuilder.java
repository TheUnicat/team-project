package app;

import data_access.InMemoryCourseDataAccessObject;
import interface_adapter.course_plan.CoursePlanViewModel;
import interface_adapter.course_plan.add_course.AddCourseController;
import interface_adapter.course_plan.add_course.AddCoursePresenter;
import interface_adapter.course_plan.search_course.SearchCourseController;
import interface_adapter.course_plan.search_course.SearchCoursePresenter;
import use_case.add_course.AddCourseInteractor;
import use_case.search_course.SearchCourseInteractor;
import view.CoursePlanView;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;

public class AppBuilder {

    private final JPanel rootPanel;
    private final InMemoryCourseDataAccessObject dataAccess;

    private CoursePlanView coursePlanView;
    private CoursePlanViewModel coursePlanViewModel;

    public AppBuilder() {
        rootPanel = new JPanel(new BorderLayout());
        dataAccess =
                new InMemoryCourseDataAccessObject();
    }

    public AppBuilder addCoursePlanView() {
        coursePlanViewModel = new CoursePlanViewModel();
        coursePlanView =
                new CoursePlanView(coursePlanViewModel);

        rootPanel.add(
                coursePlanView,
                BorderLayout.CENTER);

        return this;
    }

    public AppBuilder addAddCourseUseCase() {
        final AddCoursePresenter presenter =
                new AddCoursePresenter(coursePlanViewModel);

        final AddCourseInteractor interactor =
                new AddCourseInteractor(
                        dataAccess,
                        presenter);

        final AddCourseController controller =
                new AddCourseController(interactor);

        coursePlanView.setAddCourseController(controller);

        return this;
    }

    public AppBuilder addSearchCourseUseCase() {
        final SearchCoursePresenter searchPresenter =
                new SearchCoursePresenter(
                        coursePlanViewModel);

        final SearchCourseInteractor searchInteractor =
                new SearchCourseInteractor(
                        dataAccess,
                        searchPresenter);

        final SearchCourseController searchController =
                new SearchCourseController(
                        searchInteractor);

        coursePlanView.setSearchCourseController(
                searchController);

        return this;
    }

    public JFrame build() {

        final JFrame application =
                new JFrame("Course Planner");

        application.setDefaultCloseOperation(
                WindowConstants.EXIT_ON_CLOSE);
        application.setContentPane(rootPanel);

        return application;
    }
}
