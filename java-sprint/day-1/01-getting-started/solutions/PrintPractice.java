// SOLUTION to Exercise 3.

public class PrintPractice {
    public static void main(String[] args) {
        System.out.print("Ready... ");
        System.out.print("Set... ");
        System.out.print("Go!");
        System.out.println(); // ends the line the three prints were building

        System.out.println("9 + 4 = " + (9 + 4));
        System.out.println("9 - 4 = " + (9 - 4));
        System.out.println("9 / 4 = " + (9 / 4) + " remainder " + (9 % 4));

        // No parentheses, so this runs strictly left to right: the text absorbs
        // the 9, then absorbs the 4, and no addition ever happens.
        System.out.println("Careless answer: 9 + 4 = " + 9 + 4);
    }
}
