package main.modules;

import main.utils.QuestionHandler;
import main.database.ProgressDatabase;

import java.util.Scanner;

public class OOSE_Module {
    public static void start(String type, String username, Scanner sc) {
        System.out.println("[" + type + "] Module started for " + username);

        while (true) {
            System.out.println("\nChoose what you want to practice:");
            System.out.println("1. Interview Questions");
            System.out.println("2. Mock Test");
            System.out.println("3. Go Back");
            System.out.println("4. User Options");
            System.out.print("Enter your choice (1-4): ");

            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    showInterviewDifficultyMenu(username, sc);
                    break;
                case 2:
                    startMockTest(username);
                    break;
                case 3:
                    System.out.println("Returning to main menu...");
                    return;
                case 4:
                    showUserOptions(username, sc);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void showInterviewDifficultyMenu(String username, Scanner sc) {
        System.out.println("\n" + username + ", what type of interview questions do you want to practice?");
        System.out.println("1. Easy-level questions");
        System.out.println("2. Medium-level questions");
        System.out.println("3. Hard-level questions");
        System.out.print("Enter your choice (1-3): ");

        int choice = sc.nextInt();
        sc.nextLine(); // clear buffer

        String levelName;
        switch (choice) {
            case 1: levelName = "easy"; break;
            case 2: levelName = "medium"; break;
            case 3: levelName = "hard"; break;
            default:
                System.out.println("Invalid choice. Returning...");
                return;
        }

        String filePath = "main/data/questions/oose/interview/" + levelName + ".txt";
        System.out.println("Starting " + levelName + " interview questions for " + username + "...");
        System.out.println("Loading questions from: " + filePath);

        QuestionHandler.startInterviewQuiz(filePath, sc, username);
        ProgressDatabase.saveProgress(username, "OOSE Interview - " + levelName); // ✅ Save progress
    }

    private static void startMockTest(String username) {
        System.out.println("Starting OOSE mock test for " + username + "...");
        System.out.println("All the best with your OOSE mock test!");
    }

    private static void showUserOptions(String username, Scanner sc) {
        while (true) {
            System.out.println("\n🧾 User Options:");
            System.out.println("1. View My Progress");
            System.out.println("2. Delete My Progress");
            System.out.println("3. Go Back");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    ProgressDatabase.printProgress(username);
                    break;
                case 2:
                    System.out.print("Are you sure you want to delete your progress? (yes/no): ");
                    String confirm = sc.nextLine();
                    if (confirm.equalsIgnoreCase("yes")) {
                        ProgressDatabase.deleteProgress(username);
                    } else {
                        System.out.println("❎ Cancelled.");
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
