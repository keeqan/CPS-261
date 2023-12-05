import java.util.*;
import java.util.stream.Collectors;

public class ScrabbleScoreStats {

            // Static map to store the letter values for Scrabble.
    private static final Map<Character, Integer> letterValues = new HashMap<>();

    static {
        letterValues.put('a', 1);
        letterValues.put('b', 3);
        letterValues.put('c', 3);
        letterValues.put('d', 2);
        letterValues.put('e', 1);
        letterValues.put('f', 4);
        letterValues.put('g', 2);
        letterValues.put('h', 4);
        letterValues.put('i', 1);
        letterValues.put('j', 8);
        letterValues.put('k', 5);
        letterValues.put('l', 1);
        letterValues.put('m', 3);
        letterValues.put('n', 1);
        letterValues.put('o', 1);
        letterValues.put('p', 3);
        letterValues.put('q', 10);
        letterValues.put('r', 1);
        letterValues.put('s', 1);
        letterValues.put('t', 1);
        letterValues.put('u', 1);
        letterValues.put('v', 8);
        letterValues.put('w', 4);
        letterValues.put('x', 8);
        letterValues.put('y', 4);
        letterValues.put('z', 10);
       
    }

    // Calculate the Scrabble score of a word
    private static int calculateScrabbleScore(String word) {
        return word.toLowerCase().chars()
                .map(c -> letterValues.getOrDefault((char) c, 0))
                .sum();
    }

    public static void main(String[] args) {
        List<String> words = Arrays.asList("Java", "program", "list", "string", "unix", "hours", "syntax", "error");

        // Calculate scores for each word
        Map<String, Integer> scores = words.stream()
                .collect(Collectors.toMap(word -> word, ScrabbleScoreStats::calculateScrabbleScore));

        // Display top three
        System.out.println("Top three words are:");
        scores.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .forEach(entry -> System.out.println(entry.getKey() + ":" + entry.getValue()));

        // Calculate average
        double sumOfScores = scores.values().stream()
                .reduce(0, Integer::sum);
        double averageScore = sumOfScores / scores.size();
        System.out.println("Average scrabble value is: " + averageScore);

        // Classify words on average
        List<String> wordsBelowAverage = scores.entrySet().stream()
                .filter(entry -> entry.getValue() < averageScore)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
                
        List<String> wordsAboveAverage = scores.entrySet().stream()
                .filter(entry -> entry.getValue() > averageScore)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        System.out.println("Words below average: " + wordsBelowAverage);
        System.out.println("Words above average: " + wordsAboveAverage);
    }
}