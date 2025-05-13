package main.models;

public class PracticeRecord {
    private String username;
    private int totalAttempts;
    private int correctAnswers;
    private int timeSpentMinutes;

    public PracticeRecord(String username, int totalAttempts, int correctAnswers, int timeSpentMinutes) {
        this.username = username;
        this.totalAttempts = totalAttempts;
        this.correctAnswers = correctAnswers;
        this.timeSpentMinutes = timeSpentMinutes;
    }

    public String getUsername() {
        return username;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getTimeSpentMinutes() {
        return timeSpentMinutes;
    }

    public void setTotalAttempts(int totalAttempts) {
        this.totalAttempts = totalAttempts;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public void setTimeSpentMinutes(int timeSpentMinutes) {
        this.timeSpentMinutes = timeSpentMinutes;
    }

    @Override
    public String toString() {
        return "PracticeRecord{" +
                "username='" + username + '\'' +
                ", totalAttempts=" + totalAttempts +
                ", correctAnswers=" + correctAnswers +
                ", timeSpentMinutes=" + timeSpentMinutes +
                '}';
    }
}
