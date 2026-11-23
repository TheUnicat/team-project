package app;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();

        final JFrame application = appBuilder
                .addCoursePlanView()
                .addSearchCourseUseCase()
                .addAddCourseUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
