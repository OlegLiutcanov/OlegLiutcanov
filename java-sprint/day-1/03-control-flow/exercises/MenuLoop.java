// MenuLoop.java
//
// GOAL: show a little menu over and over until the user types quit.
//
// Run it with:  java MenuLoop.java
//
// EXPECTED SESSION. Lines starting with > are what you type; everything
// else is what the program prints. (The menu is printed before every
// prompt, so it appears six times in this session - only the first and
// last are spelled out here.)
//
//   === Toolbox ===
//     1) Greet me
//     2) Double a number
//     3) Count to five
//     quit) Leave
//   Choose: > 1
//   Hello there!
//   ...menu again...
//   Choose: > 2
//   Number: > 12
//   12 doubled is 24
//   ...menu again...
//   Choose: > 3
//   1 2 3 4 5
//   ...menu again...
//   Choose: > help
//   I do not know the option "help".
//   ...menu again...
//   Choose: > quit
//   Bye!
//
// You can also feed it answers without typing:
//   printf '1\n2\n12\n3\nhelp\nquit\n' | java MenuLoop.java
//
// As given, this compiles and runs and does absolutely nothing. Prove that,
// then build the loop.

import java.util.Scanner;

public class MenuLoop {

    // Written for you. Call it once per pass through the loop.
    static void printMenu() {
        System.out.println();
        System.out.println("=== Toolbox ===");
        System.out.println("  1) Greet me");
        System.out.println("  2) Double a number");
        System.out.println("  3) Count to five");
        System.out.println("  quit) Leave");
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // TODO 1: declare  boolean running = true;
        //
        // TODO 2: write  while (running) { ... }  and inside it:
        //           - call printMenu()
        //           - System.out.print("Choose: ")     <- print, not println
        //           - String choice = in.nextLine();
        //
        // TODO 3: react to the choice, with either an if / else if chain or a
        //         switch. Remember that text is compared with .equals(...) in
        //         an if, while a switch on a String compares for you.
        //           "1"    -> print  Hello there!
        //           "2"    -> print "Number: ", read a line, turn it into an
        //                     int with Integer.parseInt(...), print the double
        //           "3"    -> a for loop printing  1 2 3 4 5  on one line
        //           "quit" -> print  Bye!  and set running = false
        //           other  -> print  I do not know the option "help".
        //                     with whatever they typed in the quotes
        //
        // TODO 4: nothing after the loop but the line below - once running is
        //         false the while test fails and the program falls out here.

        in.close();
    }
}
