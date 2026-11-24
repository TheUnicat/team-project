package use_case.login;

import entity.User;

/**
 * Logs a user in: checks that the account exists and the password matches, then tells the
 * presenter what happened.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccess;
    private final LoginOutputBoundary presenter;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccess, LoginOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(LoginInputData inputData) {
        final String username = inputData.getUsername() == null ? "" : inputData.getUsername().trim();

        if (username.isEmpty()) {
            presenter.prepareFailView("Please enter a username.");
            return;
        }
        if (!userDataAccess.existsByName(username)) {
            presenter.prepareFailView("Incorrect username or password.");
            return;
        }

        final User user = userDataAccess.get(username);
        if (!user.checkPassword(inputData.getPassword())) {
            presenter.prepareFailView("Incorrect username or password.");
            return;
        }

        presenter.prepareSuccessView(new LoginOutputData(user.getUsername()));
    }
}
