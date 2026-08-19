// TemperatureStats.java
// The same program written twice: once as one long main, once as a handful of
// small named methods.
//
// The two versions print exactly the same thing. That is the whole point -
// refactoring changes the shape of code, never what it does.
//
// Run it with:  java TemperatureStats.java

public class TemperatureStats {

    static final double[] READINGS = { 12.4, 15.0, 9.8, 21.3, 18.7, 7.2, 24.1 };
    static final double WARM_THRESHOLD = 18.0;

    // ==================================================================
    // BEFORE - one method doing five jobs at once
    // ==================================================================
    // Nothing here is wrong. It works. But to answer "how is the average
    // calculated?" you have to read the whole loop, and there is no name
    // anywhere in this method that says "average" until the very end.
    static void theHardWay() {
        double lowest = READINGS[0];
        double highest = READINGS[0];
        double total = 0.0;
        int warmDays = 0;

        for (double reading : READINGS) {
            if (reading < lowest) {
                lowest = reading;
            }
            if (reading > highest) {
                highest = reading;
            }
            total = total + reading;
            if (reading >= WARM_THRESHOLD) {
                warmDays = warmDays + 1;
            }
        }

        double average = total / READINGS.length;
        double range = highest - lowest;

        System.out.println("Readings taken : " + READINGS.length);
        System.out.println("Lowest         : " + Math.round(lowest * 10) / 10.0);
        System.out.println("Highest        : " + Math.round(highest * 10) / 10.0);
        System.out.println("Average        : " + Math.round(average * 10) / 10.0);
        System.out.println("Range          : " + Math.round(range * 10) / 10.0);
        System.out.println("Warm days      : " + warmDays + "  (" + WARM_THRESHOLD + " C or above)");
    }

    // ==================================================================
    // AFTER - one method per idea
    // ==================================================================

    static double lowestOf(double[] values) {
        double lowest = values[0];
        for (double value : values) {
            if (value < lowest) {
                lowest = value;
            }
        }
        return lowest;
    }

    static double highestOf(double[] values) {
        double highest = values[0];
        for (double value : values) {
            if (value > highest) {
                highest = value;
            }
        }
        return highest;
    }

    static double averageOf(double[] values) {
        if (values.length == 0) {
            return 0.0;
        }
        double total = 0.0;
        for (double value : values) {
            total = total + value;
        }
        return total / values.length;
    }

    static double rangeOf(double[] values) {
        return highestOf(values) - lowestOf(values);
    }

    static int countAtOrAbove(double[] values, double threshold) {
        int matches = 0;
        for (double value : values) {
            if (value >= threshold) {
                matches = matches + 1;
            }
        }
        return matches;
    }

    // The rounding expression appeared four times above. Once it has a name it
    // appears once, and if the rule ever changes there is one place to edit.
    static double roundedToOneDecimal(double value) {
        return Math.round(value * 10) / 10.0;
    }

    // A void helper whose only job is to keep the printing tidy.
    static void printStat(String label, double value) {
        System.out.println(label + " : " + roundedToOneDecimal(value));
    }

    static void theClearWay() {
        System.out.println("Readings taken : " + READINGS.length);
        printStat("Lowest        ", lowestOf(READINGS));
        printStat("Highest       ", highestOf(READINGS));
        printStat("Average       ", averageOf(READINGS));
        printStat("Range         ", rangeOf(READINGS));
        System.out.println("Warm days      : " + countAtOrAbove(READINGS, WARM_THRESHOLD)
                + "  (" + WARM_THRESHOLD + " C or above)");
    }

    public static void main(String[] args) {
        System.out.println("----- BEFORE: everything inside one main -----");
        theHardWay();

        System.out.println();
        System.out.println("----- AFTER: small methods with names -----");
        theClearWay();

        System.out.println();
        System.out.println("Identical output, very different code.");
        System.out.println("The second version also answers questions on its own:");
        System.out.println("  averageOf(READINGS) reads like the thing it computes.");
        System.out.println("And its pieces are reusable - here they are on new data:");

        double[] classGrades = { 72.0, 88.5, 91.0, 64.5, 79.0 };
        System.out.println("  grade average : " + roundedToOneDecimal(averageOf(classGrades)));
        System.out.println("  grades over 80: " + countAtOrAbove(classGrades, 80.0));
    }
}
