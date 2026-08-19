// CountdownAndTables.java
// while, do-while, for, and a nested loop that draws a times table.
// Run it with:  java CountdownAndTables.java

public class CountdownAndTables {

    public static void main(String[] args) {

        // ---- while: test first, then maybe run the body ----
        System.out.println("Countdown with while:");
        int count = 5;
        while (count > 0) {
            System.out.println("  " + count + "...");
            count--;              // shorthand for: count = count - 1
        }
        System.out.println("  Liftoff!");
        System.out.println();

        // ---- The same countdown as a for loop ----
        // Setup, test and update all sit on one line where you can see them.
        System.out.println("Countdown with for:");
        for (int i = 5; i > 0; i--) {
            System.out.println("  " + i + "...");
        }
        System.out.println("  Liftoff!");
        System.out.println();

        // ---- do-while: run the body once, THEN test ----
        // Use it when the body must happen at least once, such as showing a
        // menu before asking whether the user wants to leave.
        int spins = 0;
        do {
            spins++;
            System.out.println("do-while body ran; spins = " + spins);
        } while (spins < 1);
        System.out.println("The test was false immediately, but the body still ran once.");
        System.out.println();

        // ---- The < versus <= decision, side by side ----
        System.out.print("for (int i = 0; i < 5; i++)  gives: ");
        for (int i = 0; i < 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.print("for (int i = 0; i <= 5; i++) gives: ");
        for (int i = 0; i <= 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println("  <- six numbers, not five");
        System.out.println();

        // ---- Counting from 1 instead, which is often what you want ----
        System.out.print("The first five positive numbers: ");
        for (int i = 1; i <= 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println();

        // ---- Nested loops: the inner loop runs fully for each outer step ----
        // \t is a tab character, used here to line the columns up.
        System.out.println("Times table (5 x 5 = 25 lines of work, 5 rows of output):");
        for (int row = 1; row <= 5; row++) {
            for (int col = 1; col <= 5; col++) {
                System.out.print(row * col + "\t");
            }
            System.out.println();   // ends the row, back in the outer loop
        }
    }
}
