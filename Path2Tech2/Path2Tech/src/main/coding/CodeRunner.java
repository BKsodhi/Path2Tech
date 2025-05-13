/*package main.coding;

import main.database.ProgressDatabase;
import main.models.Progress;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Scanner;

public class CodeRunner {

    public static void run(Scanner sc, String username, String module, String questionFile, String testCaseBasePath, int questionNumber, String questionId) {
        System.out.println("\n==============================");
        System.out.println("Question from: " + questionFile);
        System.out.println("==============================");

        // Check if the question file exists
        if (!Files.exists(Paths.get(questionFile))) {
            System.out.println("❌ Question file does not exist.");
            return;
        }

        // Load and display the question
        try {
            Files.readAllLines(Paths.get(questionFile)).forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("❌ Failed to load question: " + e.getMessage());
            return;
        }

        System.out.println("\nPlease enter your **complete Java code**:");
        System.out.println("⚠️ Include:");
        System.out.println("- import statements if needed");
        System.out.println("- public class UserCode");
        System.out.println("- public static void main(String[] args)");
        System.out.println("Type 'END' on a new line after you're done.\n");

        StringBuilder userCode = new StringBuilder();
        while (true) {
            String line = sc.nextLine();
            if (line.trim().equalsIgnoreCase("END")) break;
            userCode.append(line).append("\n");
        }

        // Save code and validate using existing method
        String[] testResults = CodeExecutor.loadAndRunWithPathOverride(userCode.toString(), questionFile, testCaseBasePath, questionId);

        int passed = 0;
        int total = 0;

        System.out.println("\n✅ Code saved successfully.");
        for (String result : testResults) {
            System.out.println(result);
            if (result.startsWith("✅ Test Case")) passed++;
            if (result.startsWith("✅ Test Case") || result.startsWith("❌ Test Case")) total++;
        }

        System.out.println("\n? Total Passed: " + passed + " / " + total);

        boolean allPassed = passed == total;
        ProgressDatabase.saveQuestionProgress(username, module, questionId, allPassed);

        if (allPassed) {
            System.out.println("\n" + username + ", do you want to continue?");
            System.out.println("1. Ques" + (questionNumber + 1));
            System.out.println("2. Go Back");
        } else {
            System.out.println("\nChoose an option:");
            System.out.println("1. Reattempt");
            System.out.println("2. Ques" + (questionNumber + 1));
            System.out.println("3. Go Back");
        }

        int choice = 0;
        while (true) {
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
                if (choice >= 1 && choice <= 3) break;
                System.out.println("Invalid choice. Please choose 1, 2, or 3.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        if (allPassed) {
            switch (choice) {
                case 1 -> run(sc, username, module,
                        questionFile.replace("q" + questionNumber, "q" + (questionNumber + 1)),
                        testCaseBasePath.replace("q" + questionNumber, "q" + (questionNumber + 1)),
                        questionNumber + 1,
                        "q" + (questionNumber + 1));
                case 2 -> System.out.println("🔙 Returning to menu...");
            }
        } else {
            switch (choice) {
                case 1 -> run(sc, username, module, questionFile, testCaseBasePath, questionNumber, questionId);
                case 2 -> run(sc, username, module,
                        questionFile.replace("q" + questionNumber, "q" + (questionNumber + 1)),
                        testCaseBasePath.replace("q" + questionNumber, "q" + (questionNumber + 1)),
                        questionNumber + 1,
                        "q" + (questionNumber + 1));
                case 3 -> System.out.println("🔙 Returning to menu...");
            }
        }
    }

    public static void run(Scanner sc) {
        System.out.println("\n===== Java Code Runner =====");
        System.out.println("Type your Java code below.");
        System.out.println("Write only code inside the main() method (no class or method headers needed).");
        System.out.println("Type 'RUN' on a new line to execute your code.\n");

        StringBuilder userCode = new StringBuilder();
        while (true) {
            String line = sc.nextLine();
            if (line.trim().equalsIgnoreCase("RUN")) break;
            userCode.append(line).append("\n");
        }

        String result = CodeExecutor.executeSingleSnippet(userCode.toString());
        System.out.println("\n===== Execution Result =====");
        System.out.println(result);
        System.out.println("============================\n");
    }
}*/


/*package main.coding;

import main.database.ProgressDatabase;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Scanner;

public class CodeRunner {

    public static void run(Scanner sc, String username, String module, String questionFile, String testCaseBasePath, int questionNumber, String questionId) {
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n==============================");
            System.out.println("Question from: " + questionFile);
            System.out.println("==============================");

            if (!Files.exists(Paths.get(questionFile))) {
                System.out.println("❌ Question file does not exist.");
                return;
            }

            try {
                Files.readAllLines(Paths.get(questionFile)).forEach(System.out::println);
            } catch (Exception e) {
                System.out.println("❌ Failed to load question: " + e.getMessage());
                return;
            }

            System.out.println("\nPlease enter your **complete Java code**:");
            System.out.println("⚠️ Include:");
            System.out.println("- import statements if needed");
            System.out.println("- public class UserCode");
            System.out.println("- public static void main(String[] args)");
            System.out.println("Type 'END' on a new line after you're done.\n");

            StringBuilder userCode = new StringBuilder();
            while (true) {
                String line = sc.nextLine();
                if (line.trim().equalsIgnoreCase("END")) break;
                userCode.append(line).append("\n");
            }

            String[] testResults = CodeExecutor.loadAndRunWithPathOverride(userCode.toString(), questionFile, testCaseBasePath, questionId);

            int passed = 0;
            int total = 0;

            System.out.println("\n✅ Code saved successfully.");
            for (String result : testResults) {
                System.out.println(result);
                if (result.startsWith("✅ Test Case")) passed++;
                if (result.startsWith("✅ Test Case") || result.startsWith("❌ Test Case")) total++;
            }

            System.out.println("\n? Total Passed: " + passed + " / " + total);

            boolean allPassed = passed == total;
            ProgressDatabase.saveQuestionProgress(username, module, questionId, allPassed);

            if (!allPassed && !containsFailureMessage(testResults, username, questionId)) {
                System.out.println("\n? " + username + " failed some test cases for " + questionId);
            }

            // Display options
            if (allPassed) {
                System.out.println("\n" + username + ", do you want to continue?");
                System.out.println("1. Ques" + (questionNumber + 1));
                System.out.println("2. Go Back");
            } else {
                System.out.println("\nChoose an option:");
                System.out.println("1. Reattempt");
                System.out.println("2. Ques" + (questionNumber + 1));
                System.out.println("3. Go Back");
            }

            int choice = 0;
            while (true) {
                try {
                    choice = Integer.parseInt(sc.nextLine().trim());
                    if ((allPassed && (choice == 1 || choice == 2)) || (!allPassed && (choice >= 1 && choice <= 3))) {
                        break;
                    }
                    System.out.println("Invalid choice. Please choose a valid option.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            // Handle user choice
            if (allPassed) {
                switch (choice) {
                    case 1 -> {
                        run(sc, username, module,
                                questionFile.replace("q" + questionNumber, "q" + (questionNumber + 1)),
                                testCaseBasePath.replace("q" + questionNumber, "q" + (questionNumber + 1)),
                                questionNumber + 1,
                                "q" + (questionNumber + 1));
                        return;
                    }
                    case 2 -> {
                        System.out.println("🔙 Returning to menu...");
                        return;
                    }
                }
            } else {
                switch (choice) {
                    case 1 -> {
                        // just loop again — reattempt
                    }
                    case 2 -> {
                        run(sc, username, module,
                                questionFile.replace("q" + questionNumber, "q" + (questionNumber + 1)),
                                testCaseBasePath.replace("q" + questionNumber, "q" + (questionNumber + 1)),
                                questionNumber + 1,
                                "q" + (questionNumber + 1));
                        return;
                    }
                    case 3 -> {
                        System.out.println("🔙 Returning to menu...");
                        return;
                    }
                }
            }
        }
    }

    public static void run(Scanner sc) {
        System.out.println("\n===== Java Code Runner =====");
        System.out.println("Type your Java code below.");
        System.out.println("Write only code inside the main() method (no class or method headers needed).");
        System.out.println("Type 'RUN' on a new line to execute your code.\n");

        StringBuilder userCode = new StringBuilder();
        while (true) {
            String line = sc.nextLine();
            if (line.trim().equalsIgnoreCase("RUN")) break;
            userCode.append(line).append("\n");
        }

        String result = CodeExecutor.executeSingleSnippet(userCode.toString());
        System.out.println("\n===== Execution Result =====");
        System.out.println(result);
        System.out.println("============================\n");
    }

    private static boolean containsFailureMessage(String[] results, String username, String questionId) {
        String failureMsg = "? " + username + " failed some test cases for " + questionId;
        for (String result : results) {
            if (result.trim().equals(failureMsg)) {
                return true;
            }
        }
        return false;
    }
}*/

/*package main.coding;

import main.database.ProgressDatabase;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Scanner;

public class CodeRunner {

    public static void run(Scanner sc, String username, String module, String questionFile, String testCaseBasePath, int questionNumber, String questionId) {
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n=========================================================");
            System.out.println("Question from: " + questionFile);
            System.out.println("==========================================================");

            if (!Files.exists(Paths.get(questionFile))) {
                System.out.println("Question file does not exist.");
                return;
            }

            try {
                Files.readAllLines(Paths.get(questionFile)).forEach(System.out::println);
            } catch (Exception e) {
                System.out.println("Failed to load question: " + e.getMessage());
                return;
            }

            System.out.println("\nPlease enter your **complete Java code**:");
            System.out.println("Include:");
            System.out.println("- import statements if needed");
            System.out.println("- public class UserCode");
            System.out.println("- public static void main(String[] args)");
            System.out.println("Type 'END' on a new line after you're done.\n");

            StringBuilder userCode = new StringBuilder();
            while (true) {
                String line = sc.nextLine();
                if (line.trim().equalsIgnoreCase("END")) break;
                userCode.append(line).append("\n");
            }

            String[] testResults = CodeExecutor.loadAndRunWithPathOverride(userCode.toString(), questionFile, testCaseBasePath, questionId);

            int passed = 0;
            int total = 0;

            System.out.println("\nCode saved successfully.");
            for (String result : testResults) {
                System.out.println(result);
                if (result.startsWith("Test Case")) {
                    total++;
                    if (result.contains("Passed")) {
                        passed++;
                    }
                }
            }

            System.out.println("\nTotal Passed: " + passed + " / " + total);

            boolean allPassed = passed == total && total > 0;
            ProgressDatabase.saveQuestionProgress(username, module, questionId, allPassed);

           if (!allPassed && !containsFailureMessage(testResults, username, questionId)) {
    System.out.println(username + " failed some test cases for " + questionId);
}else if (allPassed) {
                System.out.println("\n" + username + " passed all test cases for " + questionId);
            }

            // Display options
            if (allPassed) {
                System.out.println("\n" + username + ", do you want to continue?");
                System.out.println("1. Ques" + (questionNumber + 1));
                System.out.println("2. Go Back");
                System.out.print("Enter your choice (1-2): ");
            } else {
                System.out.println("\nChoose an option:");
                System.out.println("1. Reattempt");
                System.out.println("2. Ques" + (questionNumber + 1));
                System.out.println("3. Go Back");
                System.out.print("Enter your choice (1-3): ");
            }

            int choice = 0;
            while (true) {
                try {
                    choice = Integer.parseInt(sc.nextLine().trim());
                    if ((allPassed && (choice == 1 || choice == 2)) || (!allPassed && (choice >= 1 && choice <= 3))) {
                        break;
                    }
                    System.out.println("Invalid choice. Please choose a valid option.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            // Handle user choice
            if (allPassed) {
                switch (choice) {
                    case 1 -> {
                        run(sc, username, module,
                                questionFile.replace("q" + questionNumber, "q" + (questionNumber + 1)),
                                testCaseBasePath.replace("q" + questionNumber, "q" + (questionNumber + 1)),
                                questionNumber + 1,
                                "q" + (questionNumber + 1));
                        return;
                    }
                    case 2 -> {
                        System.out.println("Returning to menu...");
                        return;
                    }
                }
            } else {
                switch (choice) {
                    case 1 -> {
                        // reattempt — just loop again
                    }
                    case 2 -> {
                        run(sc, username, module,
                                questionFile.replace("q" + questionNumber, "q" + (questionNumber + 1)),
                                testCaseBasePath.replace("q" + questionNumber, "q" + (questionNumber + 1)),
                                questionNumber + 1,
                                "q" + (questionNumber + 1));
                        return;
                    }
                    case 3 -> {
                        System.out.println("Returning to menu...");
                        return;
                    }
                }
            }
        }
    }

    public static void run(Scanner sc) {
        System.out.println("\n===== Java Code Runner =====");
        System.out.println("Type your Java code below.");
        System.out.println("Write only code inside the main() method (no class or method headers needed).");
        System.out.println("Type 'RUN' on a new line to execute your code.\n");

        StringBuilder userCode = new StringBuilder();
        while (true) {
            String line = sc.nextLine();
            if (line.trim().equalsIgnoreCase("RUN")) break;
            userCode.append(line).append("\n");
        }

        String result = CodeExecutor.executeSingleSnippet(userCode.toString());
        System.out.println("\n===== Execution Result =====");
        System.out.println(result);
        System.out.println("============================\n");
    }

    private static boolean containsFailureMessage(String[] results, String username, String questionId) {
        String failureMsg = " " + username + " failed some test cases for " + questionId;
        for (String result : results) {
            if (result.trim().equals(failureMsg)) {
                return true;
            }
        }
        return false;
    }
}*/

package main.coding;

import main.database.ProgressDatabase;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Scanner;

public class CodeRunner {

    public static void run(Scanner sc, String username, String module, String questionFile, String testCaseBasePath, int questionNumber, String questionId) {
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n=========================================================");
            System.out.println("Question from: " + questionFile);
            System.out.println("===========================================================");

            if (!Files.exists(Paths.get(questionFile))) {
                System.out.println("Question file does not exist.");
                return;
            }

            try {
                Files.readAllLines(Paths.get(questionFile)).forEach(System.out::println);
            } catch (Exception e) {
                System.out.println("Failed to load question: " + e.getMessage());
                return;
            }

            System.out.println("\nPlease enter your **complete Java code**:");
            System.out.println("Include:");
            System.out.println("- import statements if needed");
            System.out.println("- public class UserCode");
            System.out.println("- public static void main(String[] args)");
            System.out.println("Type 'END' on a new line after you're done.\n");

            StringBuilder userCode = new StringBuilder();
            while (true) {
                String line = sc.nextLine();
                if (line.trim().equalsIgnoreCase("END")) break;
                userCode.append(line).append("\n");
            }

            String[] testResults = CodeExecutor.loadAndRunWithPathOverride(userCode.toString(), questionFile, testCaseBasePath, questionId);

            int passed = 0;
            int total = 0;

            System.out.println("\nCode saved successfully.");
            for (String result : testResults) {
                System.out.println(result);
                if (result.startsWith("Test Case") && result.contains("Passed")) passed++;
                if (result.startsWith("Test Case")) total++;
            }

            System.out.println("\nTotal Passed: " + passed + " / " + total);

            boolean allPassed = passed == total;
            ProgressDatabase.saveQuestionProgress(username, module, questionId, allPassed);

            if (!allPassed) {
                System.out.println(username + " failed some test cases for " + questionId);
            }

            // Display options
            if (allPassed) {
                System.out.println("\n" + username + ", do you want to continue?");
                System.out.println("1. Ques" + (questionNumber + 1));
                System.out.println("2. Go Back");
                System.out.print("Enter your choice (1-2): ");
            } else {
                System.out.println("\nChoose an option:");
                System.out.println("1. Reattempt");
                System.out.println("2. Ques" + (questionNumber + 1));
                System.out.println("3. Go Back");
                System.out.print("Enter your choice (1-3): ");
            }

            int choice = 0;
            while (true) {
                try {
                    choice = Integer.parseInt(sc.nextLine().trim());
                    if ((allPassed && (choice == 1 || choice == 2)) || (!allPassed && (choice >= 1 && choice <= 3))) {
                        break;
                    }
                    System.out.println("Invalid choice. Please choose a valid option.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            // Handle user choice
            if (allPassed) {
                switch (choice) {
                    case 1 -> {
                        run(sc, username, module,
                                questionFile.replace("q" + questionNumber, "q" + (questionNumber + 1)),
                                testCaseBasePath.replace("q" + questionNumber, "q" + (questionNumber + 1)),
                                questionNumber + 1,
                                "q" + (questionNumber + 1));
                        return;
                    }
                    case 2 -> {
                        System.out.println("Returning to menu...");
                        return;
                    }
                }
            } else {
                switch (choice) {
                    case 1 -> {
                        // reattempt: loop continues
                    }
                    case 2 -> {
                        run(sc, username, module,
                                questionFile.replace("q" + questionNumber, "q" + (questionNumber + 1)),
                                testCaseBasePath.replace("q" + questionNumber, "q" + (questionNumber + 1)),
                                questionNumber + 1,
                                "q" + (questionNumber + 1));
                        return;
                    }
                    case 3 -> {
                        System.out.println("Returning to menu...");
                        return;
                    }
                }
            }
        }
    }

    public static void run(Scanner sc) {
        System.out.println("\n===== Java Code Runner =====");
        System.out.println("Type your Java code below.");
        System.out.println("Write only code inside the main() method (no class or method headers needed).");
        System.out.println("Type 'RUN' on a new line to execute your code.\n");

        StringBuilder userCode = new StringBuilder();
        while (true) {
            String line = sc.nextLine();
            if (line.trim().equalsIgnoreCase("RUN")) break;
            userCode.append(line).append("\n");
        }

        String result = CodeExecutor.executeSingleSnippet(userCode.toString());
        System.out.println("\n===== Execution Result =====");
        System.out.println(result);
        System.out.println("============================\n");
    }
}

