package use_case.signup;

import entity.User;

/**
 * Creates an account: checks the username is free and the password is acceptable, stores the new
 * user, then tells the presenter what happened.
 */
public class SignupInteractor implements SignupInputBoundary {
    public static final int MIN_PASSWORD_LENGTH = 8;

    private final SignupUserDataAccessInterface userDataAccess;
    private final SignupOutputBoundary presenter;

    public SignupInteractor(SignupUserDataAccessInterface userDataAccess, SignupOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(SignupInputData inputData) {
        final String username = inputData.getUsername() == null ? "" : inputData.getUsername().trim();
        final String password = inputData.getPassword() == null ? "" : inputData.getPassword();

        if (username.isEmpty()) {
            presenter.prepareFailView("Please enter a username.");
        }
        else if (userDataAccess.existsByName(username)) {
            presenter.prepareFailView("That username is already taken.");
        }
        else if (password.length() < MIN_PASSWORD_LENGTH) {
            presenter.prepareFailView("Password must be at least " + MIN_PASSWORD_LENGTH + " characters.");
        }
        else if (!password.equals(inputData.getRepeatPassword())) {
            presenter.prepareFailView("Passwords don't match.");
        }
        else {
            userDataAccess.save(User.withPassword(username, password));
            presenter.prepareSuccessView(new SignupOutputData(username));
        }
    }
}
