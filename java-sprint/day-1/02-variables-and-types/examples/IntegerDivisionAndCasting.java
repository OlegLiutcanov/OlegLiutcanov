// IntegerDivisionAndCasting.java
// The number rules that surprise every newcomer to Java.
// Run it with:  java IntegerDivisionAndCasting.java

public class IntegerDivisionAndCasting {

    public static void main(String[] args) {

        System.out.println("--- 1. int / int throws away the remainder ---");
        int pizzas = 7;
        int friends = 2;
        System.out.println("7 / 2  = " + (pizzas / friends));   // 3, not 3.5
        System.out.println("7 % 2  = " + (pizzas % friends));   // 1 slice left over

        System.out.println();
        System.out.println("--- 2. Three ways to get the real answer ---");
        System.out.println("7 / 2.0            = " + (7 / 2.0));             // one side is a double
        System.out.println("(double) 7 / 2     = " + ((double) 7 / 2));      // cast first, then divide
        System.out.println("(double) (7 / 2)   = " + ((double) (7 / 2)));    // TOO LATE: 3 became 3.0

        System.out.println();
        System.out.println("--- 3. Remainder is great for 'is it divisible?' ---");
        int minutes = 137;
        System.out.println(minutes + " minutes = " + (minutes / 60) + "h " + (minutes % 60) + "m");
        System.out.println("Is 137 even? " + (minutes % 2 == 0));

        System.out.println();
        System.out.println("--- 4. Widening is automatic (small type into big type) ---");
        int score = 42;
        double preciseScore = score;   // int fits inside a double, so Java does it silently
        long bigScore = score;         // int fits inside a long too
        System.out.println("int 42 as a double = " + preciseScore);
        System.out.println("int 42 as a long   = " + bigScore);

        System.out.println();
        System.out.println("--- 5. Narrowing needs a cast, and it truncates ---");
        double price = 19.99;
        int wholeEuros = (int) price;  // chops the decimals off, never rounds
        System.out.println("(int) 19.99  = " + wholeEuros);
        System.out.println("(int) -3.99  = " + (int) -3.99);   // chops toward zero
        System.out.println("Rounded properly: " + Math.round(price));

        System.out.println();
        System.out.println("--- 6. double is fast, not exact ---");
        System.out.println("0.1 + 0.2 = " + (0.1 + 0.2));
        System.out.println("(Binary fractions cannot store 0.1 perfectly, same as 1/3 in decimal.)");

        System.out.println();
        System.out.println("--- 7. int has a ceiling; long is roomier ---");
        System.out.println("Biggest int:        " + Integer.MAX_VALUE);
        System.out.println("Biggest int plus 1: " + (Integer.MAX_VALUE + 1) + "   <-- it wrapped around!");
        long safe = (long) Integer.MAX_VALUE + 1;
        System.out.println("Same sum in a long: " + safe);

        System.out.println();
        System.out.println("--- 8. char is secretly a number ---");
        char letter = 'a';
        System.out.println("'a' as a number:   " + (int) letter);
        System.out.println("The letter after a: " + (char) (letter + 1));
    }
}
