// AccountWithExceptions.java
// The BankAccount from module 05, grown up.
//
// Back then, a refused deposit returned false. That works, but 'false' cannot
// tell you WHY, and a caller who forgets to check the return value silently
// gets nothing. Exceptions fix both problems: the reason travels with the
// failure, and ignoring it is not an option.
//
// Run it with:  java AccountWithExceptions.java

public class AccountWithExceptions {

    public static void main(String[] args) {

        System.out.println("=== A normal day ===");
        BankAccount alice = new BankAccount("Alice", 100.0);
        alice.deposit(50.0);
        System.out.println("  " + alice.getOwner() + ": " + alice.getBalance());

        System.out.println();
        System.out.println("=== The constructor refuses to build a broken account ===");
        tryToBuild("", 100.0);
        tryToBuild("Bob", -20.0);
        tryToBuild("Bob", 0.0);

        System.out.println();
        System.out.println("=== Setters and deposits validate too ===");
        try {
            alice.deposit(-5.0);
        } catch (IllegalArgumentException e) {
            System.out.println("  deposit(-5.0) refused: " + e.getMessage());
        }
        try {
            alice.setOwner("   ");
        } catch (IllegalArgumentException e) {
            System.out.println("  setOwner(\"   \") refused: " + e.getMessage());
        }
        System.out.println("  balance and owner unchanged: " + alice.getOwner() + ", " + alice.getBalance());

        System.out.println();
        System.out.println("=== A custom exception that carries extra information ===");
        try {
            alice.withdraw(500.0);
        } catch (InsufficientFundsException e) {
            System.out.println("  " + e.getMessage());
            System.out.println("  short by " + e.getShortfall() + " - the exception carried the number with it");
        }

        System.out.println();
        System.out.println("=== A method that passes the problem upwards ===");
        try {
            payRent(alice, 40.0);
            System.out.println("  rent of 40.0 paid, balance now " + alice.getBalance());
            payRent(alice, 1000.0);
            System.out.println("  this line is never reached");
        } catch (InsufficientFundsException e) {
            System.out.println("  rent unpaid: " + e.getMessage());
        }

        System.out.println();
        System.out.println("Final balance: " + alice.getBalance() + " (never negative, whatever we threw at it)");
    }

    // 'throws InsufficientFundsException' means: this method does not deal with
    // that failure, its caller must. The compiler holds you to it.
    static void payRent(BankAccount account, double amount) throws InsufficientFundsException {
        account.withdraw(amount);
    }

    static void tryToBuild(String owner, double startingBalance) {
        try {
            new BankAccount(owner, startingBalance);
            System.out.println("  new BankAccount(\"" + owner + "\", " + startingBalance + ") -> built fine");
        } catch (IllegalArgumentException e) {
            System.out.println("  new BankAccount(\"" + owner + "\", " + startingBalance + ") -> refused: " + e.getMessage());
        }
    }
}

class BankAccount {

    private String owner;
    private double balance;

    BankAccount(String owner, double startingBalance) {
        // Reuse the setter so the rule about names lives in exactly one place.
        setOwner(owner);

        if (startingBalance < 0) {
            // Throwing from a constructor means the object is never handed
            // back to the caller. There is no half-built account to misuse.
            throw new IllegalArgumentException("starting balance must not be negative, got " + startingBalance);
        }
        this.balance = startingBalance;
    }

    void setOwner(String newOwner) {
        if (newOwner == null || newOwner.isBlank()) {
            throw new IllegalArgumentException("owner must not be empty");
        }
        this.owner = newOwner;
    }

    void deposit(double amount) {
        // IllegalArgumentException says "the caller made a mistake". Depositing
        // a negative amount is a bug in the calling code, not a fact of life.
        if (amount <= 0) {
            throw new IllegalArgumentException("deposit must be positive, got " + amount);
        }
        balance += amount;
    }

    void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("withdrawal must be positive, got " + amount);
        }
        if (amount > balance) {
            // Running out of money is not a programming mistake - it is an
            // ordinary thing that happens to real accounts. That is why this
            // one is a checked exception: every caller has to have a plan.
            throw new InsufficientFundsException(
                    "cannot withdraw " + amount + " from a balance of " + balance,
                    amount - balance);
        }
        balance -= amount;
    }

    String getOwner() {
        return owner;
    }

    double getBalance() {
        return balance;
    }
}

// A custom exception is just a class that extends an existing one. Extending
// Exception makes it CHECKED: callers must catch it or declare it.
// (Extending RuntimeException instead would make it unchecked.)
class InsufficientFundsException extends Exception {

    private final double shortfall;

    InsufficientFundsException(String message, double shortfall) {
        super(message);          // hands the message to the machinery getMessage() reads
        this.shortfall = shortfall;
    }

    // An exception is an object, so it can carry whatever the handler needs -
    // here, exactly how much was missing.
    double getShortfall() {
        return shortfall;
    }
}
