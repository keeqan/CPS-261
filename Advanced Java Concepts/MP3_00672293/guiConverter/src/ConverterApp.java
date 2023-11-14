import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConverterApp extends Application {

    // Declare input and output 
    private Label inputLabel;
    private Label outputLabel;

    // Override the start method
    @Override
    public void start(Stage primaryStage) {
        // Create Buttons 
        RadioButton rbMilesKm = new RadioButton("Miles <-> Kilometers");
        RadioButton rbCelsiusFahrenheit = new RadioButton("Celsius <-> Fahrenheit");
        RadioButton rbPoundsKg = new RadioButton("Pounds <-> Kilograms");

        // Group the Buttons
        ToggleGroup conversionGroup = new ToggleGroup();
        rbMilesKm.setToggleGroup(conversionGroup);
        rbCelsiusFahrenheit.setToggleGroup(conversionGroup);
        rbPoundsKg.setToggleGroup(conversionGroup);

        // Add a listener to handle changes in conversion type selection.
        conversionGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> updateLabels(newVal));

        // Output field is not editable.
        TextField inputField = new TextField();
        TextField outputField = new TextField();
        outputField.setEditable(false);

        // Initialize labels 
        inputLabel = new Label("Input");
        outputLabel = new Label("Output");

        // Add an action when data is entered.
        inputField.setOnAction(e -> convert(conversionGroup, inputField, outputField));

        // Layout 
        VBox layout = new VBox(10, rbMilesKm, rbCelsiusFahrenheit, rbPoundsKg, inputLabel, inputField, outputLabel, outputField);
        Scene scene = new Scene(layout, 300, 250);

        // Set up stage with visibility settings.
        primaryStage.setTitle("Converter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to update input and output 
    private void updateLabels(Toggle newValue) {
        if (newValue instanceof RadioButton) {
            RadioButton selected = (RadioButton) newValue;
            switch (selected.getText()) {
                // Conversion type.
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

    // Method to perform the conversion 
    private void convert(ToggleGroup group, TextField input, TextField output) {
        try {
            // Get the selected conversion type.
            RadioButton selectedConversion = (RadioButton) group.getSelectedToggle();
            if (selectedConversion == null) {
                output.setText("Select a conversion type");
                return;
            }

            // input value to a double.
            double inputValue = Double.parseDouble(input.getText());
            double result = 0;

            // Perform conversion.
            switch (selectedConversion.getText()) {
                case "Miles <-> Kilometers":
                    // Convert miles and kilometers.
                    if (inputLabel.getText().startsWith("Mile")) {
                        result = ConversionLogic.convertMilesToKilometers(inputValue);
                    } else {
                        result = ConversionLogic.convertKilometersToMiles(inputValue);
                    }
                    break;
                case "Celsius <-> Fahrenheit":
                    // Convert Celsius and Fahrenheit.
                    if (inputLabel.getText().startsWith("Celsius")) {
                        result = ConversionLogic.convertCelsiusToFahrenheit(inputValue);
                    } else {
                        result = ConversionLogic.convertFahrenheitToCelsius(inputValue);
                    }
                    break;
                case "Pounds <-> Kilograms":
                    // Convert pounds and kilograms.
                    if (inputLabel.getText().startsWith("Pound")) {
                        result = ConversionLogic.convertPoundsToKilograms(inputValue);
                    } else {
                        result = ConversionLogic.convertKilogramsToPounds(inputValue);
                    }
                    break;
            }

            // Display two decimal places.
            output.setText(String.format("%.2f", result));
        } catch (NumberFormatException e) {
         // Handle invalid number 
            output.setText("Invalid input");
        }
    }

      // The main method 
    public static void main(String[] args) {
        launch(args);
    }
}