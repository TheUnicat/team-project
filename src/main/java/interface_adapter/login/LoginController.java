package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.signup.SignupViewModel;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;

/**
 * Turns the login view's input into requests: logging in, or going to the sign-up screen.
 */
public class LoginController {
    private final LoginInputBoundary loginInteractor;
    private final ViewManagerModel viewManagerModel;

    public LoginController(LoginInputBoundary loginInteractor, ViewManagerModel viewManagerModel) {
        this.loginInteractor = loginInteractor;
        this.viewManagerModel = viewManagerModel;
    }

    public void execute(String username, String password) {
        loginInteractor.execute(new LoginInputData(username, password));
    }

    public void switchToSignupView() {
        viewManagerModel.setActiveView(SignupViewModel.VIEW_NAME);
    }
}
