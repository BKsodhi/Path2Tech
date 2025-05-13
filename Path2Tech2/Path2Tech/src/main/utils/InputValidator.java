package main.utils;

import java.util.regex.Pattern;

public class InputValidator {

    public static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.matches(emailRegex, email);
    }

    public static boolean validatePhone(String phone) {
        return phone.matches("\\d{10}");  // Assuming phone number must be 10 digits long
    }

    public static boolean validateAge(int age) {
        return age >= 18 && age <= 100;
    }
}
