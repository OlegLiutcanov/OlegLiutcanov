// NumberDrills.java - solution
// Run it with:  java NumberDrills.java

public class NumberDrills {

    static boolean isEven(int n) {
        // n % 2 == 1 would be wrong: -3 % 2 is -1 in Java, so odd negatives
        // would be reported as even. Comparing against 0 has no such problem.
        return n % 2 == 0;
    }

    static String evenOrOdd(int n) {
        if (isEven(n)) {
            return "even";
        } else {
            return "odd";
        }
    }

    static int sumOfDigits(int n) {
        int sum = 0;
        while (n > 0) {
            sum += n % 10;   // take the last digit
            n /= 10;         // and drop it
        }
        return sum;
    }

    static int countMultiplesOfThree(int limit) {
        int count = 0;
        for (int i = 1; i <= limit; i++) {
            if (i % 3 == 0) {
                count++;
            }
        }
        return count;
    }

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
