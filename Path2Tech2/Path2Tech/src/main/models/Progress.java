package main.models;

public class Progress {
    private String username;
    private int dsaProgress;
    private int javaProgress;
    private int ooseProgress;
    private int interviewProgress;

    // Constructor without interviewProgress (defaults to 0)
    public Progress(String username, int dsaProgress, int javaProgress, int ooseProgress) {
        this(username, dsaProgress, javaProgress, ooseProgress, 0);
    }

    // Full constructor
    public Progress(String username, int dsaProgress, int javaProgress, int ooseProgress, int interviewProgress) {
        this.username = username;
        this.dsaProgress = dsaProgress;
        this.javaProgress = javaProgress;
        this.ooseProgress = ooseProgress;
        this.interviewProgress = interviewProgress;
    }

    public String getUsername() {
        return username;
    }

    public int getDsaProgress() {
        return dsaProgress;
    }

    public int getJavaProgress() {
        return javaProgress;
    }

    public int getOoseProgress() {
        return ooseProgress;
    }

    public int getInterviewProgress() {
        return interviewProgress;
    }

    // Update progress based on module name
    public void updateProgress(String module, int score) {
        switch (module.toLowerCase()) {
            case "dsa" -> dsaProgress = score;
            case "java" -> javaProgress = score;
            case "oose" -> ooseProgress = score;
            case "interview" -> interviewProgress = score;
        }
    }

    // Get progress based on module name
    public int getProgress(String module) {
        return switch (module.toLowerCase()) {
            case "dsa" -> dsaProgress;
            case "java" -> javaProgress;
            case "oose" -> ooseProgress;
            case "interview" -> interviewProgress;
            default -> 0;
        };
    }

    @Override
    public String toString() {
        return username + "," + dsaProgress + "," + javaProgress + "," + ooseProgress + "," + interviewProgress;
    }

    // Parse from a saved string line
    public static Progress fromString(String line) {
        String[] parts = line.split(",");
        try {
            return switch (parts.length) {
                case 5 -> new Progress(parts[0],
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4]));
                case 4 -> new Progress(parts[0],
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]));
                default -> null;
            };
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
