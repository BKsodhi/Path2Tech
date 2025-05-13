package main.modules;

import main.database.ProgressDatabase;
import main.models.Progress;

public class ProgressModule {

    // Initialize progress for the user (if new user)
    public static void initializeProgress(String username) {
        ProgressDatabase.saveOrUpdateProgress(new Progress(username, 0, 0, 0));
        System.out.println("Progress initialized for user: " + username);
    }

    // Update progress for a particular subject (e.g., after practice)
    public static void updateProgress(String username, String subject) {
        int currentProgress = ProgressDatabase.getProgress(username, subject);
        int newProgress = currentProgress + 10; // Example: Increase by 10% after practice
        ProgressDatabase.updateProgress(username, subject, newProgress);
        System.out.println("Progress updated for " + subject + "!");
    }

    // Retrieve progress for all subjects
    public static void displayProgress(String username) {
        int dsaProgress = ProgressDatabase.getProgress(username, "DSA");
        int javaProgress = ProgressDatabase.getProgress(username, "Java");
        int ooseProgress = ProgressDatabase.getProgress(username, "OOSE");

        System.out.println("Progress for " + username + ":");
        System.out.println("DSA: " + dsaProgress + "%");
        System.out.println("Java: " + javaProgress + "%");
        System.out.println("OOSE: " + ooseProgress + "%");
    }

    // ✅ New method: check if user has completed a subject
    public static boolean hasCompletedSubject(String username, String subject) {
        int progress = ProgressDatabase.getProgress(username, subject);
        return progress >= 100;
    }
}
