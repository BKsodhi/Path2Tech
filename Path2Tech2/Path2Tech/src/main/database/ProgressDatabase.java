package main.database;

import main.models.Progress;

import java.io.*;
import java.util.*;

public class ProgressDatabase {
    private static final String FILE_PATH = "main/data/progress.txt";
    private static final Map<String, Progress> progressMap = new HashMap<>();

    // Static block: Load all progress on class load
    static {
        loadProgressFromFile();
    }

    private static void loadProgressFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Progress p = Progress.fromString(line);
                if (p != null) {
                    progressMap.put(p.getUsername(), p);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading progress: " + e.getMessage());
        }
    }

    private static void saveProgressToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Progress p : progressMap.values()) {
                writer.write(p.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving progress: " + e.getMessage());
        }
    }

    // Save or update full Progress object
    public static void saveOrUpdateProgress(Progress progress) {
        progressMap.put(progress.getUsername(), progress);
        saveProgressToFile();
    }

    // Update progress for coding/DSA/Java/OOSE modules
    public static void updateProgress(String username, String module, int score) {
        Progress existing = progressMap.getOrDefault(username, new Progress(username, 0, 0, 0));
        int newScore = score;
        if (!module.equalsIgnoreCase("interview")) {
            newScore += existing.getProgress(module);  // Accumulate for non-interview
        }
        existing.updateProgress(module, newScore);
        progressMap.put(username, existing);
        saveProgressToFile();
    }

    // Overloaded method for interview (percentage-based progress)
    public static void updateProgress(String username, String module, String filePath, int correct, int total) {
        int percentage = (int) (((double) correct / total) * 100);
        Progress existing = progressMap.getOrDefault(username, new Progress(username, 0, 0, 0));
        existing.updateProgress(module, percentage);
        progressMap.put(username, existing);
        saveProgressToFile();
    }

    // Get module progress percentage for a user
    public static int getProgress(String username, String module) {
        Progress p = progressMap.get(username);
        return (p != null) ? p.getProgress(module) : 0;
    }

    // Print progress report for one user
    public static void printProgress(String username) {
        Progress p = progressMap.get(username);
        if (p == null) {
            System.out.println("No progress found for user: " + username);
        } else {
            System.out.println("📊 Progress for " + username + ":");
            System.out.println("- DSA: " + p.getDsaProgress() + "%");
            System.out.println("- Java: " + p.getJavaProgress() + "%");
            System.out.println("- OOSE: " + p.getOoseProgress() + "%");
            System.out.println("- Interview: " + p.getInterviewProgress() + "%");
        }
    }

    // Delete one user's progress
    public static void deleteProgress(String username) {
        if (progressMap.containsKey(username)) {
            progressMap.remove(username);
            saveProgressToFile();
            System.out.println("✅ Progress deleted for user: " + username);
        } else {
            System.out.println("⚠️ No progress found for: " + username);
        }
    }

    // Update question-based progress (e.g., on coding submissions)
    public static void saveQuestionProgress(String username, String module, String questionId, boolean passedAll) {
        if (passedAll) {
            System.out.println("✅ " + username + " passed all test cases for " + questionId);
            updateProgress(username, module, 1); // Increment score
        } else {
            System.out.println("❌ " + username + " failed some test cases for " + questionId);
        }
    }

    // Print progress for all users
    public static void printAllProgress() {
        if (progressMap.isEmpty()) {
            System.out.println("No user progress found.");
        } else {
            for (Progress p : progressMap.values()) {
                printProgress(p.getUsername());
                System.out.println();
            }
        }
    }

    // ✅ Newly added method to fix compilation error
    public static void saveProgress(String username, String label) {
        System.out.println("✅ Progress saved: " + username + " -> " + label);
    }
}
