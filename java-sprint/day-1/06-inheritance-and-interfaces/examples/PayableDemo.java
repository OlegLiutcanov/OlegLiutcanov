// Run me with:  java PayableDemo.java
//
// Shows: an interface as a contract, implemented by two classes that have
// nothing else in common. A Contractor is a person. An Invoice is a piece of
// paper. They share no superclass - but both can be paid, so both are Payable.

public class PayableDemo {
    public static void main(String[] args) {
        Payable[] toPayThisMonth = {
                new Contractor("Nadia", 40, 55),
                new Invoice("INV-2031", "Cloud hosting", 320),
                new Contractor("Tom", 12, 70)
        };

        int total = 0;
        for (Payable item : toPayThisMonth) {
            System.out.println(item.payee() + " -> " + item.amountDue());
            total += item.amountDue();
        }

        System.out.println("-----");
        System.out.println("Total to pay: " + total);
    }
}

// An interface lists what a type can DO. No fields, no constructors,
// no method bodies (unless you mark them default - see MultiInterfaceDemo).
interface Payable {
    String payee();

    int amountDue();
}

class Contractor implements Payable {
    private final String name;
    private final int hoursWorked;
    private final int hourlyRate;

    Contractor(String name, int hoursWorked, int hourlyRate) {
        this.name = name;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public String payee() {
        return name + " (contractor)";
    }

    @Override
    public int amountDue() {
        return hoursWorked * hourlyRate;
    }
}

class Invoice implements Payable {
    private final String reference;
    private final String supplier;
    private final int amount;

    Invoice(String reference, String supplier, int amount) {
        this.reference = reference;
        this.supplier = supplier;
        this.amount = amount;
    }

    @Override
    public String payee() {
        return supplier + " (invoice " + reference + ")";
    }

    @Override
    public int amountDue() {
        return amount;
    }
}
