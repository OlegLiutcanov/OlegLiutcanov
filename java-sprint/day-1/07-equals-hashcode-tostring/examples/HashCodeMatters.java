// HashCodeMatters.java
// Why hashCode exists, shown rather than asserted.
// Run it with:  java HashCodeMatters.java
//
// This file uses a HashSet and a HashMap, which are properly covered on day 2.
// All you need for now:
//   HashSet  - a bag that refuses to hold two equal things
//   HashMap  - a lookup table: you store a value under a key, then ask for it
// Both are called "hash" collections because of how they find things, and that
// is exactly where hashCode comes in.

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class HashCodeMatters {

    public static void main(String[] args) {
        System.out.println("=== The broken class: equals overridden, hashCode forgotten ===");
        BrokenCity paris1 = new BrokenCity("Paris", "FR");
        BrokenCity paris2 = new BrokenCity("Paris", "FR");

        System.out.println("  paris1.equals(paris2) -> " + paris1.equals(paris2)
                + "    (equals works perfectly)");
        System.out.println("  same hash code?       -> "
                + (paris1.hashCode() == paris2.hashCode()) + "   (and here is the crack)");

        Set<BrokenCity> brokenSet = new HashSet<>();
        brokenSet.add(paris1);
        brokenSet.add(paris2);
        System.out.println();
        System.out.println("  Added two equal cities to a HashSet.");
        System.out.println("  set size          -> " + brokenSet.size() + "   (should be 1)");
        System.out.println("  set contains an identical new BrokenCity? -> "
                + brokenSet.contains(new BrokenCity("Paris", "FR")));

        Map<BrokenCity, Integer> brokenPopulation = new HashMap<>();
        brokenPopulation.put(paris1, 2_100_000);
        System.out.println("  look the population up with an equal key -> "
                + brokenPopulation.get(new BrokenCity("Paris", "FR")));

        System.out.println();
        System.out.println("  Nothing threw an exception. Nothing warned you. The data");
        System.out.println("  is just quietly wrong, which is the worst kind of wrong.");

        System.out.println();
        System.out.println("=== Why: a hash set looks in a numbered drawer first ===");
        System.out.println("  A HashSet does not compare you against everything it holds.");
        System.out.println("  It turns your object into a number, opens only that drawer,");
        System.out.println("  and calls equals on the few things inside.");
        System.out.println();
        System.out.println("  Two equal objects with different hash codes land in different");
        System.out.println("  drawers, so equals is never even called. They never meet.");

        System.out.println();
        System.out.println("=== The fixed class: both methods, built from the same fields ===");
        City good1 = new City("Paris", "FR");
        City good2 = new City("Paris", "FR");

        System.out.println("  good1.equals(good2) -> " + good1.equals(good2));
        System.out.println("  same hash code?     -> " + (good1.hashCode() == good2.hashCode()));

        Set<City> goodSet = new HashSet<>();
        goodSet.add(good1);
        goodSet.add(good2);
        System.out.println();
        System.out.println("  set size     -> " + goodSet.size() + "   (the duplicate was recognised)");
        System.out.println("  set contents -> " + goodSet + "   (toString, doing its job)");
        System.out.println("  contains an identical new City? -> "
                + goodSet.contains(new City("Paris", "FR")));

        Map<City, Integer> population = new HashMap<>();
        population.put(good1, 2_100_000);
        System.out.println("  population.get(new City(\"Paris\", \"FR\")) -> "
                + population.get(new City("Paris", "FR")));

        System.out.println();
        System.out.println("=== One last way to break it: move the key after storing it ===");
        MutableTag tag = new MutableTag("red");
        Set<MutableTag> tags = new HashSet<>();
        tags.add(tag);

        System.out.println("  contains before -> " + tags.contains(tag));
        tag.setName("blue");                    // changes a field hashCode uses
        System.out.println("  contains after  -> " + tags.contains(tag));
        System.out.println("  set still holds -> " + tags);
        System.out.println();
        System.out.println("  The object is right there in the set and the set cannot find");
        System.out.println("  it, because it changed drawers without telling anyone.");
        System.out.println("  Immutable objects - and records - cannot do this to you.");

        System.out.println();
        System.out.println("The rule in one line:");
        System.out.println("  override equals and hashCode together, from the same fields,");
        System.out.println("  or not at all.");
    }
}

// BROKEN ON PURPOSE. equals is overridden, hashCode is not - so it still
// returns the inherited per-object number, which differs for every new object.
class BrokenCity {
    private final String name;
    private final String country;

    BrokenCity(String name, String country) {
        this.name = name;
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BrokenCity other = (BrokenCity) o;
        return Objects.equals(name, other.name) && Objects.equals(country, other.country);
    }

    // No hashCode here. The compiler accepts this without a word. (Only if you
    // go out of your way and run "javac -Xlint:all" does it mention it - and
    // nothing forces you to.) Your program is not nearly as happy.
}

// Both methods are correct here. The problem is that the field can still
// change after the object has been filed away in a collection.
class MutableTag {
    private String name;

    MutableTag(String name) {
        this.name = name;
    }

    void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag(" + name + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Objects.equals(name, ((MutableTag) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

class City {
    private final String name;
    private final String country;

    City(String name, String country) {
        this.name = name;
        this.country = country;
    }

    @Override
    public String toString() {
        return name + " (" + country + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        City other = (City) o;
        // Objects.equals handles null on either side instead of throwing.
        return Objects.equals(name, other.name) && Objects.equals(country, other.country);
    }

    @Override
    public int hashCode() {
        // Same two fields equals uses. That is the whole trick.
        return Objects.hash(name, country);
    }
}
