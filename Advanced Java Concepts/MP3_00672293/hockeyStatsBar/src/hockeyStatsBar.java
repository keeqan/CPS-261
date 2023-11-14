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

        // axes for the bar chart: NumberAxis for goals, CategoryAxis for teams
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        // initialize a bar chart with the specified axes
        final BarChart<Number, String> bc = new BarChart<>(xAxis, yAxis);

        // series to hold the data
        XYChart.Series series = new XYChart.Series();

        // close the BufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader("mp3_hockey_stats.txt"))) {
            String line;
            // Read 
            while ((line = br.readLine()) != null) {
                // Split each line 
                String[] parts = line.split(",");
                // Check if formatted 
                if (parts.length == 2) {
                    String team = parts[0].trim(); // Extract 
                    int goals = Integer.parseInt(parts[1].trim()); 
                    // Add data 
                    series.getData().add(new XYChart.Data(goals, team));
                }
            }
        } catch (IOException e) {
            // Handle exceptions 
            e.printStackTrace();
        }

        // Set the scene with the bar chart 
        Scene scene = new Scene(bc, 800, 600);
        // Add data to the bar chart
        bc.getData().add(series);

        // Display the stage
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}