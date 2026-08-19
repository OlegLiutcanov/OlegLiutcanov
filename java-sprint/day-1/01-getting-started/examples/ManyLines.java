// Shows the difference between println and print, and does a little math.
// Run it with:  java ManyLines.java

public class ManyLines {
    public static void main(String[] args) {
        // println = "print line". It prints, then moves to the next line.
        System.out.println("First line.");
        System.out.println("Second line.");

        // println with nothing inside just ends the line, giving you a blank one.
        System.out.println();

        // print does NOT move to the next line, so these three land side by side.
        System.out.print("These ");
        System.out.print("three prints ");
        System.out.print("share one line.");
        System.out.println(); // and this one finally ends it

        System.out.println();

        // Numbers do not need quotes. Java does the arithmetic, then prints the result.
        System.out.println(2 + 3);
        System.out.println(7 * 6);

        // Both sides are whole numbers, so Java does whole-number division
        // and throws the remainder away. 7 / 2 is 3, not 3.5.
        System.out.println(7 / 2);

        // % gives you the remainder that division threw away.
        System.out.println(7 % 2);

        System.out.println();

        // A + between text and a number glues them together into one piece of text.
        // The parentheses force the addition to happen FIRST.
        System.out.println("2 + 3 = " + (2 + 3));

        // Without parentheses Java works left to right: it glues 2 onto the text,
        // and then glues 3 onto that. Same characters, very different answer.
        System.out.println("2 + 3 = " + 2 + 3);
    }
}
