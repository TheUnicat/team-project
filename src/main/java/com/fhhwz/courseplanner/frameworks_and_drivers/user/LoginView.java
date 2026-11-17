package com.fhhwz.courseplanner.frameworks_and_drivers.user;

import com.fhhwz.courseplanner.interface_adapters.user.LoginController;
import com.fhhwz.courseplanner.interface_adapters.user.LoginViewModel;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

/**
 * The login screen. It sends what the user typed to the controller and
 * redraws itself whenever the view model changes. It contains no login logic.
 */
public class LoginView extends JPanel implements PropertyChangeListener {
    private static final long serialVersionUID = 1L;
    private static final Color SUCCESS_COLOR = new Color(0, 128, 0);

    private final LoginViewModel loginViewModel;
    private final LoginController loginController;

    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JButton loginButton = new JButton("Log in");
    private final JButton signupLink = createLink("Sign up here");
    private final JLabel statusLabel = new JLabel(" ");

    public LoginView(LoginViewModel loginViewModel, LoginController loginController) {
        this.loginViewModel = loginViewModel;
        this.loginController = loginController;
        this.loginViewModel.addPropertyChangeListener(this);

        buildLayout();

        loginButton.addActionListener(e -> submit());
        // Pressing Enter in either field also logs in.
        usernameField.addActionListener(e -> submit());
        passwordField.addActionListener(e -> submit());
        signupLink.addActionListener(e -> {
            passwordField.setText("");
            statusLabel.setText(" ");
            loginController.switchToSignupView();
        });
    }

    /**
     * Makes a button that looks like an underlined text link.
     */
    private static JButton createLink(String text) {
        final JButton link = new JButton("<html><u>" + text + "</u></html>");
        link.setForeground(new Color(0, 102, 204));
        link.setBorder(BorderFactory.createEmptyBorder());
        link.setContentAreaFilled(false);
        link.setFocusPainted(false);
        link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return link;
    }

    private void buildLayout() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        final GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.anchor = GridBagConstraints.WEST;

        final JLabel title = new JLabel("Log in");
        title.setFont(title.getFont().deriveFont(18f));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        add(title, c);

        c.gridwidth = 1;
        c.gridy = 1;
        add(new JLabel("Username"), c);
        c.gridx = 1;
        add(usernameField, c);

        c.gridx = 0;
        c.gridy = 2;
        add(new JLabel("Password"), c);
        c.gridx = 1;
        add(passwordField, c);

        c.gridx = 1;
        c.gridy = 3;
        c.anchor = GridBagConstraints.EAST;
        add(loginButton, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.WEST;
        add(statusLabel, c);

        final JPanel switchRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        switchRow.add(new JLabel("Don't have an account?"));
        switchRow.add(signupLink);
        c.gridy = 5;
        c.anchor = GridBagConstraints.CENTER;
        add(switchRow, c);
    }

    private void submit() {
        final char[] password = passwordField.getPassword();
        loginController.execute(usernameField.getText(), new String(password));
        Arrays.fill(password, '\0');
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!loginViewModel.getErrorMessage().isEmpty()) {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText(loginViewModel.getErrorMessage());
            passwordField.setText("");
        }
        else if (!loginViewModel.getLoggedInUser().isEmpty()) {
            statusLabel.setForeground(SUCCESS_COLOR);
            statusLabel.setText("Logged in as " + loginViewModel.getLoggedInUser() + ".");
            passwordField.setText("");
        }
        else if (!loginViewModel.getInfoMessage().isEmpty()) {
            // Arrived from sign-up: fill in the new username.
            usernameField.setText(loginViewModel.getUsername());
            statusLabel.setForeground(SUCCESS_COLOR);
            statusLabel.setText(loginViewModel.getInfoMessage());
            passwordField.setText("");
            passwordField.requestFocusInWindow();
        }
    }
}
