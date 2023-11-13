// Import necessary JavaFX classes for GUI development.
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Define a new class `ConverterApp` extending JavaFX `Application` for GUI functionality.
public class ConverterApp extends Application {

    // Declare labels for input and output fields.
    private Label inputLabel;
    private Label outputLabel;

    // Override the start method, which is the main entry point for JavaFX applications.
    @Override
    public void start(Stage primaryStage) {
        // Create RadioButtons for different types of conversions.
        RadioButton rbMilesKm = new RadioButton("Miles <-> Kilometers");
        RadioButton rbCelsiusFahrenheit = new RadioButton("Celsius <-> Fahrenheit");
        RadioButton rbPoundsKg = new RadioButton("Pounds <-> Kilograms");

        // Group the RadioButtons together so only one can be selected at a time.
        ToggleGroup conversionGroup = new ToggleGroup();
        rbMilesKm.setToggleGroup(conversionGroup);
        rbCelsiusFahrenheit.setToggleGroup(conversionGroup);
        rbPoundsKg.setToggleGroup(conversionGroup);

        // Add a listener to handle changes in conversion type selection.
        conversionGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> updateLabels(newVal));

        // Create text fields for input and output; output field is not editable.
        TextField inputField = new TextField();
        TextField outputField = new TextField();
        outputField.setEditable(false);

        // Initialize labels for input and output fields.
        inputLabel = new Label("Input");
        outputLabel = new Label("Output");

        // Add an action on the input field to trigger conversion when data is entered.
        inputField.setOnAction(e -> convert(conversionGroup, inputField, outputField));

        // Layout the UI components vertically using VBox.
        VBox layout = new VBox(10, rbMilesKm, rbCelsiusFahrenheit, rbPoundsKg, inputLabel, inputField, outputLabel, outputField);
        Scene scene = new Scene(layout, 300, 250);

        // Set up the primary stage with title, scene, and visibility settings.
        primaryStage.setTitle("Converter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to update input and output labels based on selected conversion type.
    private void updateLabels(Toggle newValue) {
        if (newValue instanceof RadioButton) {
            RadioButton selected = (RadioButton) newValue;
            switch (selected.getText()) {
                // Change labels depending on conversion type.
                case "Miles <-> Kilometers":
                    inputLabel.setText("Mile/Kilometer");
                    outputLabel.setText("Kilometer/Mile");
                    break;
                case "Celsius <-> Fahrenheit":
                    inputLabel.setText("Celsius/Fahrenheit");
                    outputLabel.setText("Fahrenheit/Celsius");
                    break;
                case "Pounds <-> Kilograms":
                    inputLabel.setText("Pound/Kilogram");
                    outputLabel.setText("Kilogram/Pound");
                    break;
                default:
                    inputLabel.setText("Input");
                    outputLabel.setText("Output");
            }
        }
    }

    // Method to perform the conversion based on selected type and input value.
    private void convert(ToggleGroup group, TextField input, TextField output) {
        try {
            // Get the selected conversion type.
            RadioButton selectedConversion = (RadioButton) group.getSelectedToggle();
            if (selectedConversion == null) {
                output.setText("Select a conversion type");
                return;
            }

            // Parse the input value to a double.
            double inputValue = Double.parseDouble(input.getText());
            double result = 0;

            // Perform the appropriate conversion.
            switch (selectedConversion.getText()) {
                case "Miles <-> Kilometers":
                    // Convert between miles and kilometers.
                    if (inputLabel.getText().startsWith("Mile")) {
                        result = ConversionLogic.convertMilesToKilometers(inputValue);
                    } else {
                        result = ConversionLogic.convertKilometersToMiles(inputValue);
                    }
                    break;
                case "Celsius <-> Fahrenheit":
                    // Convert between Celsius and Fahrenheit.
                    if (inputLabel.getText().startsWith("Celsius")) {
                        result = ConversionLogic.convertCelsiusToFahrenheit(inputValue);
                    } else {
                        result = ConversionLogic.convertFahrenheitToCelsius(inputValue);
                    }
                    break;
                case "Pounds <-> Kilograms":
                    // Convert between pounds and kilograms.
                    if (inputLabel.getText().startsWith("Pound")) {
                        result = ConversionLogic.convertPoundsToKilograms(inputValue);
                    } else {
                        result = ConversionLogic.convertKilogramsToPounds(inputValue);
                    }
                    break;
            }

            // Display the result formatted to two decimal places.
            output.setText(String.format("%.2f", result));
        } catch (NumberFormatException e) {
         // Handle invalid number format exception by displaying an error message.
            output.setText("Invalid input");
        }
    }

      // The main method to launch the JavaFX application.
    public static void main(String[] args) {
        launch(args);
    }
}