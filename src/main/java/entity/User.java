package entity;

import java.util.*;

public class User {
    int year;
    Program program;
    List<Course> coursesCompleted;
    List<CoursePlan> savedTimetables;

    double creditsEarned() {
        double sum = 0;
        for (Course c : coursesCompleted) {
            sum += c.getCredits();
        }
        return sum;
    }
}
