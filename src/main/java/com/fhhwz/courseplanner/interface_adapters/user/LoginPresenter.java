package com.fhhwz.courseplanner.interface_adapters.user;

import com.fhhwz.courseplanner.use_cases.user.LoginOutputBoundary;
import com.fhhwz.courseplanner.use_cases.user.LoginOutputData;

/**
 * Converts the login use case's results into what the login view shows.
 */
public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;

    public LoginPresenter(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData outputData) {
        loginViewModel.setUsername(outputData.getUsername());
        loginViewModel.setErrorMessage("");
        loginViewModel.setInfoMessage("");
        loginViewModel.setLoggedInUser(outputData.getUsername());
        loginViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        loginViewModel.setErrorMessage(errorMessage);
        loginViewModel.setInfoMessage("");
        loginViewModel.setLoggedInUser("");
        loginViewModel.firePropertyChanged();
    }
}
