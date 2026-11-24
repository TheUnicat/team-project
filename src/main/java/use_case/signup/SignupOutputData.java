package use_case.signup;

/**
 * The result of a successful sign-up.
 */
public class SignupOutputData {
    private final String username;

    public SignupOutputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
