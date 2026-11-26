package app;

import data_access.InMemoryCourseDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.course_plan.CoursePlanViewModel;
import interface_adapter.course_plan.ExportTimetablePdfController;
import interface_adapter.course_plan.add_course.AddCourseController;
import interface_adapter.course_plan.add_course.AddCoursePresenter;
import interface_adapter.course_plan.search_course.SearchCourseController;
import interface_adapter.course_plan.search_course.SearchCoursePresenter;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.add_course.AddCourseInteractor;
import use_case.export_timetable_pdf.ExportTimetablePdfInteractor;
import use_case.export_timetable_pdf.TimetablePdfFileWriter;
import use_case.login.LoginInteractor;
import use_case.search_course.SearchCourseInteractor;
import use_case.signup.SignupInteractor;
import view.CoursePlanView;
import view.LoginView;
import view.SignupView;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.CardLayout;

public class AppBuilder {
    private final CardLayout cardLayout;
    private final JPanel rootPanel;
    private final ViewManagerModel viewManagerModel;
    private final InMemoryCourseDataAccessObject dataAccess;

    private final InMemoryUserDataAccessObject userDataAccess;

    private CoursePlanView coursePlanView;
    private CoursePlanViewModel coursePlanViewModel;
    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private SignupView signupView;
    private SignupViewModel signupViewModel;

    public AppBuilder() {
        cardLayout = new CardLayout();
        rootPanel = new JPanel(cardLayout);
        viewManagerModel = new ViewManagerModel();
        viewManagerModel.addPropertyChangeListener(
                evt -> cardLayout.show(rootPanel, (String) evt.getNewValue()));
        dataAccess =
                new InMemoryCourseDataAccessObject();
        userDataAccess = new InMemoryUserDataAccessObject();
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        return this;
    }

    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        return this;
    }

    public AppBuilder addCoursePlanView() {
        coursePlanViewModel = new CoursePlanViewModel();
        final ExportTimetablePdfInteractor exportInteractor =
                new ExportTimetablePdfInteractor(new TimetablePdfFileWriter());
        final ExportTimetablePdfController exportController =
                new ExportTimetablePdfController(exportInteractor, coursePlanViewModel);
        coursePlanView =
                new CoursePlanView(coursePlanViewModel, exportController);

        rootPanel.add(
                coursePlanView,
                CoursePlanViewModel.VIEW_NAME);

        return this;
    }

    public AppBuilder addLoginUseCase() {
        final LoginPresenter presenter =
                new LoginPresenter(loginViewModel, viewManagerModel);

        final LoginInteractor interactor =
                new LoginInteractor(userDataAccess, presenter);

        final LoginController controller =
                new LoginController(interactor, viewManagerModel);

        loginView = new LoginView(loginViewModel, controller);
        rootPanel.add(loginView, LoginViewModel.VIEW_NAME);

        return this;
    }

    public AppBuilder addSignupUseCase() {
        final SignupPresenter presenter =
                new SignupPresenter(signupViewModel, loginViewModel, viewManagerModel);

        final SignupInteractor interactor =
                new SignupInteractor(userDataAccess, presenter);

        final SignupController controller =
                new SignupController(interactor, viewManagerModel);

        signupView = new SignupView(signupViewModel, controller);
        rootPanel.add(signupView, SignupViewModel.VIEW_NAME);

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
        viewManagerModel.setActiveView(LoginViewModel.VIEW_NAME);

        return application;
    }
}
