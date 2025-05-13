package main.utils;

import main.database.ProgressDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestionHandler {

    // ✅ Inner Question class
    public static class Question {
        public String questionText;
        public String optionA, optionB, optionC, optionD;
        public String correctAnswer;

        public Question(String questionText, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
            this.questionText = questionText;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.correctAnswer = correctAnswer;
        }
    }

    // ✅ For OOSE_Module or any module that needs preloaded list
    public static List<Question> loadQuestions(String filePath) {
        List<Question> questions = new ArrayList<>();
        File file = new File(filePath);

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String question = fileScanner.nextLine().trim();
                if (question.isEmpty()) continue;

                String optionA = fileScanner.hasNextLine() ? fileScanner.nextLine() : "";
                String optionB = fileScanner.hasNextLine() ? fileScanner.nextLine() : "";
                String optionC = fileScanner.hasNextLine() ? fileScanner.nextLine() : "";
                String optionD = fileScanner.hasNextLine() ? fileScanner.nextLine() : "";
                String correctAnswerLine = fileScanner.hasNextLine() ? fileScanner.nextLine().trim().toUpperCase() : "";

                // ✅ Extract just the letter from "ANSWER: B" or similar
                String correctAnswer = correctAnswerLine.contains(":") ?
                        correctAnswerLine.split(":")[1].trim().substring(0, 1).toUpperCase() :
                        correctAnswerLine;

                questions.add(new Question(question, optionA, optionB, optionC, optionD, correctAnswer));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading questions: " + e.getMessage());
        }

        return questions;
    }

    // ✅ Interactive quiz mode
    public static void startInterviewQuiz(String filePath, Scanner sc, String username) {
        List<Question> questions = loadQuestions(filePath);
        int totalQuestions = questions.size();
        int correctAnswers = 0;

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);

            System.out.println("\nQ" + (i + 1) + ": " + q.questionText);
            System.out.println(q.optionA);
            System.out.println(q.optionB);
            System.out.println(q.optionC);
            System.out.println(q.optionD);
            System.out.print("Your answer (A/B/C/D): ");
            String userAnswer = sc.nextLine().trim().toUpperCase();

            if (userAnswer.equals(q.correctAnswer)) {
                System.out.println("Correct!");
                correctAnswers++;
            } else {
                System.out.println("Incorrect! Correct Answer: " + q.correctAnswer);
            }
        }

        System.out.println("\nQuiz Completed!");
        System.out.println("Correct: " + correctAnswers + " / " + totalQuestions);

        ProgressDatabase.updateProgress(username, "interview", filePath, correctAnswers, totalQuestions);
    }
}
