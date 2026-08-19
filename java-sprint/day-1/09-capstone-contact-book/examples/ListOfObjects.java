// ListOfObjects.java
// A list of your own objects - which is what the capstone is really about.
//
// A list of Strings is a warm-up. The moment the list holds a class YOU wrote,
// two things from module 07 start doing real work: toString decides what the
// list looks like when you print it, and equals decides what "already in the
// list" means.
//
// Run it with:  java ListOfObjects.java

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Objects;

public class ListOfObjects {

    public static void main(String[] args) {

        ArrayList<Pet> pets = new ArrayList<>();
        pets.add(new Pet("Rex", "dog"));
        pets.add(new Pet("Milo", "cat"));
        pets.add(new Pet("Nala", "cat"));

        System.out.println("=== printing the list calls toString on every item ===");
        System.out.println("  " + pets);

        System.out.println();
        System.out.println("=== for-each, one per line, is what you usually want ===");
        int number = 1;
        for (Pet pet : pets) {
            System.out.println("  " + number + ". " + pet);
            number++;
        }

        System.out.println();
        System.out.println("=== search: walk the list, collect the hits into a NEW list ===");
        ArrayList<Pet> cats = bySpecies(pets, "cat");
        System.out.println("  bySpecies(\"cat\") -> " + cats);
        System.out.println("  bySpecies(\"fish\") -> " + bySpecies(pets, "fish") + "   (empty, not null)");

        System.out.println();
        System.out.println("=== contains: this is where YOUR equals gets called ===");
        Pet anotherRex = new Pet("rex", "dog");
        System.out.println("  a brand new Pet(\"rex\", \"dog\")");
        System.out.println("  pets.contains(it) -> " + pets.contains(anotherRex));
        System.out.println("  Pet.equals compares names ignoring case, so the list says yes.");
        System.out.println("  Delete Pet's equals and this prints false - the list would compare");
        System.out.println("  references instead, and two separate objects are never the same one.");

        System.out.println();
        System.out.println("=== delete: find the position, then remove it ===");
        System.out.println("  before -> " + pets);
        Pet removed = removeByName(pets, "MILO");
        System.out.println("  removeByName(\"MILO\") returned -> " + removed);
        System.out.println("  after  -> " + pets);
        System.out.println("  removeByName(\"Nobody\") returned -> " + removeByName(pets, "Nobody"));

        System.out.println();
        System.out.println("=== the trap: removing while a for-each is running ===");
        System.out.println("  Same mistake both times: the loop is walking the list, and the");
        System.out.println("  body changes it underneath. Java reacts in two different ways.");

        System.out.println();
        System.out.println("  case 1 - it quietly gives you the wrong answer:");
        ArrayList<Pet> three = new ArrayList<>();
        three.add(new Pet("Rex", "dog"));
        three.add(new Pet("Milo", "cat"));
        three.add(new Pet("Nala", "cat"));
        System.out.println("    before -> " + three);
        removeAllCats(three);
        System.out.println("    after  -> " + three);
        System.out.println("    We asked for every cat to go. Nala is still sitting there.");
        System.out.println("    Removing Milo slid Nala into the slot the loop had already passed.");

        System.out.println();
        System.out.println("  case 2 - same code, one more pet, and now it throws:");
        ArrayList<Pet> four = new ArrayList<>();
        four.add(new Pet("Rex", "dog"));
        four.add(new Pet("Milo", "cat"));
        four.add(new Pet("Nala", "cat"));
        four.add(new Pet("Zoe", "dog"));
        System.out.println("    before -> " + four);
        try {
            removeAllCats(four);
            System.out.println("    after  -> " + four);
        } catch (ConcurrentModificationException e) {
            System.out.println("    threw " + e.getClass().getSimpleName()
                    + " (getMessage() is " + e.getMessage() + ")");
            System.out.println("    list left half-edited -> " + four);
        }

        System.out.println();
        System.out.println("  A bug that sometimes crashes and sometimes lies is the worst kind.");
        System.out.println("  Fix: loop by index and return the moment you remove something -");
        System.out.println("  exactly what removeByName above does.");
    }

    // Broken on purpose. Never edit a list while a for-each is walking it.
    static void removeAllCats(ArrayList<Pet> pets) {
        for (Pet pet : pets) {
            if (pet.getSpecies().equals("cat")) {
                pets.remove(pet);
            }
        }
    }

    // Build and return a new list. The original is untouched - callers hate
    // surprises, and a search that quietly edited the book would be one.
    static ArrayList<Pet> bySpecies(ArrayList<Pet> pets, String species) {
        ArrayList<Pet> matches = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getSpecies().equalsIgnoreCase(species)) {
                matches.add(pet);
            }
        }
        return matches;
    }

    // Counted loop, because remove needs a position. Returning immediately
    // means the loop never runs again on a list that just changed shape.
    static Pet removeByName(ArrayList<Pet> pets, String name) {
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getName().equalsIgnoreCase(name)) {
                return pets.remove(i);
            }
        }
        return null;      // nothing matched, and null is how we say so
    }
}

class Pet {

    private final String name;
    private final String species;

    Pet(String name, String species) {
        this.name = name;
        this.species = species;
    }

    String getName() {
        return name;
    }

    String getSpecies() {
        return species;
    }

    @Override
    public String toString() {
        return name + " the " + species;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pet other = (Pet) o;
        return name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        // equals ignores case, so hashCode has to ignore it too.
        return Objects.hash(name.toLowerCase());
    }
}
