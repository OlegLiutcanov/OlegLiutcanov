// ReceiptRefactor.java - solution
//
// The output is byte for byte what the messy version printed. Everything that
// changed is structure: main now reads as a list of steps, and each
// calculation has a name and a single home.

public class ReceiptRefactor {

    static final double TAX_RATE = 0.085;
    static final double DISCOUNT_RATE = 0.10;
    static final double DISCOUNT_THRESHOLD = 50.0;

    static double round2(double amount) {
        return Math.round(amount * 100) / 100.0;
    }

    static double lineTotal(double unitPrice, int quantity) {
        return round2(unitPrice * quantity);
    }

    static double subtotalOf(double[] unitPrices, int[] quantities) {
        double subtotal = 0.0;
        for (int i = 0; i < unitPrices.length; i++) {
            subtotal = subtotal + lineTotal(unitPrices[i], quantities[i]);
        }
        return round2(subtotal);
    }

    // The guard clause turns "if big enough, do the maths, otherwise nothing"
    // into "get the boring case out of the way, then do the maths".
    static double discountFor(double subtotal) {
        if (subtotal <= DISCOUNT_THRESHOLD) {
            return 0.0;
        }
        return round2(subtotal * DISCOUNT_RATE);
    }

    static double taxOn(double amount) {
        return round2(amount * TAX_RATE);
    }

    static void printItem(String name, double unitPrice, int quantity) {
        System.out.println(name + " (" + quantity + " x " + unitPrice + ") = "
                + lineTotal(unitPrice, quantity));
    }

    static void printAmount(String label, double amount) {
        System.out.println(label + ": " + amount);
    }

    public static void main(String[] args) {
        String[] names = { "Notebook", "Pen", "Backpack", "Sticky notes" };
        double[] unitPrices = { 3.49, 1.25, 34.99, 2.15 };
        int[] quantities = { 4, 10, 1, 3 };

        System.out.println("=== RECEIPT ===");
        for (int i = 0; i < names.length; i++) {
            printItem(names[i], unitPrices[i], quantities[i]);
        }
        System.out.println("---------------");

        double subtotal = subtotalOf(unitPrices, quantities);
        double discount = discountFor(subtotal);
        double taxable = round2(subtotal - discount);
        double tax = taxOn(taxable);

        printAmount("Subtotal", subtotal);
        printAmount("Discount", discount);
        printAmount("Taxable", taxable);
        printAmount("Tax", tax);
        printAmount("TOTAL", round2(taxable + tax));
    }
}
