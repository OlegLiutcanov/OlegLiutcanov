// MethodBasics.java
// The anatomy of a method: return type, name, parameters, body - plus the
// difference between a method that hands a value back and one that only does
// something.
// Run it with:  java MethodBasics.java

public class MethodBasics {

    // The word 'int' in front of the name is the RETURN TYPE. It promises
    // "calling this gives you back an int". The promise is enforced: leave out
    // the return statement and the file will not compile.
    static int square(int n) {
        return n * n;
    }

    // 'void' means "there is no answer to hand back". This method is worth
    // calling for what it DOES, not for what it gives you.
    static void printBanner(String title) {
        System.out.println("=== " + title + " ===");
    }

    // Two parameters. Each one needs its own type, even when the types match -
    // (int a, b) is not valid Java.
    static int biggerOf(int a, int b) {
        if (a > b) {
            return a;    // an early return: the method stops right here
        }
        return b;
    }

    // A guard clause handles the awkward case first and gets out, so the rest
    // of the method can assume everything is normal.
    static double safeAverage(int total, int count) {
        if (count == 0) {
            return 0.0;
        }
        return (double) total / count;
    }

    // Methods call other methods freely. main has no special power here.
    static int sumOfSquares(int a, int b) {
        return square(a) + square(b);
    }

    public static void main(String[] args) {
        printBanner("Method basics");

        // The 5 below is an ARGUMENT: the actual value handed over at the call.
        // The n inside square is the PARAMETER: the name the method uses for
        // whatever it was handed.
        System.out.println("square(5)          = " + square(5));
        System.out.println("square(12)         = " + square(12));

        // A returned value can go into a variable...
        int area = square(9);
        System.out.println("area               = " + area);

        // ...or straight into a bigger expression.
        System.out.println("square(3) + 1      = " + (square(3) + 1));

        // ...or into another call. The inner call runs first.
        System.out.println("square(square(2))  = " + square(square(2)));

        System.out.println("biggerOf(4, 9)     = " + biggerOf(4, 9));
        System.out.println("sumOfSquares(3, 4) = " + sumOfSquares(3, 4));

        System.out.println("safeAverage(10, 4) = " + safeAverage(10, 4));
        System.out.println("safeAverage(10, 0) = " + safeAverage(10, 0)
                + "   <- no crash, the guard caught it");

        printBanner("Done");
    }
}
