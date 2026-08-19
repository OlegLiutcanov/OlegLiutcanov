// Comments are notes for humans. The compiler skips them entirely.
// Run it with:  java CommentTour.java

/*
 * This is a block comment. It starts with slash-star and ends with star-slash,
 * and it can run across as many lines as you like. The stars at the start of
 * each line are just a habit for looks - they are not required.
 */
public class CommentTour {
    public static void main(String[] args) {
        System.out.println("1. This line runs.");

        // System.out.println("2. This line does NOT run.");
        // A // comment kills everything to the end of that line. Putting // in
        // front of a line to switch it off is called "commenting out" code, and
        // you will do it constantly while hunting down a bug.

        /* System.out.println("3. This one is switched off too."); */

        System.out.println("4. This line runs.");

        System.out.println("5. Same line, trailing note."); // a comment can sit after real code

        /*
        System.out.println("6. A block comment can switch off");
        System.out.println("   several lines at once.");
        */

        System.out.println("Only lines 1, 4 and 5 printed - the rest were comments.");
    }
}
