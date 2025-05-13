package main.modules;

import main.database.UserDatabase; 


import java.util.Scanner;

public class UserModule {
    public static void start(Scanner sc) {
        System.out.println("Welcome to Path2Tech!");

        System.out.print("Enter your username: ");
        String username = sc.nextLine().trim();

        if (!UserDatabase.userExists(username)) {
            System.out.println("New user detected. Creating profile...");
            UserDatabase.createUser(username);
        } else {
            System.out.println("Welcome back, " + username + "!");
        }

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Java");
            System.out.println("2. DSA");
            System.out.println("3. View Progress");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    JAVA_Module.start("JAVA", username, sc);
                    break;
                case 2:
                    DSA_Module.start("DSA", username, sc);
                    break;
                case 3:
                    System.out.println("Progress feature coming soon...");
                    break;
                case 4:
                    System.out.println("Exiting... Goodbye, " + username + "!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
