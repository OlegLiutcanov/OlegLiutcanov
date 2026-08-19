// BankAccountDemo.java
// The centrepiece of this module: a class that guards its own data.
// The fields are private, so the only way to change a balance is to go through
// a method that checks first. That is what lets BankAccount promise something
// its users can rely on: the balance is never negative.
// Run it with:  java BankAccountDemo.java

public class BankAccountDemo {

    public static void main(String[] args) {
        // Two objects from one blueprint. Each gets its own owner and balance.
        BankAccount alice = new BankAccount("Alice", 100.0);
        BankAccount bob = new BankAccount("Bob", 0.0);

        System.out.println("=== Two accounts, two separate balances ===");
        printAccount(alice);
        printAccount(bob);

        System.out.println();
        System.out.println("=== Deposits do not interfere ===");
        alice.deposit(50.0);
        System.out.println("Alice deposits 50.0");
        printAccount(alice);
        printAccount(bob);

        System.out.println();
        System.out.println("=== The guards do their job ===");

        // deposit returns true if it happened, false if it was refused. The
        // caller can react instead of guessing.
        boolean ok = alice.deposit(-25.0);
        System.out.println("alice.deposit(-25.0) accepted? " + ok);

        ok = alice.withdraw(1000.0);
        System.out.println("alice.withdraw(1000.0) accepted? " + ok);

        System.out.println("Alice's balance is untouched by both attempts:");
        printAccount(alice);

        System.out.println();
        System.out.println("=== A withdrawal that is allowed ===");
        ok = alice.withdraw(30.0);
        System.out.println("alice.withdraw(30.0) accepted? " + ok);
        printAccount(alice);

        System.out.println();
        System.out.println("=== One object talking to another ===");
        ok = alice.transferTo(bob, 20.0);
        System.out.println("alice.transferTo(bob, 20.0) accepted? " + ok);
        printAccount(alice);
        printAccount(bob);

        System.out.println();
        System.out.println("=== A setter that is allowed to refuse ===");
        System.out.println("bob.setOwner(\"\") accepted?        " + bob.setOwner(""));
        System.out.println("bob.setOwner(\"Bob Smith\") accepted? " + bob.setOwner("Bob Smith"));
        printAccount(bob);

        System.out.println();
        System.out.println("=== The invariant held the whole way through ===");
        System.out.println("Alice's balance negative? " + (alice.getBalance() < 0));
        System.out.println("Bob's balance negative?   " + (bob.getBalance() < 0));
    }

    static void printAccount(BankAccount account) {
        System.out.println("  " + account.getOwner() + ": " + account.getBalance());
    }
}

class BankAccount {

    // PRIVATE means: only code inside this class may touch these directly.
    // Nothing outside can write 'account.balance = -500;' and break the rule
    // the class exists to enforce.
    private String owner;
    private double balance;

    // A CONSTRUCTOR: same name as the class, no return type. It runs once, at
    // 'new', and its job is to leave the object in a valid state from birth.
    BankAccount(String owner, double startingBalance) {
        // 'this.owner' is the field; plain 'owner' is the parameter. Without
        // 'this.' the line would just assign the parameter to itself.
        this.owner = owner;

        // Validate here too. An account must not be born broken.
        if (startingBalance < 0) {
            this.balance = 0.0;
        } else {
            this.balance = startingBalance;
        }
    }

    // An INSTANCE method: no 'static'. It runs on one particular account, and
    // inside it 'balance' means "this account's balance".
    boolean deposit(double amount) {
        if (amount <= 0) {
            return false;          // refuse, and say so
        }
        balance = balance + amount;
        return true;
    }

    boolean withdraw(double amount) {
        if (amount <= 0) {
            return false;
        }
        if (amount > balance) {
            return false;          // this is the guard that keeps balance >= 0
        }
        balance = balance - amount;
        return true;
    }

    // An object can take another object of its own class as a parameter.
    // Note that it reuses withdraw and deposit rather than touching balances
    // directly - so the validation cannot be bypassed by accident.
    boolean transferTo(BankAccount other, double amount) {
        if (!withdraw(amount)) {
            return false;
        }
        other.deposit(amount);
        return true;
    }

    // GETTERS: read-only doors into the private data.
    String getOwner() {
        return owner;
    }

    // A SETTER, and the reason setters are worth writing rather than making
    // the field public: this one gets to say no. An owner name may legitimately
    // change, but it must never become empty or missing.
    boolean setOwner(String newOwner) {
        if (newOwner == null || newOwner.isEmpty()) {
            return false;
        }
        owner = newOwner;
        return true;
    }

    double getBalance() {
        return balance;
    }

    // Notice what is NOT here: there is no setBalance. The only ways the
    // balance changes are deposit, withdraw and transferTo, and all three
    // check first. Leaving a setter out is a design decision, not an oversight.
}
