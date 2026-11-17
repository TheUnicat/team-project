package com.fhhwz.courseplanner.interface_adapters.user;

import com.fhhwz.courseplanner.use_cases.user.SignupOutputBoundary;
import com.fhhwz.courseplanner.use_cases.user.SignupOutputData;

/**
 * Converts the sign-up use case's results into what the views show. On
 * success it sends the user to the login screen with their username filled in.
 */
public class SignupPresenter implements SignupOutputBoundary {
    private final SignupViewModel signupViewModel;
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;

    public SignupPresenter(SignupViewModel signupViewModel, LoginViewModel loginViewModel,
            ViewManagerModel viewManagerModel) {
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(SignupOutputData outputData) {
        signupViewModel.setErrorMessage("");
        signupViewModel.firePropertyChanged();

        loginViewModel.setUsername(outputData.getUsername());
        loginViewModel.setErrorMessage("");
        loginViewModel.setLoggedInUser("");
        loginViewModel.setInfoMessage("Account created. You can log in now.");
        loginViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(LoginViewModel.VIEW_NAME);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        signupViewModel.setErrorMessage(errorMessage);
        signupViewModel.firePropertyChanged();
    }
}
