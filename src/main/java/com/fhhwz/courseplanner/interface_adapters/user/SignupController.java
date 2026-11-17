package com.fhhwz.courseplanner.interface_adapters.user;

import com.fhhwz.courseplanner.use_cases.user.SignupInputBoundary;
import com.fhhwz.courseplanner.use_cases.user.SignupInputData;

/**
 * Turns the sign-up view's input into requests: creating an account, or
 * going back to the login screen.
 */
public class SignupController {
    private final SignupInputBoundary signupInteractor;
    private final ViewManagerModel viewManagerModel;

    public SignupController(SignupInputBoundary signupInteractor, ViewManagerModel viewManagerModel) {
        this.signupInteractor = signupInteractor;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Starts the sign-up use case.
     *
     * @param username       the username typed by the user
     * @param password       the password typed by the user
     * @param repeatPassword the password typed a second time
     */
    public void execute(String username, String password, String repeatPassword) {
        signupInteractor.execute(new SignupInputData(username, password, repeatPassword));
    }

    /**
     * Shows the login screen.
     */
    public void switchToLoginView() {
        viewManagerModel.setActiveView(LoginViewModel.VIEW_NAME);
    }
}
