// CustomExceptionLab.java
//
// Write one custom exception and one class that throws it.
// This file COMPILES as given - run it first, see the placeholder output, then
// fill in the TODOs from the top down.
//
// main() and the two helper methods below are finished. Do not change them:
// they are the test, and they decide the exact wording of the output.
//
// Expected output when you are done:
//
// === Fizzy Cola machine: 2 cans at 75c ===
//   vend(100) -> 25c change, 1 can(s) left
//   vend(50) -> refused: 50c is not enough, this costs 75c
//   vend(0) -> refused: coins must be positive, got 0
//   vend(75) -> 0c change, 0 can(s) left
//   vend(100) -> sold out (Fizzy Cola): the machine is out of Fizzy Cola
//
// === Restocking ===
//   restock(3) -> 3 can(s) left
//   vend(80) -> 5c change, 2 can(s) left
//
// === Machines that cannot legally exist ===
//   new VendingMachine("", 5, 75) -> refused: the item name must not be empty
//   new VendingMachine("Water", -1, 75) -> refused: cans must not be negative, got -1
//   new VendingMachine("Water", 5, 0) -> refused: the price must be positive, got 0
//
// Run it with:  java CustomExceptionLab.java

public class CustomExceptionLab {

    public static void main(String[] args) {
        System.out.println("=== Fizzy Cola machine: 2 cans at 75c ===");
        VendingMachine machine = new VendingMachine("Fizzy Cola", 2, 75);
        tryVend(machine, 100);   // pays too much - gets change
        tryVend(machine, 50);    // not enough money
        tryVend(machine, 0);     // no money at all
        tryVend(machine, 75);    // exact money, takes the last can
        tryVend(machine, 100);   // sold out

        System.out.println();
        System.out.println("=== Restocking ===");
        machine.restock(3);
        System.out.println("  restock(3) -> " + machine.getCansLeft() + " can(s) left");
        tryVend(machine, 80);

        System.out.println();
        System.out.println("=== Machines that cannot legally exist ===");
        tryToBuild("", 5, 75);
        tryToBuild("Water", -1, 75);
        tryToBuild("Water", 5, 0);
    }

    // Finished for you. Note the two catch blocks: one for the checked
    // exception you are about to write, one for the unchecked one Java gives
    // you for free.
    static void tryVend(VendingMachine machine, int coins) {
        try {
            int change = machine.vend(coins);
            System.out.println("  vend(" + coins + ") -> " + change + "c change, "
                    + machine.getCansLeft() + " can(s) left");
        } catch (OutOfStockException e) {
            System.out.println("  vend(" + coins + ") -> sold out (" + e.getItemName() + "): " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("  vend(" + coins + ") -> refused: " + e.getMessage());
        }
    }

    // Finished for you.
    static void tryToBuild(String itemName, int cans, int priceInCents) {
        String call = "  new VendingMachine(\"" + itemName + "\", " + cans + ", " + priceInCents + ")";
        try {
            new VendingMachine(itemName, cans, priceInCents);
            System.out.println(call + " -> built fine");
        } catch (IllegalArgumentException e) {
            System.out.println(call + " -> refused: " + e.getMessage());
        }
    }
}

// A CHECKED exception: it extends Exception (not RuntimeException), so every
// caller of a method that throws it must catch it or declare it. Running out of
// cans is not a programming bug - it is an ordinary event the caller should
// have a plan for - which is exactly when checked is the right choice.
class OutOfStockException extends Exception {

    // TODO 1: add a private final String field called itemName.

    // TODO 2: add a constructor that takes the item name and does two things:
    //           super("the machine is out of " + itemName);
    //           this.itemName = itemName;
    //         super(...) hands the message to the machinery getMessage() reads.

    // TODO 3: make getItemName return the field instead of null.
    String getItemName() {
        return null;
    }
}

class VendingMachine {

    private String itemName;
    private int cansLeft;
    private int priceInCents;

    // TODO 4: validate, then assign. Throw IllegalArgumentException with these
    //         exact messages:
    //           itemName null or blank -> "the item name must not be empty"
    //           cans below 0           -> "cans must not be negative, got " + cans
    //           price 0 or below       -> "the price must be positive, got " + priceInCents
    //         Hint: String has an .isBlank() method, and a null check must come
    //         first or the isBlank() call itself will throw.
    VendingMachine(String itemName, int cans, int priceInCents) {
    }

    // TODO 5: make this work, in this order:
    //           coins 0 or below   -> throw new IllegalArgumentException(
    //                                     "coins must be positive, got " + coins)
    //           no cans left       -> throw new OutOfStockException(itemName)
    //           coins below price  -> throw new IllegalArgumentException(
    //                                     coins + "c is not enough, this costs " + priceInCents + "c")
    //           otherwise          -> take one can off the shelf and return the change
    //
    // The 'throws OutOfStockException' part of the signature is already here.
    // Delete it once and the compiler will tell you exactly why it is needed.
    int vend(int coins) throws OutOfStockException {
        return 0;
    }

    // TODO 6: add 'cans' to cansLeft. Reject a negative 'cans' with
    //         new IllegalArgumentException("cannot restock a negative number of cans")
    void restock(int cans) {
    }

    // TODO 7: return the real field.
    int getCansLeft() {
        return 0;
    }
}
