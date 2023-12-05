import java.util.Random;
import java.util.stream.IntStream;
import java.util.function.Function;
import java.util.Map;
import java.util.stream.Collectors;

public class CountSingleDigits {
    public static void main(String[] args) {
        // Object for generating 
        Random random = new Random();

        // Generate 100 random integers between 0 and 9
        IntStream randomNumbers = random.ints(100, 0, 10);

        // Collect into map
        Map<Integer, Long> frequencyMap = randomNumbers.boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Display count 
        for (int i = 0; i < 10; i++) {
            System.out.println("Number " + i + " appears " + frequencyMap.getOrDefault(i, 0L) + " times.");
        }

    }
}