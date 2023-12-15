import java.io.IOException;
import java.util.List;

public class GameStatistics { // Utility class for game statistics

    public static long countWinsForPlayer(String playerName) throws IOException {
        List<GameRecord> records = FileUtility.loadRecordsFromFile();
        if (records == null) {
            // Handle the null case, maybe throw an exception or return 0
            throw new IOException("Failed to load records from file.");
        }
        return records.stream() // Stream of GameRecord objects
                      .filter(r -> r.getName().equals(playerName) && r.getResult().equals("Win"))
                      .count();
    }
}