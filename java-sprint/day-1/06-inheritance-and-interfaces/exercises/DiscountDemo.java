// EXERCISE 3 - Sign the same contract from two unrelated classes
//
// Run me with:  java DiscountDemo.java
//
// A Book and a GymMembership have nothing in common - one is an object on a
// shelf, one is a subscription. Neither extends the other. But both can be
// discounted, so both can sign the same contract: Discountable.
//
// EXPECTED OUTPUT when you are done:
//
// Clean Code                  45.00  -20%  ->     36.00
// Annual Gym Pass            600.00  -15%  ->    510.00
// The Pragmatic Programmer    52.00  -10%  ->     46.80
// Best deal: Clean Code at 36.00
//
// Right now every method below returns a placeholder, so the program runs but
// prints nonsense. Do not change main. Everything to edit is marked TODO.

public class DiscountDemo {
    public static void main(String[] args) {
        Discountable[] cart = {
                new Book("Clean Code", 45.00, 20),
                new GymMembership("Annual Gym Pass", 600.00, 15),
                new Book("The Pragmatic Programmer", 52.00, 10)
        };

        Discountable best = cart[0];
        for (Discountable item : cart) {
            System.out.printf("%-24s %8.2f  -%2.0f%%  ->  %8.2f%n",
                    item.label(), item.basePrice(), item.discountPercent(), finalPrice(item));
            if (finalPrice(item) < finalPrice(best)) {
                best = item;
            }
        }
        System.out.printf("Best deal: %s at %.2f%n", best.label(), finalPrice(best));
    }

    // TODO 3: return the price after the discount is applied - the base price
    //         minus that percentage of it.
    //         Hint: item.basePrice() * (1 - item.discountPercent() / 100.0)
    //         Use 100.0, not 100. Dividing two whole numbers throws away the
    //         fraction, so 20 / 100 is 0 but 20 / 100.0 is 0.2.
    //
    //         Notice this method mentions only Discountable. It has no idea
    //         Book or GymMembership exist, and it never needs to.
    static double finalPrice(Discountable item) {
        return 0;
    }
}

// This is the contract. Three things, no bodies, no fields - just a list of
// what a Discountable must be able to answer. Read it, then go make the two
// classes below keep their promises.
interface Discountable {
    String label();

    double basePrice();

    double discountPercent();   // 20 means 20% off
}

class Book implements Discountable {
    private final String title;
    private final double price;
    private final double discount;

    Book(String title, double price, double discount) {
        this.title = title;
        this.price = price;
        this.discount = discount;
    }

    // TODO 1: return the real values from this Book's fields instead of the
    //         placeholders. Methods that come from an interface must be public.
    @Override
    public String label() {
        return null;
    }

    @Override
    public double basePrice() {
        return 0;
    }

    @Override
    public double discountPercent() {
        return 0;
    }
}

class GymMembership implements Discountable {
    private final String planName;
    private final double yearlyPrice;
    private final double memberDiscount;

    GymMembership(String planName, double yearlyPrice, double memberDiscount) {
        this.planName = planName;
        this.yearlyPrice = yearlyPrice;
        this.memberDiscount = memberDiscount;
    }

    // TODO 2: same contract, totally different class, different field names.
    //         That is the whole idea - the interface says WHAT, each class
    //         decides HOW.
    @Override
    public String label() {
        return null;
    }

    @Override
    public double basePrice() {
        return 0;
    }

    @Override
    public double discountPercent() {
        return 0;
    }
}

// STRETCH GOAL (optional, and for this one you may edit main):
// Add a Haircut class that implements Discountable, then drop a Haircut into
// the cart array. Nothing else in the file should need to change - not
// finalPrice, not the loop, not the best-deal logic. If you find yourself
// editing them, something has gone sideways.
