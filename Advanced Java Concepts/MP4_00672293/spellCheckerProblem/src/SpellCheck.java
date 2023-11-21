import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

public class SpellCheck {
    //  store the words 
    private HashSet<String> dictionary;
    //  store misspelled words 
    private TreeSet<String> missSpelledWords;

    // initialize the dictionary and misspelled words 
    public SpellCheck() throws FileNotFoundException {
        dictionary = new HashSet<>();
        missSpelledWords = new TreeSet<>();
        loadDictionary(); // Load words from the dictionary file.
    }

    // Method to load words 
    private void loadDictionary() throws FileNotFoundException {
        Scanner dictScanner = new Scanner(new File("dictionary.txt"));
        while (dictScanner.hasNext()) {
            dictionary.add(dictScanner.next().toLowerCase());
        }
        dictScanner.close();
    }

    // Method to check the spelling 
    public void checkSpelling(String fileName) throws FileNotFoundException {
        System.out.println("======== Spell checking " + fileName + " =========");
        missSpelledWords.clear();
        Scanner fileScanner = new Scanner(new File(fileName));

        int lineNumber = 1; 
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] words = line.split(" +|\\p{Punct}"); // Splitting into words.
            boolean lineContainsMisspelledWord = false;

            for (String word : words) {
                word = word.toLowerCase();
                if (!word.matches("^[a-z].*")) continue; // Skip non word strings.

                // Check if the word is misspelled and identified.
                if (!dictionary.contains(word) && !missSpelledWords.contains(word)) {
                    if (!lineContainsMisspelledWord) {
                        System.out.println("line number:" + lineNumber);
                        lineContainsMisspelledWord = true;
                    }
                    handleMisspelledWord(word); // misspelled word.
                }
            }

            if (lineContainsMisspelledWord) {
                lineNumber++;
            }
        }

        fileScanner.close();
    }

    //  handle a misspelled word 
    private void handleMisspelledWord(String word) {
        Scanner userInput = new Scanner(System.in);
        System.out.println(word + " add to dictionary? y or n");
        String choice = userInput.nextLine();

        if ("y".equalsIgnoreCase(choice)) {
            dictionary.add(word); // Add to dictionary 
        } else {
            missSpelledWords.add(word); // Add to misspelled 
        }
    }

    // Method to display misspelled 
    public void dumpMissSpelledWords() {
        System.out.println("======miss spelled words======");
        for (String word : missSpelledWords) {
            System.out.println(word);
        }
    }

    public static void main(String[] args) {
        try {
            SpellCheck spellCheck = new SpellCheck(); //  SpellCheck instance.

            for (String arg : args) {
                spellCheck.checkSpelling(arg); // Check spelling for each file.
                spellCheck.dumpMissSpelledWords(); // Display misspelled words.
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}