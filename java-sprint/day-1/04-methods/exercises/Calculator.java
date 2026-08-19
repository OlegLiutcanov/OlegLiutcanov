// Calculator.java
//
// GOAL: one method per operation, plus one method that picks between them.
// This is what "decompose the problem" looks like on a small scale: four tiny
// methods that each do one obvious thing, and a fifth that routes work to
// them.
//
// Run it with:  java Calculator.java
//
// EXPECTED OUTPUT when you are finished:
//
// === Calculator ===
// 12.0 + 4.0 = 16.0
// 12.0 - 4.0 = 8.0
// 12.0 * 4.0 = 48.0
// 12.0 / 4.0 = 3.0
// 12.0 / 0.0 = NaN   <- guarded, not a crash
//
// The three-number overload
// add(1.5, 2.5, 3.0) = 7.0
//
// Dispatching through apply
// apply(9.0, '+', 3.0) = 12.0
// apply(9.0, '-', 3.0) = 6.0
// apply(9.0, '*', 3.0) = 27.0
// apply(9.0, '/', 3.0) = 3.0
// apply(9.0, '?', 3.0) = NaN
//
// About NaN: it stands for "not a number" and it is the double value Java uses
// for results that do not exist, like 0 divided by 0. Returning it is a polite
// way for a method to say "you asked me something impossible" without crashing
// the program.

public class Calculator {

    // TODO 1: return a + b.
    static double add(double a, double b) {
        return 0;
    }

    // TODO 2: return the sum of all three.
    // This is an OVERLOAD of add - same name, different number of parameters.
    // Java tells them apart by the call. Try writing the body as a single call
    // to the two-number version, so the actual addition lives in one place.
    static double add(double a, double b, double c) {
        return 0;
    }

    // TODO 3: return a - b.
    static double subtract(double a, double b) {
        return 0;
    }

    // TODO 4: return a * b.
    static double multiply(double a, double b) {
        return 0;
    }

    // TODO 5: return a / b, but guard against b being 0 first and return
    // Double.NaN in that case. Write the guard as an early return at the top.
    static double divide(double a, double b) {
        return 0;
    }

    // TODO 6: look at the operator and hand the work to the right method
    // above. Support '+', '-', '*' and '/'. For anything else, return
    // Double.NaN.
    //
    // An if / else if chain works perfectly here. (A switch also works, and
    // the solution file shows that version too.)
    //
    // Note the single quotes: 'x' is a char, one single character, while "x"
    // would be a String. Compare chars with ==, not with .equals().
    static double apply(double a, char operator, double b) {
        return 0;
    }

    // main is already written for you. Do not change it.
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
