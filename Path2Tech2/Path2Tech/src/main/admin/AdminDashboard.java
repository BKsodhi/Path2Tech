package main.admin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class AdminDashboard {

    public static void showDashboard(Scanner sc) {
        while (true) {
            System.out.println("\n====== ADMIN DASHBOARD ======");
            System.out.println("1. Upload New Questions");
            System.out.println("2. View All Registered Users");
            System.out.println("3. View All User Progress");
            System.out.println("4. Exit Admin Panel"); // ✅ NEW OPTION
            System.out.print("Enter your choice: ");

            String input = sc.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    uploadQuestions(sc);
                    break;
                case 2:
                    viewUsers();
                    break;
                case 4:
                    System.out.println("Exiting Admin Panel...");
                    return;
                case 3:
                    viewAllUserProgress(); // ✅ CALL NEW METHOD
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void uploadQuestions(Scanner sc) {
        System.out.println("\n[Admin] Uploading new questions...");
        System.out.print("Do you want to upload to a mock test? (yes/no): ");
        String mockChoice = sc.nextLine().trim().toLowerCase();

        if (mockChoice.equals("yes")) {
            handleMockTestUpload(sc);
            return;
        }

        System.out.print("Enter subject (JAVA/DSA): ");
        String subject = sc.nextLine().trim().toLowerCase();

        System.out.print("Enter difficulty (easy/medium/hard): ");
        String difficulty = sc.nextLine().trim().toLowerCase();

        System.out.print("Enter question type (coding/mcq): ");
        String type = sc.nextLine().trim().toLowerCase();

        if (type.equals("mcq")) {
            String folderPath = "main/data/questions/" + subject + "/interview";
            String filePath = folderPath + "/" + difficulty + ".txt";
            File dir = new File(folderPath);
            if (!dir.exists()) dir.mkdirs();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                System.out.print("Enter question content: ");
                String question = sc.nextLine();

                System.out.print("Option A: ");
                String a = sc.nextLine();
                System.out.print("Option B: ");
                String b = sc.nextLine();
                System.out.print("Option C: ");
                String c = sc.nextLine();
                System.out.print("Option D: ");
                String d = sc.nextLine();
                System.out.print("Correct option (A/B/C/D): ");
                String correct = sc.nextLine().trim().toUpperCase();

                writer.write(question);
                writer.newLine();
                writer.write("A) " + a);
                writer.newLine();
                writer.write("B) " + b);
                writer.newLine();
                writer.write("C) " + c);
                writer.newLine();
                writer.write("D) " + d);
                writer.newLine();
                writer.write("ANSWER: " + correct);
                writer.newLine();

                System.out.println("MCQ added to: " + filePath);

            } catch (IOException e) {
                System.out.println("Error writing MCQ: " + e.getMessage());
            }

        } else if (type.equals("coding")) {
            String folderPath = "main/data/questions/" + subject + "/coding/" + difficulty;
            File dir = new File(folderPath);
            if (!dir.exists()) dir.mkdirs();

            int questionCount = dir.list((d, name) -> name.endsWith(".txt")).length;
            int nextQno = questionCount + 1;
            String fileName = "q" + nextQno + ".txt";
            String filePath = folderPath + "/" + fileName;

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                System.out.print("Enter coding question content:\n");
                String question = sc.nextLine();

                writer.write("[CODING]");
                writer.newLine();
                writer.write(question);
                writer.newLine();
                writer.write("---");
                writer.newLine();

                System.out.println("Coding question saved to: " + filePath);

                // Create test case directories and files
                String baseTestPath = "main/testcases/" + subject + "/coding/" + difficulty;
                new File(baseTestPath + "/input").mkdirs();
                new File(baseTestPath + "/expected").mkdirs();
                new File(baseTestPath + "/output").mkdirs();

                String qname = "q" + nextQno;
                String inputPath = baseTestPath + "/input/" + qname + "_input.txt";
                String expectedPath = baseTestPath + "/expected/" + qname + "_expected.txt";
                String outputPath = baseTestPath + "/output/" + qname + "_output.txt";

                new File(inputPath).createNewFile();
                new File(expectedPath).createNewFile();
                new File(outputPath).createNewFile();

                System.out.println("Testcase files created:");
                System.out.println("- " + inputPath);
                System.out.println("- " + expectedPath);
                System.out.println("- " + outputPath);

                System.out.print("Enter number of test cases to add: ");
                int t;
                try {
                    t = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number of test cases.");
                    return;
                }

                try (
                    BufferedWriter inputWriter = new BufferedWriter(new FileWriter(inputPath, true));
                    BufferedWriter expectedWriter = new BufferedWriter(new FileWriter(expectedPath, true))
                ) {
                    for (int i = 1; i <= t; i++) {
                        System.out.println("Test Case " + i + ":");
                        System.out.print("Input: ");
                        String input = sc.nextLine();
                        inputWriter.write(input);
                        inputWriter.newLine();

                        System.out.print("Expected Output: ");
                        String expected = sc.nextLine();
                        expectedWriter.write(expected);
                        expectedWriter.newLine();
                    }
                    System.out.println(" " + t + " test cases added.");
                } catch (IOException e) {
                    System.out.println("Error writing test cases: " + e.getMessage());
                }

            } catch (IOException e) {
                System.out.println("Error writing coding question: " + e.getMessage());
            }

        } else {
            System.out.println("Invalid question type. Must be 'mcq' or 'coding'.");
        }
    }

    private static void handleMockTestUpload(Scanner sc) {
        System.out.print("Which mock test do you want to update? (JAVA/DSA/OOSE): ");
        String subject = sc.nextLine().trim().toLowerCase();

        String mockTestPath;
        switch (subject) {
            case "java":
                mockTestPath = "main/data/questions/java/mock/java_mock_test.txt";
                break;
            case "dsa":
                mockTestPath = "main/data/questions/dsa/mock/dsa_mock_test.txt";
                break;
            case "oose":
                mockTestPath = "main/data/questions/oose/oose_mock_test.txt";
                break;
            default:
                System.out.println("Invalid subject.");
                return;
        }

        System.out.print("How many questions do you want to add? ");
        int num;
        try {
            num = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
            return;
        }

        for (int i = 1; i <= num; i++) {
            System.out.println("\nAdding Question " + i + "...");
            System.out.print("Enter question type (mcq/coding): ");
            String type = sc.nextLine().trim().toLowerCase();

            if (type.equals("mcq")) {
                System.out.println("Enter MCQ question:");
                String question = sc.nextLine();
                System.out.println("Option A:");
                String a = sc.nextLine();
                System.out.println("Option B:");
                String b = sc.nextLine();
                System.out.println("Option C:");
                String c = sc.nextLine();
                System.out.println("Option D:");
                String d = sc.nextLine();
                System.out.println("Correct option (A/B/C/D):");
                String correct = sc.nextLine();

                String mcqFormatted = "[MCQ]\n" + question + "\nA. " + a + "\nB. " + b + "\nC. " + c + "\nD. " + d + "\nAnswer: " + correct + "\n---\n";
                try {
                    Files.write(Paths.get(mockTestPath), mcqFormatted.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } catch (IOException e) {
                    System.out.println("Error writing MCQ to mock test.");
                }

            } else if (type.equals("coding")) {
                System.out.print("Enter difficulty (easy/medium/hard): ");
                String diff = sc.nextLine().toLowerCase();
                System.out.print("Enter filename of existing coding question (e.g., q1.txt): ");
                String filename = sc.nextLine();
                String codingQuesPath = "main/data/questions/" + subject + "/coding/" + diff + "/" + filename;
                try {
                    String content = new String(Files.readAllBytes(Paths.get(codingQuesPath)));
                    String formatted = "[CODING]\n" + content + "\n---\n";
                    Files.write(Paths.get(mockTestPath), formatted.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } catch (IOException e) {
                    System.out.println("Error reading coding question file.");
                }

            } else {
                System.out.println("Invalid question type. Skipping...");
            }
        }
    }

    private static void viewUsers() {
        System.out.println("\n[Admin] List of Registered Users:");
        try {
            Files.lines(Paths.get("main/data/users.txt"))
                .forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Unable to read users: " + e.getMessage());
        }
    }

    // ✅ NEW METHOD
    private static void viewAllUserProgress() {
        System.out.println("\n[Admin] All User Progress (Ordered by First Attempt):");
        String file = "main/data/progress.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int count = 1;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String name = parts[0];
                    String dsa = parts[1];
                    String java = parts[2];
                    String oose = parts[3];
                    String interview = parts[4];
                    System.out.println(count++ + ". " + name + " => DSA: " + dsa + "%, Java: " + java + "%, OOSE: " + oose + "%, Interview: " + interview + "%");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading progress file: " + e.getMessage());
        }
    }
}
