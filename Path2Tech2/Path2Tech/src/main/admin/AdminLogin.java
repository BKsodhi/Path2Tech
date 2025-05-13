package main.admin;

import java.util.Scanner;

public class AdminLogin {
    private static final String ADMIN_USERNAME = "Path2Tech";
    private static final String ADMIN_PASSWORD = "12345";

    public static boolean login(Scanner sc) {
        System.out.print("Enter Admin Username: ");
        String username = sc.nextLine().trim();
        System.out.print("Enter Admin Password: ");
        String password = sc.nextLine().trim();

        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            return true;
        } else {
            System.out.println("Invalid admin credentials. Exiting...");
            return false;
        }
    }
}
