import java.util.Arrays;

public class ArraySorter {
    public static void main(String[] args) {
        int[][] numberArray = {{34, 89}, {56, 3}, {27, 61}, {45, 8}, {45, 89}};

        String result = Arrays.stream(numberArray)
                              .flatMapToInt(Arrays::stream) // Flattening 
                              .distinct()                   // Get values
                              .sorted()                     // Sort
                              .mapToObj(String::valueOf)    // Convert
                              .reduce((a, b) -> a + " " + b) // Reduce
                              .orElse("");

        System.out.println(result);
    }
}