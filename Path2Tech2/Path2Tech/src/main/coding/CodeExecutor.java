/*package main.coding;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CodeExecutor {

    private static final String SUBMISSION_PATH = "main/submissions/UserCode.java";

    public static String[] loadAndRunWithPathOverride(String code, String questionFile, String testCaseBasePath, String questionId) {
        try {
            // Step 1: Save user code
            saveUserCode(code);

            // Step 2: Compile
            if (!compileUserCode()) {
                return new String[]{"❌ Compilation failed. Please check your syntax."};
            }

            // Step 3: Prepare test case paths
            String inputPath = testCaseBasePath + "/input/" + questionId + "_input.txt";
            String expectedPath = testCaseBasePath + "/expected/" + questionId + "_expected.txt";
            String outputPath = testCaseBasePath + "/output/" + questionId + "_output.txt";

            if (!Files.exists(Paths.get(inputPath)) || !Files.exists(Paths.get(expectedPath))) {
                return new String[]{"? Test case files not found at expected paths."};
            }

            List<String> inputs = Files.readAllLines(Paths.get(inputPath));
            List<String> expectedOutputs = Files.readAllLines(Paths.get(expectedPath));
            List<String> actualOutputs = new ArrayList<>();
            List<String> results = new ArrayList<>();

            int total = Math.min(inputs.size(), expectedOutputs.size());

            for (int i = 0; i < total; i++) {
                String input = inputs.get(i);
                String expected = expectedOutputs.get(i).trim();
                String actual = runUserCode(input).trim();
                actualOutputs.add(actual);

                if (actual.equals(expected)) {
                    results.add("✅ Test Case " + (i + 1) + " Passed");
                } else {
                    results.add("❌ Test Case " + (i + 1) + " Failed");
                    results.add("   - Input: " + input);
                    results.add("   - Expected: " + expected);
                    results.add("   - Got: " + actual);
                }
            }

            // Write actual outputs to file
            Files.write(Paths.get(outputPath), actualOutputs);

            return results.toArray(new String[0]);

        } catch (Exception e) {
            return new String[]{"❌ Error: " + e.getMessage()};
        }
    }

    private static void saveUserCode(String code) throws IOException {
        Files.write(Paths.get(SUBMISSION_PATH), code.getBytes());
    }

    private static boolean compileUserCode() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("javac", SUBMISSION_PATH);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        process.waitFor();

        return process.exitValue() == 0;
    }

    private static String runUserCode(String input) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", "main/submissions", "UserCode");
        pb.redirectErrorStream(true);

        Process process = pb.start();

        // Send input
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        writer.write(input);
        writer.newLine();
        writer.flush();
        writer.close();

        // Capture output
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        process.waitFor();
        return output.toString().trim();
    }

    public static String executeSingleSnippet(String codeSnippet) {
        try {
            String code = "public class UserCode {\n" +
                    "    public static void main(String[] args) {\n" +
                    codeSnippet + "\n    }\n}";
            saveUserCode(code);
            if (!compileUserCode()) {
                return "❌ Compilation failed.";
            }
            return runUserCode("").trim();
        } catch (Exception e) {
            return "❌ Error: " + e.getMessage();
        }
    }

    // Required for JAVA_Module / DSA_Module to call
    public static void loadAndRunCodingQuestion(String questionFile, Scanner sc) {
        String baseTestPath = "main/testcases/java/coding/easy";
        String questionId = questionFile.contains("q1") ? "q1" : "q2"; // Simplified for example
        CodeRunner.run(sc, "testUser", "Java Coding", questionFile, baseTestPath, 1, questionId);
    }
}*/


/*package main.coding;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CodeExecutor {

    private static final String SUBMISSION_PATH = "main/submissions/UserCode.java";

    public static String[] loadAndRunWithPathOverride(String code, String questionFile, String testCaseBasePath, String questionId) {
        try {
            // Step 1: Save user code
            saveUserCode(code);

            // Step 2: Compile
            if (!compileUserCode()) {
                return new String[]{"Compilation failed. Please check your syntax."};
            }

            // Step 3: Prepare test case paths
            String inputPath = testCaseBasePath + "/input/" + questionId + "_input.txt";
            String expectedPath = testCaseBasePath + "/expected/" + questionId + "_expected.txt";
            String outputPath = testCaseBasePath + "/output/" + questionId + "_output.txt";

            if (!Files.exists(Paths.get(inputPath)) || !Files.exists(Paths.get(expectedPath))) {
                return new String[]{"? Test case files not found at expected paths."};
            }

            // ✅ Updated: Ignore blank lines
            List<String> inputs = Files.readAllLines(Paths.get(inputPath)).stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .toList();

            List<String> expectedOutputs = Files.readAllLines(Paths.get(expectedPath)).stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .toList();

            List<String> actualOutputs = new ArrayList<>();
            List<String> results = new ArrayList<>();

            int total = Math.min(inputs.size(), expectedOutputs.size());
            int passedCount = 0;

            for (int i = 0; i < total; i++) {
                String input = inputs.get(i);
                String expected = expectedOutputs.get(i).trim();
                String actual = runUserCode(input).trim();
                actualOutputs.add(actual);

                if (actual.equals(expected)) {
                    results.add("Test Case " + (i + 1) + " Passed");
                    passedCount++;
                } else {
                    results.add("Test Case " + (i + 1) + " Failed");
                    results.add("   - Input: " + input);
                    results.add("   - Expected: " + expected);
                    results.add("   - Got: " + actual);
                }
            }

            // Write actual outputs to file
            Files.write(Paths.get(outputPath), actualOutputs);

            // Add summary
            results.add("");
            results.add("? Total Passed: " + passedCount + " / " + total);
            results.add(passedCount == total
                ? "testUser passed all test cases for " + questionId
                : "testUser failed some test cases for " + questionId);

            return results.toArray(new String[0]);

        } catch (Exception e) {
            return new String[]{"Error: " + e.getMessage()};
        }
    }

    private static void saveUserCode(String code) throws IOException {
        Files.write(Paths.get(SUBMISSION_PATH), code.getBytes());
    }

    private static boolean compileUserCode() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("javac", SUBMISSION_PATH);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        process.waitFor();
        return process.exitValue() == 0;
    }

    private static String runUserCode(String input) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", "main/submissions", "UserCode");
        pb.redirectErrorStream(true);
        Process process = pb.start();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        writer.write(input);
        writer.newLine();
        writer.flush();
        writer.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        process.waitFor();
        return output.toString().trim();
    }

    public static String executeSingleSnippet(String codeSnippet) {
        try {
            String code = "public class UserCode {\n" +
                          "    public static void main(String[] args) {\n" +
                          codeSnippet + "\n    }\n}";
            saveUserCode(code);
            if (!compileUserCode()) {
                return "Compilation failed.";
            }
            return runUserCode("").trim();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public static void loadAndRunCodingQuestion(String questionFile, Scanner sc) {
        String baseTestPath = "main/testcases/java/coding/easy";
        String questionId = questionFile.contains("q1") ? "q1" : "q2";
        CodeRunner.run(sc, "testUser", "Java Coding", questionFile, baseTestPath, 1, questionId);
    }
}
*/
package main.coding;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CodeExecutor {

    private static final String SUBMISSION_PATH = "main/submissions/UserCode.java";

    public static String[] loadAndRunWithPathOverride(String code, String questionFile, String testCaseBasePath, String questionId) {
        try {
            // Step 1: Save user code
            saveUserCode(code);

            // Step 2: Delete previously compiled class if it exists
            File compiledClass = new File("main/submissions/UserCode.class");
            if (compiledClass.exists()) {
                compiledClass.delete();
            }

            // Step 3: Compile user code
            if (!compileUserCode()) {
                return new String[]{"Compilation failed. Please check your syntax."};
            }

            // Step 4: Prepare test case paths
            String inputPath = testCaseBasePath + "/input/" + questionId + "_input.txt";
            String expectedPath = testCaseBasePath + "/expected/" + questionId + "_expected.txt";
            String outputPath = testCaseBasePath + "/output/" + questionId + "_output.txt";

            if (!Files.exists(Paths.get(inputPath)) || !Files.exists(Paths.get(expectedPath))) {
                return new String[]{"Test case files not found at expected paths."};
            }

            // Step 5: Load and clean test inputs and expected outputs
            List<String> inputs = Files.readAllLines(Paths.get(inputPath)).stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .toList();

            List<String> expectedOutputs = Files.readAllLines(Paths.get(expectedPath)).stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .toList();

            List<String> actualOutputs = new ArrayList<>();
            List<String> results = new ArrayList<>();

            int total = Math.min(inputs.size(), expectedOutputs.size());
            int passedCount = 0;

            for (int i = 0; i < total; i++) {
                String input = inputs.get(i);
                String expected = expectedOutputs.get(i);
                String actual = runUserCode(input).trim();
                actualOutputs.add(actual);

                if (actual.equals(expected)) {
                    results.add("Test Case " + (i + 1) + " Passed");
                    passedCount++;
                } else {
                    results.add("Test Case " + (i + 1) + " Failed");
                    results.add("   - Input: " + input);
                    results.add("   - Expected: " + expected);
                    results.add("   - Got: " + actual);
                }
            }

            // Step 6: Save actual output to file
            Files.write(Paths.get(outputPath), actualOutputs);

            // Step 7: Add clean summary
            results.add("");
            results.add("Total Passed: " + passedCount + " / " + total);

            if (passedCount == total) {
                results.add("testUser passed all test cases for " + questionId);
            } else {
                results.add("testUser failed some test cases for " + questionId);
            }

            return results.toArray(new String[0]);

        } catch (Exception e) {
            return new String[]{"Error: " + e.getMessage()};
        }
    }

    private static void saveUserCode(String code) throws IOException {
        Files.write(Paths.get(SUBMISSION_PATH), code.getBytes());
    }

    private static boolean compileUserCode() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("javac", SUBMISSION_PATH);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        process.waitFor();
        return process.exitValue() == 0;
    }

    private static String runUserCode(String input) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", "main/submissions", "UserCode");
        pb.redirectErrorStream(true);
        Process process = pb.start();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        writer.write(input);
        writer.newLine();
        writer.flush();
        writer.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        process.waitFor();
        return output.toString().trim();
    }

    public static String executeSingleSnippet(String codeSnippet) {
        try {
            String code = "public class UserCode {\n" +
                          "    public static void main(String[] args) {\n" +
                          codeSnippet + "\n    }\n}";

            saveUserCode(code);

            File compiledClass = new File("main/submissions/UserCode.class");
            if (compiledClass.exists()) compiledClass.delete();

            if (!compileUserCode()) {
                return "Compilation failed.";
            }

            return runUserCode("").trim();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public static void loadAndRunCodingQuestion(String questionFile, Scanner sc) {
        String subject = questionFile.contains("dsa") ? "dsa" : "java";
        String level = questionFile.contains("easy") ? "easy"
                        : questionFile.contains("medium") ? "medium"
                        : "hard";
        String questionId = questionFile.contains("q1") ? "q1" : "q2";

        String baseTestPath = "main/testcases/" + subject + "/coding/" + level;
        CodeRunner.run(sc, "testUser", subject.equals("java") ? "Java Coding" : "DSA Coding", questionFile, baseTestPath, 1, questionId);
    }
}
