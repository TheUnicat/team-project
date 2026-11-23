package view;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupViewModel;

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
 * The sign-up screen.
 */
public class SignupView extends JPanel implements PropertyChangeListener {
    private static final long serialVersionUID = 1L;

    private final SignupViewModel signupViewModel;
    private final SignupController signupController;

    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JPasswordField repeatPasswordField = new JPasswordField(20);
    private final JButton createButton = new JButton("Create account");
    private final JButton loginLink = createLink("Sign in here");
    private final JLabel statusLabel = new JLabel(" ");

    public SignupView(SignupViewModel signupViewModel, SignupController signupController) {
        this.signupViewModel = signupViewModel;
        this.signupController = signupController;
        this.signupViewModel.addPropertyChangeListener(this);

        buildLayout();

        createButton.addActionListener(e -> submit());
        repeatPasswordField.addActionListener(e -> submit());
        loginLink.addActionListener(e -> {
            clearFields();
            statusLabel.setText(" ");
            signupController.switchToLoginView();
        });
    }

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

        final JLabel title = new JLabel("Create an account");
        title.setFont(title.getFont().deriveFont(18f));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        add(title, c);

        c.gridwidth = 1;
        addRow(c, 1, "Username", usernameField);
        addRow(c, 2, "Password", passwordField);
        addRow(c, 3, "Repeat password", repeatPasswordField);

        c.gridx = 1;
        c.gridy = 4;
        c.anchor = GridBagConstraints.EAST;
        add(createButton, c);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.WEST;
        add(statusLabel, c);

        final JPanel switchRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        switchRow.add(new JLabel("Already have an account?"));
        switchRow.add(loginLink);
        c.gridy = 6;
        c.anchor = GridBagConstraints.CENTER;
        add(switchRow, c);
    }

    private void addRow(GridBagConstraints c, int row, String label, JTextField field) {
        c.gridx = 0;
        c.gridy = row;
        add(new JLabel(label), c);
        c.gridx = 1;
        add(field, c);
    }

    private void submit() {
        final char[] password = passwordField.getPassword();
        final char[] repeat = repeatPasswordField.getPassword();
        signupController.execute(usernameField.getText(), new String(password), new String(repeat));
        Arrays.fill(password, '\0');
        Arrays.fill(repeat, '\0');
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        repeatPasswordField.setText("");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (signupViewModel.getErrorMessage().isEmpty()) {
            clearFields();
            statusLabel.setText(" ");
        }
        else {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText(signupViewModel.getErrorMessage());
            passwordField.setText("");
            repeatPasswordField.setText("");
        }
    }
}
