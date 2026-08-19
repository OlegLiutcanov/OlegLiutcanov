// StackTraceTour.java
// This program CRASHES on purpose. That is the entire point of it: it produces
// a real, multi-frame stack trace for you to practise reading.
//
// Run it with:  java StackTraceTour.java
//
// It prints two receipt lines, then dies on the third. Section 2 of LESSON.md
// walks through the crash output line by line.

public class StackTraceTour {

    public static void main(String[] args) {
        String[] names = { "Coffee beans", "Filter papers", "Milk frother" };
        String[] quantities = { "2", "1", "two" };   // <- a human typed that last one
        double[] prices = { 8.50, 3.25, 19.99 };

        System.out.println("=== Receipt ===");
        printReceipt(names, quantities, prices);
        System.out.println("Thank you, come again!");   // never reached
    }

    // Called by main. Calls lineTotal once per item.
    static void printReceipt(String[] names, String[] quantities, double[] prices) {
        double total = 0.0;
        for (int i = 0; i < names.length; i++) {
            double line = lineTotal(quantities[i], prices[i]);
            System.out.println("  " + names[i] + ": " + line);
            total += line;
        }
        System.out.println("  TOTAL: " + total);
    }

    // Called by printReceipt. Calls quantityOf.
    static double lineTotal(String quantityText, double unitPrice) {
        return quantityOf(quantityText) * unitPrice;
    }

    // Called by lineTotal. This is where the crash actually happens: parseInt
    // is handed the text "two", which is not a number it can make sense of.
    static int quantityOf(String quantityText) {
        return Integer.parseInt(quantityText);
    }
}
