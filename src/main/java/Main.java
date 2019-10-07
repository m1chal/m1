import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File selectedCategory = selectCategory();
        List<QuizChallenge> challenges = readChallengesFromFile(selectedCategory);
        Collections.shuffle(challenges);
        int points = 0;
        for (int i = 0; i < 10; i++) {
            QuizChallenge currentChallenge = challenges.get(i);
            points += askQuestion(currentChallenge);
        }
        System.out.println("Congratulations! You gained " + points + " points!");
    }

    private static int askQuestion(QuizChallenge currentChallenge) {
        System.out.println("Question: " + currentChallenge.getQuestion());
        List<String> answers = new ArrayList<>(currentChallenge.getAnswers());
        Collections.shuffle(answers);
        for (int j = 0; j < answers.size(); j++) {
            String answer = answers.get(j);
            System.out.println("  " + (j+1) + "] " + answer);
        }
        Scanner scannerIn = new Scanner(System.in);
        int userChoice = scannerIn.nextInt();
        String userAnswer = answers.get(userChoice - 1);

        if (userAnswer.equals(currentChallenge.getCorrectAnswer())) {
            System.out.println("Correct answer!");
            return 1;
        } else {
            System.out.println("Incorrect answer! The correct answer is: " + currentChallenge.getCorrectAnswer());
            return 0;
        }
    }

    private static File selectCategory() {
        File folder = new File("src/main/resources");
        File[] categoryFile = folder.listFiles();

        for (int i = 0; i < categoryFile.length; i++) {
            System.out.println((i+1) + ") " + categoryFile[i].getName()
                    .replace(".txt", "")
                    .replaceAll("_", " "));
        }
        System.out.println("Select category: ");
        Scanner scannerIn = new Scanner(System.in);
        int userCategoryChoice = scannerIn.nextInt();
        File selectedCategory = categoryFile[userCategoryChoice - 1];

        System.out.println("Your selected category is: " + selectedCategory.getName());
        return selectedCategory;
    }

    private static List<QuizChallenge> readChallengesFromFile(File categoryFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(categoryFile);
        List<QuizChallenge> challengesForTheCategory = new ArrayList<QuizChallenge>();
        while (scanner.hasNextLine()) {
            String question = scanner.nextLine();
            String numberOfAnswersAsString = scanner.nextLine();
            int numberOfAnswers = Integer.parseInt(numberOfAnswersAsString);
            List<String> answers = new ArrayList<String>(numberOfAnswers);
            for (int i = 0; i < numberOfAnswers; i++) {
                String answer = scanner.nextLine();
                answers.add(answer);
            }

            QuizChallenge oneChallenge = new QuizChallenge(question, answers);
            challengesForTheCategory.add(oneChallenge);
        }
        return challengesForTheCategory;
    }
}