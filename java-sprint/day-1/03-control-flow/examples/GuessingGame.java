// GuessingGame.java
// Everything from this module in one small program: a loop, a chain of
// conditions, a break, and keyboard input.
// Run it with:  java GuessingGame.java
//
// To feed it guesses without typing:
//   printf '50\n25\n37\n' | java GuessingGame.java

import java.util.Random;
import java.util.Scanner;

public class GuessingGame {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        // new Random(7) always produces the same sequence, so the secret is
        // the same every run while you are testing. Swap it for new Random()
        // - no number inside - once you want a real game.
        Random random = new Random(7);
        int secret = random.nextInt(100) + 1;   // nextInt(100) gives 0..99, so +1 gives 1..100

        int maxAttempts = 5;
        boolean won = false;

        System.out.println("I am thinking of a number between 1 and 100.");
        System.out.println("You have " + maxAttempts + " guesses.");
        System.out.println();

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            System.out.print("Guess " + attempt + ": ");
            int guess = in.nextInt();

            if (guess == secret) {
                System.out.println("Correct! " + secret + " it was, in " + attempt + " guesses.");
                won = true;
                break;                       // no point running the rest of the loop
            } else if (guess < secret) {
                System.out.println("  Too low.");
            } else {
                System.out.println("  Too high.");
            }

            // A helpful nudge on the last attempt only.
            if (attempt == maxAttempts - 1) {
                System.out.println("  One guess left!");
            }
        }

        System.out.println();
        if (won) {
            System.out.println("Well played.");
        } else {
            System.out.println("Out of guesses. The number was " + secret + ".");
        }

        in.close();
    }
}
