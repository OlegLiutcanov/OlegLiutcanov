// EXERCISE 1 - SOLUTION

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MoneyDemo {

    static int passed = 0;
    static int total = 0;

    public static void main(String[] args) {
        Money a = new Money(1250, "EUR");
        Money b = new Money(1250, "EUR");
        Money c = new Money(999, "EUR");
        Money d = new Money(1250, "USD");

        System.out.println("=== Money checks - every line should end in true ===");
        System.out.println();

        check("1.  toString() is \"12.50 EUR\"", a.toString().equals("12.50 EUR"));
        check("2.  a.equals(a) - a Money equals itself", a.equals(a));
        check("3.  a.equals(b) - same amount, same currency", a.equals(b));
        check("4.  b.equals(a) - and the other way round", b.equals(a));
        check("5.  a == b is false - equal, but two objects", !(a == b));
        check("6.  a does not equal c - different amount", !a.equals(c));
        check("7.  a does not equal d - different currency", !a.equals(d));
        check("8.  a.equals(null) is false, not an exception", !a.equals(null));
        check("9.  a does not equal the String \"12.50 EUR\"", !a.equals("12.50 EUR"));
        check("10. a and b have the same hash code", a.hashCode() == b.hashCode());

        Set<Money> wallet = new HashSet<>();
        wallet.add(a);
        wallet.add(b);
        check("11. a HashSet stores the two equal Moneys once", wallet.size() == 1);

        System.out.println();
        System.out.println(passed + " of " + total + " checks passed.");
        System.out.println("For the record, a prints as: " + a);
    }

    static void check(String label, boolean result) {
        total++;
        if (result) {
            passed++;
        }
        System.out.printf("%-52s %s%n", label, result);
    }
}

class Money {
    private final int amountInCents;
    private final String currency;

    Money(int amountInCents, String currency) {
        this.amountInCents = amountInCents;
        this.currency = currency;
    }

    int getAmountInCents() {
        return amountInCents;
    }

    String getCurrency() {
        return currency;
    }

    // %02d pads to two digits, so 5 cents prints as "05" and not "5".
    @Override
    public String toString() {
        return String.format("%d.%02d %s", amountInCents / 100, amountInCents % 100, currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Money other = (Money) o;
        // int with ==, object field with Objects.equals (which survives nulls).
        return amountInCents == other.amountInCents
                && Objects.equals(currency, other.currency);
    }

    @Override
    public int hashCode() {
        // The same two fields equals uses, in any order - just be consistent.
        return Objects.hash(amountInCents, currency);
    }
}
