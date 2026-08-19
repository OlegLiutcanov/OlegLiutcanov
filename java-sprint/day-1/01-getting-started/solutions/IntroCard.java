// SOLUTION to Exercise 2.
// Your details will differ - only the shape of the card is being checked.

public class IntroCard {
    public static void main(String[] args) {
        System.out.println("==============================");
        System.out.println("  Ada Lovelace");
        System.out.println("  Computer Science student");
        System.out.println("------------------------------");
        System.out.println("  Already knows: Python, SQL");
        System.out.println("  Learning now:  Java");

        // The parentheses make Java subtract first and glue second.
        // Without them the line would read "... 3 - 1 days to go".
        System.out.println("  Sprint day " + 1 + " of 3, " + (3 - 1) + " days to go");

        System.out.println("==============================");
    }
}
