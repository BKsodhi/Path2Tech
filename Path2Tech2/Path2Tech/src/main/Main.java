/*
package main;

import main.database.UserDatabase;
import main.models.User;
import main.modules.DSA_Module;
import main.modules.JAVA_Module;
import main.modules.OOSE_Module; 

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        User user = null;

        System.out.println("Welcome to Path2Tech: Prep.Track.Succeed!!");

        // Main menu loop for registration, login, update, and delete
        while (user == null) {
            System.out.println("\nChoose an option to proceed:");
            System.out.println("1. Register (New User)");
            System.out.println("2. Login (Existing User)");
            System.out.println("3. Update (Update Your Profile)");
            System.out.println("4. Delete (Delete Your Account)");
            System.out.print("Enter choice (1-4): ");
            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    user = UserDatabase.registerUser(sc);
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    String loginUsername = sc.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = sc.nextLine();
                    user = UserDatabase.loginUser(loginUsername, loginPassword);
                    break;
                case 3:
                    System.out.print("Enter username to update: ");
                    String updateUsername = sc.nextLine();
                    UserDatabase.updateUser(sc, updateUsername);
                    System.out.println("Your profile has been updated!");

                    // Re-login after update
                    System.out.print("\nRe-login after update.\nEnter username: ");
                    String reLoginUsername = sc.nextLine();
                    System.out.print("Enter password: ");
                    String reLoginPassword = sc.nextLine();
                    user = UserDatabase.loginUser(reLoginUsername, reLoginPassword);
                    break;
                case 4:
                    System.out.print("Enter username to delete: ");
                    String deleteUsername = sc.nextLine();
                    UserDatabase.deleteUser(deleteUsername);
                    System.out.println("Exiting program. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        // After successful login/registration
        System.out.println("\nHey " + user.getUsername() + ", what do you want to practice?");

        // Practice selection loop
        while (true) {
            System.out.println("\nChoose a subject to practice:");
            System.out.println("1. DSA");
            System.out.println("2. JAVA");
            System.out.println("3. OOSE"); 
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");
            int subjectChoice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (subjectChoice) {
                case 1:
                    DSA_Module.start("DSA", user.getUsername(), sc);
                    break;
                case 2:
                    JAVA_Module.start("JAVA", user.getUsername(), sc);
                    break;
                case 3:
                    OOSE_Module.start("OOSE", user.getUsername(), sc); 
                    break;
                case 4:
                    System.out.println("Thank you for using Path2Tech. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
*/

package main;

import main.database.UserDatabase;
import main.models.User;
import main.modules.DSA_Module;
import main.modules.JAVA_Module;
import main.modules.OOSE_Module;
import main.admin.AdminLogin;
import main.admin.AdminDashboard;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Path2Tech: Prep.Track.Succeed!!");

        showMainMenu(sc);
    }

    // New main menu function with loop
    public static void showMainMenu(Scanner sc) {
        while (true) {
            System.out.println("\nLogin as:");
            System.out.println("1. User");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int roleChoice;
            try {
                roleChoice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            if (roleChoice == 3) {
                System.out.println("Thank you for using Path2Tech. Goodbye!");
                return;
            }

            if (roleChoice == 2) {
                // Admin Login
                if (AdminLogin.login(sc)) {
                    AdminDashboard.showDashboard(sc);
                } else {
                    System.out.println("Invalid admin credentials. Returning to main menu...");
                }
                continue;
            }

            // ---------- Existing User Flow ----------
            User user = null;

            // Main menu loop for registration, login, update, and delete
            while (user == null) {
                System.out.println("\nChoose an option to proceed:");
                System.out.println("1. Register (New User)");
                System.out.println("2. Login (Existing User)");
                System.out.println("3. Update (Update Your Profile)");
                System.out.println("4. Delete (Delete Your Account)");
                System.out.println("5. Back to Main Menu");
                System.out.print("Enter choice (1-5): ");
                int choice;
                try {
                    choice = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        user = UserDatabase.registerUser(sc);
                        break;
                    case 2:
                        System.out.print("Enter username: ");
                        String loginUsername = sc.nextLine();
                        System.out.print("Enter password: ");
                        String loginPassword = sc.nextLine();
                        user = UserDatabase.loginUser(loginUsername, loginPassword);
                        break;
                    case 3:
                        System.out.print("Enter username to update: ");
                        String updateUsername = sc.nextLine();
                        UserDatabase.updateUser(sc, updateUsername);
                        System.out.println("Your profile has been updated!");

                        // Re-login after update
                        System.out.print("\nRe-login after update.\nEnter username: ");
                        String reLoginUsername = sc.nextLine();
                        System.out.print("Enter password: ");
                        String reLoginPassword = sc.nextLine();
                        user = UserDatabase.loginUser(reLoginUsername, reLoginPassword);
                        break;
                    case 4:
                        System.out.print("Enter username to delete: ");
                        String deleteUsername = sc.nextLine();
                        UserDatabase.deleteUser(deleteUsername);
                        System.out.println("Your account has been deleted. Returning to main menu...");
                        user = null;
                        break;
                    case 5:
                        user = null;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }

                if (choice == 5 || user == null) {
                    break;
                }
            }

            // After successful login/registration
            if (user != null) {
                System.out.println("\nHey " + user.getUsername() + ", what do you want to practice?");

                // Practice selection loop
                while (true) {
                    System.out.println("\nChoose a subject to practice:");
                    System.out.println("1. DSA");
                    System.out.println("2. JAVA");
                    System.out.println("3. OOSE");
                    System.out.println("4. Logout");
                    System.out.print("Enter your choice (1-4): ");
                    int subjectChoice;
                    try {
                        subjectChoice = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        continue;
                    }

                    switch (subjectChoice) {
                        case 1:
                            DSA_Module.start("DSA", user.getUsername(), sc);
                            break;
                        case 2:
                            JAVA_Module.start("JAVA", user.getUsername(), sc);
                            break;
                        case 3:
                            OOSE_Module.start("OOSE", user.getUsername(), sc);
                            break;
                        case 4:
                            System.out.println("Logged out successfully. Returning to main menu...");
                            user = null;
                            break;
                        default:
                            System.out.println("Invalid option. Try again.");
                    }

                    if (subjectChoice == 4) {
                        break;
                    }
                }
            }
        }
    }
}
