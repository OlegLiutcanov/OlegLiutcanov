// Run me with:  java MultiInterfaceDemo.java
//
// Shows: one class implementing SEVERAL interfaces, a default method, and the
// fact that each interface gives you a different "view" of the same object.

public class MultiInterfaceDemo {
    public static void main(String[] args) {
        ConcertTicket concert = new ConcertTicket("The Midnight Choir, row F", 85);
        TrainTicket train = new TrainTicket("Berlin -> Prague", 39);
        Souvenir mug = new Souvenir("Tour mug");

        // A mug can be described but never refunded, so it belongs in this
        // array and not the next one.
        Describable[] everything = {concert, train, mug};
        System.out.println("Everything in the bag:");
        for (Describable item : everything) {
            System.out.println("  " + item.shortLabel());
        }

        Refundable[] refundables = {concert, train};
        int refund = 0;
        for (Refundable item : refundables) {
            refund += item.refundAmount();
        }
        System.out.println("Refundable if the trip is cancelled: " + refund);

        System.out.println();
        System.out.println("Full label of the concert ticket: " + concert.label());
        System.out.println("A ConcertTicket is Describable? " + (concert instanceof Describable));
        System.out.println("A ConcertTicket is Refundable?  " + (concert instanceof Refundable));
        System.out.println("A Souvenir is Refundable?       " + (mug instanceof Refundable));
    }
}

interface Describable {
    String label();

    // A default method: a ready-made implementation every implementer gets
    // for free, and may override if it wants something different.
    default String shortLabel() {
        String full = label();
        return full.length() <= 20 ? full : full.substring(0, 20) + "...";
    }
}

interface Refundable {
    int refundAmount();
}

// A class may implement as many interfaces as it likes - separate them with commas.
class ConcertTicket implements Describable, Refundable {
    private final String act;
    private final int pricePaid;

    ConcertTicket(String act, int pricePaid) {
        this.act = act;
        this.pricePaid = pricePaid;
    }

    @Override
    public String label() {
        return "Concert: " + act;
    }

    @Override
    public int refundAmount() {
        return pricePaid / 2;   // concerts refund half
    }
}

class TrainTicket implements Describable, Refundable {
    private final String route;
    private final int pricePaid;

    TrainTicket(String route, int pricePaid) {
        this.route = route;
        this.pricePaid = pricePaid;
    }

    @Override
    public String label() {
        return "Train: " + route;
    }

    @Override
    public int refundAmount() {
        return pricePaid;       // trains refund in full
    }
}

class Souvenir implements Describable {
    private final String what;

    Souvenir(String what) {
        this.what = what;
    }

    @Override
    public String label() {
        return "Souvenir: " + what;
    }

    @Override
    public String shortLabel() {
        // Overriding the default method, because souvenir names are already short.
        return label().toUpperCase();
    }
}
