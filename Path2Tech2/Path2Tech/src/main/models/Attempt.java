package main.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Attempt {
    private String username;
    private String subject;     // e.g., DSA, JAVA
    private String level;       // easy, medium, hard
    private String type;        // coding or interview
    private boolean success;    // true if answer/code was correct
    private LocalDateTime timestamp;

    public Attempt(String username, String subject, String level, String type, boolean success) {
        this.username = username;
        this.subject = subject;
        this.level = level;
        this.type = type;
        this.success = success;
        this.timestamp = LocalDateTime.now();
    }

    public Attempt(String username, String subject, String level, String type, boolean success, LocalDateTime timestamp) {
        this.username = username;
        this.subject = subject;
        this.level = level;
        this.type = type;
        this.success = success;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public String getSubject() {
        return subject;
    }

    public String getLevel() {
        return level;
    }

    public String getType() {
        return type;
    }

    public boolean isSuccess() {
        return success;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String toFileFormat() {
        return username + "," + subject + "," + level + "," + type + "," + success + "," + timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static Attempt fromFileFormat(String line) {
        String[] parts = line.split(",");
        if (parts.length != 6) return null;

        String username = parts[0];
        String subject = parts[1];
        String level = parts[2];
        String type = parts[3];
        boolean success = Boolean.parseBoolean(parts[4]);
        LocalDateTime timestamp = LocalDateTime.parse(parts[5], DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        return new Attempt(username, subject, level, type, success, timestamp);
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + subject.toUpperCase() + " - " + level + " " + type + " → " + (success ? "✅ Passed" : "❌ Failed");
    }
}
