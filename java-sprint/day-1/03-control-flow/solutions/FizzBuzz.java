// FizzBuzz.java - solution
// Run it with:  java FizzBuzz.java

public class FizzBuzz {

    static String fizzBuzzFor(int n) {
        // The both-at-once test has to come first. If "n % 3 == 0" were on
        // top, 15 would match it and return "Fizz" before anything else
        // got a chance to look at it.
        if (n % 3 == 0 && n % 5 == 0) {
            return "FizzBuzz";
        } else if (n % 3 == 0) {
            return "Fizz";
        } else if (n % 5 == 0) {
            return "Buzz";
        } else {
            return String.valueOf(n);
        }
    }

    public static void main(String[] args) {
        // i <= 20, not i < 20, because 20 itself must be printed.
        for (int i = 1; i <= 20; i++) {
            System.out.println(fizzBuzzFor(i));
        }
    }
}
