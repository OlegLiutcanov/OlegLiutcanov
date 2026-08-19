// TemperatureConverter.java
//
// GOAL: fill in the three methods below so the conversions are correct.
// Run it with:  java TemperatureConverter.java
//
// EXPECTED OUTPUT when you are finished:
//
// -40.0 C = -40.0 F
// 0.0 C = 32.0 F
// 25.0 C = 77.0 F
// 37.0 C = 98.6 F
// 100.0 C = 212.0 F
//
// 212.0 F = 100.0 C
// 98.6 F = 37.0 C
//
// Is -5.0 C freezing? true
// Is 18.0 C freezing? false
//
// The formulas:
//   fahrenheit = celsius * 9 / 5 + 32
//   celsius    = (fahrenheit - 32) * 5 / 9
//
// WARNING: if you write the fractions as 9 / 5 or 5 / 9 on their own,
// Java divides two ints and you get 1 and 0. Keep at least one double
// in play. Run it and look at the numbers before you trust them.

public class TemperatureConverter {

    // The temperature at which water freezes, in Celsius.
    // 'final' means this value can never be reassigned.
    static final double FREEZING_POINT_C = 0.0;

    static double celsiusToFahrenheit(double celsius) {
        // TODO: return the temperature converted to Fahrenheit.
        return 0;
    }

    static double fahrenheitToCelsius(double fahrenheit) {
        // TODO: return the temperature converted to Celsius.
        return 0;
    }

    static boolean isFreezing(double celsius) {
        // TODO: return true when the temperature is at or below freezing.
        // Use FREEZING_POINT_C rather than typing 0.0 again.
        return false;
    }

    // main is already written for you. Do not change it.
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
