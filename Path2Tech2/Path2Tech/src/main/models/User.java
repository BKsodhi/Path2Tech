package main.models;

public class User {
    private String username;
    private String email;
    private String phone;
    private int age;
    private String password;

    // Constructor
    public User(String username, String email, String phone, int age, String password) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.password = password;
    }

    // Getters
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public int getAge() { return age; }
    public String getPassword() { return password; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAge(int age) { this.age = age; }
    public void setPassword(String password) { this.password = password; }

    // Convert user to string (for file storage)
    @Override
    public String toString() {
        return username + "," + email + "," + phone + "," + age + "," + password;
    }

    // Create a User object from a line in the file
    public static User fromString(String data) {
        String[] parts = data.split(",");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid user data format: " + data);
        }
        String username = parts[0];
        String email = parts[1];
        String phone = parts[2];
        int age = Integer.parseInt(parts[3]);
        String password = parts[4];

        return new User(username, email, phone, age, password);
    }
}
