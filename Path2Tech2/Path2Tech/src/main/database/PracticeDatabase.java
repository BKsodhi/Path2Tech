package main.database;

import main.models.PracticeRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PracticeDatabase {
    private static final Map<String, List<PracticeRecord>> userRecords = new HashMap<>();

    public static void addRecord(String username, PracticeRecord record) {
        userRecords.putIfAbsent(username, new ArrayList<>());
        userRecords.get(username).add(record);
    }

    public static List<PracticeRecord> getUserRecords(String username) {
        return userRecords.getOrDefault(username, new ArrayList<>());
    }

    public static void printUserRecords(String username) {
        List<PracticeRecord> records = getUserRecords(username);
        if (records.isEmpty()) {
            System.out.println("No practice records for " + username);
        } else {
            System.out.println("Practice Records for " + username + ":");
            for (PracticeRecord record : records) {
                System.out.println(record);
            }
        }
    }
}
