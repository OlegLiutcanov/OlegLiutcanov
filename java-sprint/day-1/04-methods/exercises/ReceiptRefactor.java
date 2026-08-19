// ReceiptRefactor.java
//
// GOAL: this program already works. Your job is to make it READABLE without
// changing a single character of its output.
//
// Fill in the seven empty methods, then rewrite main so it calls them instead
// of doing the work itself. When you are finished, main should read like a
// short description of a receipt, and every calculation should live in a
// method with a name that says what it is.
//
// Run it with:  java ReceiptRefactor.java
//
// EXPECTED OUTPUT - identical before and after your refactor:
//
// === RECEIPT ===
// Notebook (4 x 3.49) = 13.96
// Pen (10 x 1.25) = 12.5
// Backpack (1 x 34.99) = 34.99
// Sticky notes (3 x 2.15) = 6.45
// ---------------
// Subtotal: 67.9
// Discount: 6.79
// Taxable: 61.11
// Tax: 5.19
// TOTAL: 66.3
//
// Amounts print as plain doubles, so "12.5" means twelve euros fifty.
//
// TIP: run the program BEFORE you touch anything and save the output. After
// every extraction, run it again and check that nothing moved. That is the
// discipline that makes refactoring safe.

public class ReceiptRefactor {

    static final double TAX_RATE = 0.085;
    static final double DISCOUNT_RATE = 0.10;
    static final double DISCOUNT_THRESHOLD = 50.0;

    // TODO 1: round an amount to 2 decimal places.
    // The expression Math.round(amount * 100) / 100.0 appears six times in
    // main below. Give it a name here and it appears once.
    static double round2(double amount) {
        return 0;
    }

    // TODO 2: the cost of one receipt line, rounded to 2 decimals.
    static double lineTotal(double unitPrice, int quantity) {
        return 0;
    }

    // TODO 3: add up every line, rounded to 2 decimals.
    // The two arrays line up: unitPrices[i] belongs with quantities[i].
    static double subtotalOf(double[] unitPrices, int[] quantities) {
        return 0;
    }

    // TODO 4: the discount earned by this subtotal, rounded to 2 decimals.
    // Nothing is discounted at or below DISCOUNT_THRESHOLD. Write that as a
    // guard clause: check the boring case first and return early.
    static double discountFor(double subtotal) {
        return 0;
    }

    // TODO 5: the tax due on an amount, rounded to 2 decimals.
    static double taxOn(double amount) {
        return 0;
    }

    // TODO 6: print one item line, e.g.  Notebook (4 x 3.49) = 13.96
    // This one returns nothing, so its return type is void.
    static void printItem(String name, double unitPrice, int quantity) {
    }

    // TODO 7: print one labelled amount, e.g.  Subtotal: 67.9
    static void printAmount(String label, double amount) {
    }

    // TODO 8: once the methods above work, replace the body of main with calls
    // to them. Keep the same data at the top - only the logic moves out.
    public static void main(String[] args) {
        String[] names = { "Notebook", "Pen", "Backpack", "Sticky notes" };
        double[] unitPrices = { 3.49, 1.25, 34.99, 2.15 };
        int[] quantities = { 4, 10, 1, 3 };

        System.out.println("=== RECEIPT ===");

        double subtotal = 0.0;
        for (int i = 0; i < names.length; i++) {
            double total = Math.round(unitPrices[i] * quantities[i] * 100) / 100.0;
            subtotal = subtotal + total;
            System.out.println(names[i] + " (" + quantities[i] + " x " + unitPrices[i]
                    + ") = " + total);
        }
        subtotal = Math.round(subtotal * 100) / 100.0;

        System.out.println("---------------");
        System.out.println("Subtotal: " + subtotal);

        double discount = 0.0;
        if (subtotal > 50.0) {
            discount = Math.round(subtotal * 0.10 * 100) / 100.0;
        }
        System.out.println("Discount: " + discount);

        double taxable = Math.round((subtotal - discount) * 100) / 100.0;
        System.out.println("Taxable: " + taxable);

        double tax = Math.round(taxable * 0.085 * 100) / 100.0;
        System.out.println("Tax: " + tax);

        System.out.println("TOTAL: " + Math.round((taxable + tax) * 100) / 100.0);
    }
}
