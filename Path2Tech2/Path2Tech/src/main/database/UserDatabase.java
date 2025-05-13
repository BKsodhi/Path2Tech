package main.database;

import main.models.User;
import java.io.*;
import java.util.*;

public class UserDatabase {

    private static final String FILE_PATH = "main/data/users.txt"; // Ensure the data folder exists and the file is present

    public static boolean userExists(String username) {
        List<User> users = getUsersFromFile();
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public static void addUser(User user) {
        List<User> users = getUsersFromFile();
        users.add(user);
        saveUsersToFile(users);
    }

    // ✅ ADDED: Simple createUser method for compatibility
    public static void createUser(String username) {
        User newUser = new User(username, "", "", 0, "");
        addUser(newUser);
    }

    public static User loginUser(String username, String password) {
        List<User> users = getUsersFromFile();
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        System.out.println("Invalid username or password.");
        return null;
    }

    public static User registerUser(Scanner sc) {
        System.out.print("Enter username: ");
        String username = sc.nextLine().trim();

        if (userExists(username)) {
            System.out.println("Username already exists! Try again.");
            return null;
        }

        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();  // Clear buffer
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User newUser = new User(username, email, phone, age, password);
        addUser(newUser);
        System.out.println("Registration successful!");
        return newUser;
    }

    public static void updateUser(Scanner sc, String usernameToUpdate) {
        List<User> users = getUsersFromFile();
        boolean found = false;

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);

            if (user.getUsername().equalsIgnoreCase(usernameToUpdate)) {
                System.out.print("Enter password to verify: ");
                String password = sc.nextLine();

                if (user.getPassword().equals(password)) {
                    System.out.print("Enter new username (leave blank to keep the same): ");
                    String newUsername = sc.nextLine().trim();
                    if (newUsername.isEmpty()) newUsername = user.getUsername();

                    System.out.print("Enter new email (leave blank to keep the same): ");
                    String newEmail = sc.nextLine();
                    if (newEmail.isEmpty()) newEmail = user.getEmail();

                    System.out.print("Enter new phone (leave blank to keep the same): ");
                    String newPhone = sc.nextLine();
                    if (newPhone.isEmpty()) newPhone = user.getPhone();

                    System.out.print("Enter new age (leave blank to keep the same): ");
                    String ageInput = sc.nextLine();
                    int newAge = user.getAge();
                    if (!ageInput.isEmpty()) newAge = Integer.parseInt(ageInput);

                    System.out.print("Enter new password (leave blank to keep the same): ");
                    String newPassword = sc.nextLine();
                    if (newPassword.isEmpty()) newPassword = user.getPassword();

                    user.setUsername(newUsername);
                    user.setEmail(newEmail);
                    user.setPhone(newPhone);
                    user.setAge(newAge);
                    user.setPassword(newPassword);

                    users.set(i, user);
                    saveUsersToFile(users);
                    System.out.println("Profile updated successfully!");
                    found = true;
                    break;
                } else {
                    System.out.println("Incorrect password. Update aborted.");
                    return;
                }
            }
        }

        if (!found) {
            System.out.println("User not found with username: " + usernameToUpdate);
        }
    }

    public static void deleteUser(String username) {
        List<User> users = getUsersFromFile();
        boolean found = false;
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
            User user = iterator.next();
            if (user.getUsername().equalsIgnoreCase(username)) {
                iterator.remove();
                saveUsersToFile(users);
                found = true;
                System.out.println("User deleted successfully!");
                break;
            }
        }
        if (!found) {
            System.out.println("User not found!");
        }
    }

    private static List<User> getUsersFromFile() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    users.add(new User(data[0], data[1], data[2], Integer.parseInt(data[3]), data[4]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    private static void saveUsersToFile(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getEmail() + "," + user.getPhone() + "," 
                        + user.getAge() + "," + user.getPassword() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
