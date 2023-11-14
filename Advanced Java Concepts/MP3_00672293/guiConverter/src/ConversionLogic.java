public class ConversionLogic {

    public static double convertMilesToKilometers(double miles) {
        return miles * 1.60934;
    }

    public static double convertKilometersToMiles(double kilometers) {
        return kilometers / 1.60934;
    }

    public static double convertCelsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    public static double convertFahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    public static double convertPoundsToKilograms(double pounds) {
        return pounds * 0.45359237;
    }

    public static double convertKilogramsToPounds(double kilograms) {
        return kilograms / 0.45359237;
    }
}