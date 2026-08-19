// Run me with:  java OverrideTypoDemo.java
//
// Shows WHY @Override is worth typing. A one-letter typo in a method name
// silently creates a brand-new method instead of overriding the old one,
// and your program quietly does the wrong thing. @Override turns that
// silent bug into a compiler error.

public class OverrideTypoDemo {
    public static void main(String[] args) {
        Account plain = new Account("A-1", 100);
        BrokenSavings broken = new BrokenSavings("S-9", 100);
        FixedSavings fixed = new FixedSavings("S-10", 100);

        System.out.println(plain.summary());
        System.out.println(broken.summary());   // wanted 4%, got 0%
        System.out.println(fixed.summary());    // 4% - correct

        System.out.println();
        System.out.println("broken.interestRate() -> " + broken.interestRate()
                + "   (inherited from Account; the override never happened)");
        System.out.println("broken.intrestRate()  -> " + broken.intrestRate()
                + "   (a separate method nobody ever calls)");
        System.out.println("fixed.interestRate()  -> " + fixed.interestRate()
                + "   (a real override)");
    }
}

class Account {
    private final String id;
    private final int balance;

    Account(String id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    int interestRate() {
        return 0;
    }

    String summary() {
        return id + ": balance " + balance + ", rate " + interestRate() + "%";
    }
}

class BrokenSavings extends Account {
    BrokenSavings(String id, int balance) {
        super(id, balance);
    }

    // The method in Account is spelled interestRate(). This is intrestRate().
    // Java compiles it happily as a NEW method. summary() still calls the old one.
    // Adding @Override here would refuse to compile - which is exactly what we want.
    int intrestRate() {
        return 4;
    }
}

class FixedSavings extends Account {
    FixedSavings(String id, int balance) {
        super(id, balance);
    }

    @Override
    int interestRate() {
        return 4;
    }
}
