// RetryUntilValid.java
// Catch-and-recover in its most useful everyday form: keep asking until the
// answer makes sense. A person typing nonsense is not a bug in your program,
// so crashing would be the wrong response.
//
// Run it with:  java RetryUntilValid.java
//
// Or feed it answers without typing:
//   printf 'abc\n999\n  36  \n' | java RetryUntilValid.java

import java.util.Scanner;

public class RetryUntilValid {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("=== Sign-up ===");
        int age = readAge(in, "How old are you? ");

        System.out.println();
        System.out.println("Registered with age " + age + ".");

        in.close();
    }

    // Loops until it can return a number it trusts. Two different kinds of bad
    // input are handled here, and they are handled differently on purpose:
    //   - text that is not a number at all  -> parseInt throws, we catch it
    //   - a number that is out of range     -> no exception, just an if
    // Reach for an exception when something failed; reach for an if when you
    // can simply check first.
    static int readAge(Scanner in, String prompt) {
        while (true) {
            System.out.print(prompt);

            // Guard against input running out, so piping a file cannot spin
            // this loop forever.
            if (!in.hasNextLine()) {
                System.out.println();
                System.out.println("  no more input - defaulting to 0");
                return 0;
            }

            String line = in.nextLine().trim();

            try {
                int age = Integer.parseInt(line);

                if (age < 0 || age > 130) {
                    System.out.println("  " + age + " is not a plausible age. Please enter 0 to 130.");
                    continue;               // back to the top, ask again
                }

                return age;                 // the only way out with a real answer

            } catch (NumberFormatException e) {
                System.out.println("  \"" + line + "\" is not a whole number. Please try again.");
                // no 'continue' needed - falling off the end of the catch block
                // lands us back at the top of the while loop anyway
            }
        }
    }
}
