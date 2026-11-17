package com.fhhwz.courseplanner;

import com.fhhwz.courseplanner.frameworks_and_drivers.user.InMemoryUserDataAccessObject;
import com.fhhwz.courseplanner.frameworks_and_drivers.user.LoginView;
import com.fhhwz.courseplanner.frameworks_and_drivers.user.SignupView;
import com.fhhwz.courseplanner.interface_adapters.user.LoginController;
import com.fhhwz.courseplanner.interface_adapters.user.LoginPresenter;
import com.fhhwz.courseplanner.interface_adapters.user.LoginViewModel;
import com.fhhwz.courseplanner.interface_adapters.user.SignupController;
import com.fhhwz.courseplanner.interface_adapters.user.SignupPresenter;
import com.fhhwz.courseplanner.interface_adapters.user.SignupViewModel;
import com.fhhwz.courseplanner.interface_adapters.user.ViewManagerModel;
import com.fhhwz.courseplanner.use_cases.user.LoginInteractor;
import com.fhhwz.courseplanner.use_cases.user.SignupInteractor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.CardLayout;

/**
 * Builds the program and shows the window (the "Main" component in Clean
 * Architecture). This is the only class that knows about every layer.
 */
public final class App {
    private App() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::createAndShowGui);
    }

    private static void createAndShowGui() {
        // Temporary in-memory storage until the SQLite DAO is ready.
        final InMemoryUserDataAccessObject userDataAccess = new InMemoryUserDataAccessObject();

        // One card per screen; whichever view name the model holds is shown.
        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        final CardLayout cards = new CardLayout();
        final JPanel screens = new JPanel(cards);
        viewManagerModel.addPropertyChangeListener(evt -> cards.show(screens, (String) evt.getNewValue()));

        final LoginViewModel loginViewModel = new LoginViewModel();
        final SignupViewModel signupViewModel = new SignupViewModel();

        // Log in
        final LoginController loginController = new LoginController(
                new LoginInteractor(userDataAccess, new LoginPresenter(loginViewModel)),
                viewManagerModel);
        screens.add(new LoginView(loginViewModel, loginController), LoginViewModel.VIEW_NAME);

        // Create account
        final SignupController signupController = new SignupController(
                new SignupInteractor(userDataAccess,
                        new SignupPresenter(signupViewModel, loginViewModel, viewManagerModel)),
                viewManagerModel);
        screens.add(new SignupView(signupViewModel, signupController), SignupViewModel.VIEW_NAME);

        final JFrame frame = new JFrame("Course Planner");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(screens);
        viewManagerModel.setActiveView(LoginViewModel.VIEW_NAME);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
