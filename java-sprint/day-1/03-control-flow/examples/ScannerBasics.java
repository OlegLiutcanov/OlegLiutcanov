// ScannerBasics.java
// Reading what someone types, and the one trap that catches every beginner.
// Run it with:  java ScannerBasics.java
//
// Then answer the three questions. To feed it answers without typing:
//   printf 'Ada\n36\nviolet\n' | java ScannerBasics.java

import java.util.Scanner;   // must sit above the class, at the top of the file

public class ScannerBasics {

    public static void main(String[] args) {

        // One Scanner, wired to System.in - the keyboard.
        Scanner in = new Scanner(System.in);

        // print (not println) leaves the cursor on the same line, so the
        // answer appears right after the question.
        System.out.print("What is your name? ");
        String name = in.nextLine();          // reads the whole line of text

        System.out.print("How old are you? ");
        int age = in.nextInt();               // reads a whole number ONLY

        // THE TRAP: nextInt() took the digits and stopped. The invisible
        // newline you made by pressing Enter is still sitting there waiting.
        // Without the line below, the next nextLine() would grab that leftover
        // newline, come back empty, and never pause for you at all.
        in.nextLine();                        // throw the leftover newline away

        System.out.print("Favourite colour? ");
        String colour = in.nextLine();

        System.out.println();
        System.out.println("Hello, " + name + "!");
        System.out.println("You are " + age + ", so next year you turn " + (age + 1) + ".");
        System.out.println("Noted: " + colour + " is a good colour.");

        // Text that holds digits is not a number yet. Integer.parseInt turns
        // "36" into 36 so you can do arithmetic with it.
        String typedNumber = "100";
        int parsed = Integer.parseInt(typedNumber);
        System.out.println("Integer.parseInt(\"100\") + " + age + " = " + (parsed + age));

        in.close();   // polite tidying up when the program is finished with input
    }
}
