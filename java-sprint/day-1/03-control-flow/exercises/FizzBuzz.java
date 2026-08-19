// FizzBuzz.java
//
// GOAL: print the numbers 1 to 20, except:
//   - a number divisible by 3 becomes  Fizz
//   - a number divisible by 5 becomes  Buzz
//   - a number divisible by BOTH becomes FizzBuzz
//
// Run it with:  java FizzBuzz.java
//
// EXPECTED OUTPUT - exactly these 20 lines, nothing else:
//
// 1
// 2
// Fizz
// 4
// Buzz
// Fizz
// 7
// 8
// Fizz
// Buzz
// 11
// Fizz
// 13
// 14
// FizzBuzz
// 16
// 17
// Fizz
// 19
// Buzz
//
// As given, this file compiles and prints nothing at all. That is your
// green starting point. Run it once to prove it works, then fill in the TODOs.
//
// Two things you will need:
//   n % 3 == 0          is true when n divides evenly by 3
//   String.valueOf(n)   turns the number 7 into the text "7"

public class FizzBuzz {

    // Returns what should be printed for one number.
    static String fizzBuzzFor(int n) {
        // TODO 1: return "FizzBuzz" when n divides by both 3 and 5.
        // TODO 2: return "Fizz" when n divides by 3.
        // TODO 3: return "Buzz" when n divides by 5.
        // TODO 4: otherwise return the number itself as text.
        //
        // ORDER MATTERS. Think about which test has to come first, and why.
        return null;
    }

    public static void main(String[] args) {
        // TODO 5: write a for loop that counts from 1 to 20 and prints
        //         fizzBuzzFor(i) on its own line each time.
    }
}
