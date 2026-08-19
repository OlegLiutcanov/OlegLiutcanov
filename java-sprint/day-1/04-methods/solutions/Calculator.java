// Calculator.java - solution

public class Calculator {

    static double add(double a, double b) {
        return a + b;
    }

    // The overload delegates instead of repeating the '+'. If addition ever
    // needed to change, there would still be one place to change it.
    static double add(double a, double b, double c) {
        return add(add(a, b), c);
    }

    static double subtract(double a, double b) {
        return a - b;
    }

    static double multiply(double a, double b) {
        return a * b;
    }

    static double divide(double a, double b) {
        if (b == 0) {
            return Double.NaN;
        }
        return a / b;
    }

    // A switch expression: each arm produces the value that gets returned.
    // The equivalent if / else if chain is at the bottom of this file.
    static double apply(double a, char operator, double b) {
        return switch (operator) {
            case '+' -> add(a, b);
            case '-' -> subtract(a, b);
            case '*' -> multiply(a, b);
            case '/' -> divide(a, b);
            default -> Double.NaN;
        };
    }

    static double applyWithIfChain(double a, char operator, double b) {
        if (operator == '+') {
            return add(a, b);
        } else if (operator == '-') {
            return subtract(a, b);
        } else if (operator == '*') {
            return multiply(a, b);
        } else if (operator == '/') {
            return divide(a, b);
        }
        return Double.NaN;
    }

    public static void main(String[] args) {
        System.out.println("=== Calculator ===");
        System.out.println("12.0 + 4.0 = " + add(12.0, 4.0));
        System.out.println("12.0 - 4.0 = " + subtract(12.0, 4.0));
        System.out.println("12.0 * 4.0 = " + multiply(12.0, 4.0));
        System.out.println("12.0 / 4.0 = " + divide(12.0, 4.0));
        System.out.println("12.0 / 0.0 = " + divide(12.0, 0.0) + "   <- guarded, not a crash");

        System.out.println();
        System.out.println("The three-number overload");
        System.out.println("add(1.5, 2.5, 3.0) = " + add(1.5, 2.5, 3.0));

        System.out.println();
        System.out.println("Dispatching through apply");
        char[] operators = { '+', '-', '*', '/', '?' };
        for (char operator : operators) {
            System.out.println("apply(9.0, '" + operator + "', 3.0) = " + apply(9.0, operator, 3.0));
        }
    }
}
