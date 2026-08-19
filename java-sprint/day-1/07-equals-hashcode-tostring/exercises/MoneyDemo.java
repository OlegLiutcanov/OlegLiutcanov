// EXERCISE 1 - Give a class the three methods it deserves
//
// Run me with:  java MoneyDemo.java
//
// Money holds an amount in cents plus a currency code. 1250 + "EUR" means
// 12.50 EUR. Two Money objects built from the same amount and the same
// currency are the same money - and right now Java does not believe that.
//
// main is a test harness. Do not change it. Everything to edit is a TODO
// inside the Money class at the bottom.
//
// EXPECTED OUTPUT when you are done:
//
// === Money checks - every line should end in true ===
//
// 1.  toString() is "12.50 EUR"                        true
// 2.  a.equals(a) - a Money equals itself              true
// 3.  a.equals(b) - same amount, same currency         true
// 4.  b.equals(a) - and the other way round            true
// 5.  a == b is false - equal, but two objects         true
// 6.  a does not equal c - different amount            true
// 7.  a does not equal d - different currency          true
// 8.  a.equals(null) is false, not an exception        true
// 9.  a does not equal the String "12.50 EUR"          true
// 10. a and b have the same hash code                  true
// 11. a HashSet stores the two equal Moneys once       true
//
// 11 of 11 checks passed.
// For the record, a prints as: 12.50 EUR
//
// The starter compiles and runs, but only 6 of 11 checks pass. Interestingly,
// the ones that already pass are the "should be false" ones - the inherited
// equals is very good at saying no and hopeless at saying yes.

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

    // Helper. Already written for you - read it once and move on.
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

    // TODO 1: return the amount and currency as text: "12.50 EUR".
    //         The cents live in one int, so split it:
    //           whole units -> amountInCents / 100
    //           leftover    -> amountInCents % 100
    //         String.format("%d.%02d %s", ..., ..., currency) does the rest.
    //         %02d pads to two digits, so 5 cents prints as "05", not "5".
    //
    //         The placeholder below calls the inherited version, which is the
    //         Money@1b6d3586 you are trying to get rid of.
    @Override
    public String toString() {
        return super.toString();
    }

    // TODO 2: compare contents instead of identity. Four steps, in this order:
    //           1. if (this == o) return true;
    //           2. if (o == null || getClass() != o.getClass()) return false;
    //           3. Money other = (Money) o;
    //           4. return the field comparison
    //         For step 4: amountInCents is an int, so use ==. currency is an
    //         object, so use Objects.equals(currency, other.currency) - it
    //         returns false instead of throwing if either side is null.
    //
    //         The placeholder is the inherited equals, which is just ==.
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    // TODO 3: return Objects.hash(...) over the SAME fields your equals reads.
    //         The rule you must not break: two objects that are equal have to
    //         return the same number here. Feed hash the same fields and that
    //         is automatic.
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
