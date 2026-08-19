// BreakAndContinue.java
// Two words that change how a loop flows:
//   break    - leave the loop right now
//   continue - skip the rest of this pass, start the next one
// Run it with:  java BreakAndContinue.java

public class BreakAndContinue {

    public static void main(String[] args) {

        // ---- break: stop as soon as you have what you came for ----
        System.out.println("Looking for the first number over 30 that divides by 7:");
        for (int n = 31; n <= 100; n++) {
            System.out.println("  checking " + n);
            if (n % 7 == 0) {
                System.out.println("  found it: " + n);
                break;            // the loop ends here; n never reaches 100
            }
        }
        System.out.println();

        // ---- continue: skip the ones you do not care about ----
        System.out.print("Odd numbers from 1 to 10: ");
        for (int n = 1; n <= 10; n++) {
            if (n % 2 == 0) {
                continue;         // even: jump straight to n++ and test again
            }
            System.out.print(n + " ");
        }
        System.out.println();

        // The same result without continue. Both are fine; pick whichever
        // reads more clearly for the job in front of you.
        System.out.print("Odd numbers again:        ");
        for (int n = 1; n <= 10; n++) {
            if (n % 2 != 0) {
                System.out.print(n + " ");
            }
        }
        System.out.println();
        System.out.println();

        // ---- break inside a while loop, guarding against forever ----
        // A loop that adds numbers until the running total passes 20.
        int total = 0;
        int next = 1;
        while (true) {            // "true" forever - the break is the only way out
            total = total + next;
            System.out.println("  added " + next + ", total is now " + total);
            if (total > 20) {
                break;
            }
            next++;
        }
        System.out.println("Stopped once the total passed 20.");
        System.out.println();

        // ---- break only leaves the loop it is standing in ----
        System.out.println("Nested loops: break escapes the INNER loop only.");
        for (int row = 1; row <= 3; row++) {
            for (int col = 1; col <= 5; col++) {
                if (col == 3) {
                    break;        // stops this row, not the whole grid
                }
                System.out.print("  row " + row + " col " + col);
            }
            System.out.println();
        }
    }
}
