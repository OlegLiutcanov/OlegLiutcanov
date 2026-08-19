// InstanceVsStatic.java
// The full picture of 'static', the word you have been typing since module 01.
//   static      -> belongs to the CLASS. One of it, shared by everybody.
//   no static   -> belongs to an OBJECT. One per object, called with a dot.
// Run it with:  java InstanceVsStatic.java

public class InstanceVsStatic {

    public static void main(String[] args) {
        System.out.println("Tickets issued so far: " + Ticket.getIssuedCount());

        // Each 'new' bumps the shared counter and gives this ticket its own
        // serial number.
        Ticket first = new Ticket("Ada");
        Ticket second = new Ticket("Grace");
        Ticket third = new Ticket("Alan");

        System.out.println("Tickets issued so far: " + Ticket.getIssuedCount());
        System.out.println();

        // INSTANCE methods are called on an object, with a dot. 'first.print()'
        // reads as "print yourself" addressed to one particular ticket.
        System.out.println("=== Instance methods run on one object ===");
        first.print();
        second.print();
        third.print();

        System.out.println();
        System.out.println("=== Each object has its own fields ===");
        second.markUsed();
        System.out.println("After second.markUsed():");
        first.print();
        second.print();
        third.print();

        System.out.println();
        System.out.println("=== Static belongs to the class, not any object ===");
        // A static member is called on the CLASS NAME. There is exactly one
        // issuedCount in the whole program, no matter how many tickets exist.
        System.out.println("Ticket.getIssuedCount() = " + Ticket.getIssuedCount());

        // A static METHOD that needs no object at all - it is a plain function
        // that happens to live in the Ticket class.
        System.out.println("Ticket.isValidSerial(2)   = " + Ticket.isValidSerial(2));
        System.out.println("Ticket.isValidSerial(99)  = " + Ticket.isValidSerial(99));

        System.out.println();
        System.out.println("=== Which one should a method be? ===");
        System.out.println("Does it need this object's fields? -> instance method.");
        System.out.println("Does it work purely from its arguments? -> static.");
    }
}

class Ticket {

    // A STATIC field: one single copy shared by every Ticket ever made. It is
    // not stored inside any object. Use it for things that are genuinely about
    // the class as a whole, like "how many have I made".
    private static int issuedCount = 0;

    // INSTANCE fields: one of each per object.
    private String holder;
    private int serial;
    private boolean used;

    Ticket(String holder) {
        this.holder = holder;
        // Bump the shared counter, then take the new value as this ticket's
        // serial. Every ticket ends up with a different one.
        issuedCount = issuedCount + 1;
        this.serial = issuedCount;
        this.used = false;
    }

    // No 'static' -> an instance method. It reads 'holder', 'serial' and
    // 'used', which only make sense for one particular ticket.
    void print() {
        String status = "unused";
        if (used) {
            status = "USED";
        }
        System.out.println("  #" + serial + " " + holder + " (" + status + ")");
    }

    void markUsed() {
        used = true;
    }

    // 'static' -> belongs to the class. It answers a question about tickets in
    // general, so there is no object for it to run on. Note that a static
    // method may read a static field, but it cannot see 'holder' or 'serial':
    // there is no particular ticket in scope for it to read them from. That is
    // exactly the error you met in module 04 -
    //   "non-static variable holder cannot be referenced from a static context"
    static int getIssuedCount() {
        return issuedCount;
    }

    static boolean isValidSerial(int serial) {
        return serial >= 1 && serial <= issuedCount;
    }
}
