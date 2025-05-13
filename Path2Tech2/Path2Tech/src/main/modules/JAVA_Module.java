package main.modules;

import main.coding.CodeExecutor;
import main.utils.QuestionHandler;
import main.database.ProgressDatabase;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class JAVA_Module {
    public static void start(String type, String username, Scanner sc) {
        System.out.println("[" + type + "] Module started for " + username);

        while (true) {
            System.out.println("\nChoose what you want to practice:");
            System.out.println("1. Coding");
            System.out.println("2. Interview Questions");
            System.out.println("3. Mock Test");
            System.out.println("4. Go Back");
            System.out.println("5. User Options");
            System.out.print("Enter your choice (1-5): ");

            try {
                int choice = sc.nextInt();
                sc.nextLine(); // clear buffer

                switch (choice) {
                    case 1:
                        showCodingMenu(username, "java", sc);
                        break;
                    case 2:
                        showInterviewDifficultyMenu(username, sc);
                        break;
                    case 3:
                        startMockTest(username);
                        break;
                    case 4:
                        System.out.println("Returning to main menu...");
                        return;
                    case 5:
                        showUserOptions(username, sc);
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                sc.nextLine(); 
            }
        }
    }

    private static void showCodingMenu(String username, String subject, Scanner sc) {
        System.out.println("\n" + username + ", choose coding difficulty level:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");
        System.out.print("Enter your choice (1-3): ");

        int level;
        try {
            level = sc.nextInt();
            sc.nextLine(); // clear buffer
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Returning...");
            sc.nextLine();
            return;
        }

        String levelName;
        switch (level) {
            case 1: levelName = "easy"; break;
            case 2: levelName = "medium"; break;
            case 3: levelName = "hard"; break;
            default:
                System.out.println("Invalid choice. Returning...");
                return;
        }

        String folderPath = "main/data/questions/" + subject + "/coding/" + levelName;
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Folder does not exist: " + folderPath);
            return;
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
        if (files == null || files.length == 0) {
            System.out.println("No coding questions found in: " + folderPath);
            return;
        }

        while (true) {
            System.out.println("\n" + username + ", which question do you want to practice?");
            for (int i = 0; i < files.length; i++) {
                System.out.println((i + 1) + " Ques: " + files[i].getName().replace(".txt", ""));
            }
            System.out.println((files.length + 1) + " Go Back");

            System.out.print("Enter your choice (1-" + (files.length + 1) + "): ");
            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                sc.nextLine();
                continue;
            }

            if (choice == files.length + 1) {
                return;
            }

            if (choice >= 1 && choice <= files.length) {
                String selectedFile = files[choice - 1].getPath();
                CodeExecutor.loadAndRunCodingQuestion(selectedFile, sc);
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void showInterviewDifficultyMenu(String username, Scanner sc) {
        System.out.println("\n" + username + ", what type of interview questions do you want to practice?");
        System.out.println("1. Easy-level questions");
        System.out.println("2. Medium-level questions");
        System.out.println("3. Hard-level questions");
        System.out.print("Enter your choice (1-3): ");

        int choice;
        try {
            choice = sc.nextInt();
            sc.nextLine(); // clear buffer
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Returning...");
            sc.nextLine();
            return;
        }

        String level;
        switch (choice) {
            case 1: level = "easy"; break;
            case 2: level = "medium"; break;
            case 3: level = "hard"; break;
            default:
                System.out.println("Invalid choice. Returning...");
                return;
        }

        String filePath = "main/data/questions/java/interview/" + level + ".txt";
        System.out.println("Starting " + level + " interview questions for " + username + "...");
        System.out.println("Loading questions from: " + filePath);

        QuestionHandler.startInterviewQuiz(filePath, sc, username);
        ProgressDatabase.saveProgress(username, "Java Interview - " + level); 
    }

    private static void startMockTest(String username) {
        System.out.println("Starting Java mock test for " + username + "...");
        System.out.println("All the best with your Java mock test!");
    }

    private static void showUserOptions(String username, Scanner sc) {
        while (true) {
            System.out.println("\n User Options:");
            System.out.println("1. View My Progress");
            System.out.println("2. Delete My Progress");
            System.out.println("3. Go Back");

            System.out.print("Enter your choice: ");
            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                sc.nextLine();
                continue;
            }

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
                        System.out.println("Cancelled.");
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


