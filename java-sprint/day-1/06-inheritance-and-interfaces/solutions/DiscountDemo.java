// EXERCISE 3 - SOLUTION

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

    // Written against the interface only. Any future Discountable - a concert
    // ticket, a flight, a haircut - works here with no change.
    static double finalPrice(Discountable item) {
        return item.basePrice() * (1 - item.discountPercent() / 100.0);
    }
}

interface Discountable {
    String label();

    double basePrice();

    double discountPercent();
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

    @Override
    public String label() {
        return title;
    }

    @Override
    public double basePrice() {
        return price;
    }

    @Override
    public double discountPercent() {
        return discount;
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

    @Override
    public String label() {
        return planName;
    }

    @Override
    public double basePrice() {
        return yearlyPrice;
    }

    @Override
    public double discountPercent() {
        return memberDiscount;
    }
}
