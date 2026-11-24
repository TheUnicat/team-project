package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.course_plan.CoursePlanViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * Converts the login use case's results into what the login view shows.
 */
public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(LoginViewModel loginViewModel, ViewManagerModel viewManagerModel) {
        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData outputData) {
        loginViewModel.setUsername(outputData.getUsername());
        loginViewModel.setErrorMessage("");
        loginViewModel.setInfoMessage("");
        loginViewModel.setLoggedInUser(outputData.getUsername());
        loginViewModel.firePropertyChanged();
        viewManagerModel.setActiveView(CoursePlanViewModel.VIEW_NAME);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        loginViewModel.setErrorMessage(errorMessage);
        loginViewModel.setInfoMessage("");
        loginViewModel.setLoggedInUser("");
        loginViewModel.firePropertyChanged();
    }
}
