package interface_adapter.signup;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;

/**
 * Turns the sign-up view's input into requests: creating an account, or going back to the login
 * screen.
 */
public class SignupController {
    private final SignupInputBoundary signupInteractor;
    private final ViewManagerModel viewManagerModel;

    public SignupController(SignupInputBoundary signupInteractor, ViewManagerModel viewManagerModel) {
        this.signupInteractor = signupInteractor;
        this.viewManagerModel = viewManagerModel;
    }

    public void execute(String username, String password, String repeatPassword) {
        signupInteractor.execute(new SignupInputData(username, password, repeatPassword));
    }

    public void switchToLoginView() {
        viewManagerModel.setActiveView(LoginViewModel.VIEW_NAME);
    }
}
