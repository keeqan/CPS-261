import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class hockeyStatsBar extends Application {

    @Override
    public void start(Stage stage) {
        // Set the title of the application window
        stage.setTitle("Hockey Stats Bar ");

        // Create the axes for the bar chart: NumberAxis for goals, CategoryAxis for teams
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        // Initialize a bar chart with the specified axes
        final BarChart<Number, String> bc = new BarChart<>(xAxis, yAxis);

        // Create a series to hold the data
        XYChart.Series series = new XYChart.Series();

        // Try-with-resources to automatically close the BufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader("mp3_hockey_stats.txt"))) {
            String line;
            // Read the file line by line
            while ((line = br.readLine()) != null) {
                // Split each line into parts: assumed format "Team,Goals"
                String[] parts = line.split(",");
                // Check if the line is correctly formatted with 2 parts
                if (parts.length == 2) {
                    String team = parts[0].trim(); // Extract the team name
                    int goals = Integer.parseInt(parts[1].trim()); // Parse the goals as an integer
                    // Add data to the series: number of goals and corresponding team
                    series.getData().add(new XYChart.Data(goals, team));
                }
            }
        } catch (IOException e) {
            // Handle exceptions related to file reading
            e.printStackTrace();
        }

        // Set the scene with the bar chart and specified dimensions
        Scene scene = new Scene(bc, 800, 600);
        // Add the series with data to the bar chart
        bc.getData().add(series);

        // Display the stage with the scene containing the bar chart
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}