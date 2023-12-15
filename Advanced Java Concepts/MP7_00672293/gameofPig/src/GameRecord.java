import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class GameRecord { // GameRecord class
    private String name;
    private LocalDateTime date;
    private int score;
    private String result;

    // Default constructor
    public GameRecord() {
    }

    // Constructor with all fields
    public GameRecord(String name, LocalDateTime date, int score, String result) {
        this.name = name;
        this.date = date;
        this.score = score;
        this.result = result;
    }

    // Getters and setters for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getters and setters for date
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    // Getters and setters for score
    public int getScore() {
        return score;
    }

    public void setScore(int score) { // Setting the score
        this.score = score;
    }

    // Getters and setters for result
    public String getResult() {
        return result;
    }

    public void setResult(String result) { // Setting the result
        this.result = result;
    }

    // Method to serialize GameRecord String
    public String toCSVString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.join(",", name, date.format(formatter), String.valueOf(score), result);
    }

    // Static method to deserialize String to a GameRecord
    public static GameRecord fromCSVString(String csvString) {
        try {
            String[] parts = csvString.split(",");
            if (parts.length != 4) throw new IllegalArgumentException("Invalid record format");

            String name = parts[0];
            LocalDateTime date = LocalDateTime.parse(parts[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            int score = Integer.parseInt(parts[2]);
            String result = parts[3];

            return new GameRecord(name, date, score, result); // Return a new GameRecord object
        } catch (DateTimeParseException | NumberFormatException e) {
            e.printStackTrace();
            // Log error or notify the user
            return null; 
        }
    }
}