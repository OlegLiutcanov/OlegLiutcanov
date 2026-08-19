// NumberDrills.java
//
// GOAL: fill in four small methods. Each one is a few lines. main is already
// written and must not be changed.
//
// Run it with:  java NumberDrills.java
//
// EXPECTED OUTPUT when you are finished:
//
// --- even or odd ---
// 7 is odd
// 10 is even
// 0 is even
// -3 is odd
//
// --- sum of digits ---
// sumOfDigits(5) = 5
// sumOfDigits(42) = 6
// sumOfDigits(1024) = 7
// sumOfDigits(9999) = 36
// sumOfDigits(0) = 0
//
// --- multiples of three ---
// from 1 to 10 there are 3
// from 1 to 30 there are 10
// from 1 to 2 there are 0
//
// As given, this compiles and runs - it just prints the wrong answers.
// Run it once and look at them, then start fixing.

public class NumberDrills {

    static boolean isEven(int n) {
        // TODO 1: return true when n is even.
        // Careful: -3 must come out as odd. Test for a remainder of ZERO
        // rather than a remainder of one, and negatives take care of
        // themselves.
        return false;
    }

    static String evenOrOdd(int n) {
        // TODO 2: return "even" or "odd". Call isEven rather than repeating
        // the arithmetic - one rule should live in one place.
        return null;
    }

    static int sumOfDigits(int n) {
        // TODO 3: add up the digits of n. Assume n is zero or positive.
        // The trick, applied in a loop until nothing is left:
        //     n % 10   is the last digit
        //     n / 10   is n with the last digit chopped off (whole-number division)
        return 0;
    }

    static int countMultiplesOfThree(int limit) {
        // TODO 4: count how many numbers from 1 up to and including limit
        // divide evenly by 3. A for loop plus an if is all this needs.
        return 0;
    }

    // main is written for you. Do not change it.
    public static void main(String[] args) {
        System.out.println("--- even or odd ---");
        System.out.println("7 is " + evenOrOdd(7));
        System.out.println("10 is " + evenOrOdd(10));
        System.out.println("0 is " + evenOrOdd(0));
        System.out.println("-3 is " + evenOrOdd(-3));

        System.out.println();
        System.out.println("--- sum of digits ---");
        System.out.println("sumOfDigits(5) = " + sumOfDigits(5));
        System.out.println("sumOfDigits(42) = " + sumOfDigits(42));
        System.out.println("sumOfDigits(1024) = " + sumOfDigits(1024));
        System.out.println("sumOfDigits(9999) = " + sumOfDigits(9999));
        System.out.println("sumOfDigits(0) = " + sumOfDigits(0));

        System.out.println();
        System.out.println("--- multiples of three ---");
        System.out.println("from 1 to 10 there are " + countMultiplesOfThree(10));
        System.out.println("from 1 to 30 there are " + countMultiplesOfThree(30));
        System.out.println("from 1 to 2 there are " + countMultiplesOfThree(2));
    }
}
