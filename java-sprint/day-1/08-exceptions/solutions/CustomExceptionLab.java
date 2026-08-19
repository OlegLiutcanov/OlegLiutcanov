// CustomExceptionLab.java - solution
//
// Run it with:  java CustomExceptionLab.java

public class CustomExceptionLab {

    public static void main(String[] args) {
        System.out.println("=== Fizzy Cola machine: 2 cans at 75c ===");
        VendingMachine machine = new VendingMachine("Fizzy Cola", 2, 75);
        tryVend(machine, 100);
        tryVend(machine, 50);
        tryVend(machine, 0);
        tryVend(machine, 75);
        tryVend(machine, 100);

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

class OutOfStockException extends Exception {

    private final String itemName;

    OutOfStockException(String itemName) {
        super("the machine is out of " + itemName);
        this.itemName = itemName;
    }

    // The handler gets the machine-readable detail as well as the message,
    // which is the whole reason for writing a custom exception class.
    String getItemName() {
        return itemName;
    }
}

class VendingMachine {

    private final String itemName;
    private int cansLeft;
    private final int priceInCents;

    VendingMachine(String itemName, int cans, int priceInCents) {
        if (itemName == null || itemName.isBlank()) {
            throw new IllegalArgumentException("the item name must not be empty");
        }
        if (cans < 0) {
            throw new IllegalArgumentException("cans must not be negative, got " + cans);
        }
        if (priceInCents <= 0) {
            throw new IllegalArgumentException("the price must be positive, got " + priceInCents);
        }
        this.itemName = itemName;
        this.cansLeft = cans;
        this.priceInCents = priceInCents;
    }

    int vend(int coins) throws OutOfStockException {
        if (coins <= 0) {
            throw new IllegalArgumentException("coins must be positive, got " + coins);
        }
        if (cansLeft == 0) {
            throw new OutOfStockException(itemName);
        }
        if (coins < priceInCents) {
            throw new IllegalArgumentException(coins + "c is not enough, this costs " + priceInCents + "c");
        }

        // Nothing changes until every check has passed. A refused sale must
        // leave the machine exactly as it was.
        cansLeft--;
        return coins - priceInCents;
    }

    void restock(int cans) {
        if (cans < 0) {
            throw new IllegalArgumentException("cannot restock a negative number of cans");
        }
        cansLeft += cans;
    }

    int getCansLeft() {
        return cansLeft;
    }
}
