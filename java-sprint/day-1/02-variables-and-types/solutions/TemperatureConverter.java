// TemperatureConverter.java - solution
// Run it with:  java TemperatureConverter.java

public class TemperatureConverter {

    static final double FREEZING_POINT_C = 0.0;

    static double celsiusToFahrenheit(double celsius) {
        // celsius is a double, so celsius * 9 is a double and the division
        // that follows is double division. No int division trap here.
        return celsius * 9 / 5 + 32;
    }

    static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    static boolean isFreezing(double celsius) {
        return celsius <= FREEZING_POINT_C;
    }

    public static void main(String[] args) {
        double parity = -40.0;
        double freezing = 0.0;
        double roomTemp = 25.0;
        double bodyTemp = 37.0;
        double boiling = 100.0;

        System.out.println(parity + " C = " + celsiusToFahrenheit(parity) + " F");
        System.out.println(freezing + " C = " + celsiusToFahrenheit(freezing) + " F");
        System.out.println(roomTemp + " C = " + celsiusToFahrenheit(roomTemp) + " F");
        System.out.println(bodyTemp + " C = " + celsiusToFahrenheit(bodyTemp) + " F");
        System.out.println(boiling + " C = " + celsiusToFahrenheit(boiling) + " F");

        System.out.println();
        System.out.println("212.0 F = " + fahrenheitToCelsius(212.0) + " C");
        System.out.println("98.6 F = " + fahrenheitToCelsius(98.6) + " C");

        System.out.println();
        System.out.println("Is -5.0 C freezing? " + isFreezing(-5.0));
        System.out.println("Is 18.0 C freezing? " + isFreezing(18.0));
    }
}
