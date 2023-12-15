import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileUtility { // Utility class for file operations

    private static final String FILE_PATH = "game_history.csv"; // File path

    public static void saveRecord(GameRecord record) throws IOException {
        String recordString = record.toCSVString() + System.lineSeparator();
        Files.write(Paths.get(FILE_PATH), recordString.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public static List<GameRecord> loadRecordsFromFile() throws IOException {
        if (!Files.exists(Paths.get(FILE_PATH))) {
            return new ArrayList<>();
        }

        return Files.lines(Paths.get(FILE_PATH))
                .map(GameRecord::fromCSVString)
                .collect(Collectors.toList());
    }
}