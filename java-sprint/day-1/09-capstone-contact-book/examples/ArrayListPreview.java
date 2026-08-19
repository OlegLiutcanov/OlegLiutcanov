// ArrayListPreview.java
// Just enough ArrayList to build the capstone - six operations, no more.
//
// An ArrayList is a box that holds things in order and grows as you add to it.
// You never say how big it is. You add, and it makes room.
//
// The full story about lists, sets, maps and the rest is day 2 material. This
// file is the small corner of it that the contact book needs.
//
// Run it with:  java ArrayListPreview.java

import java.util.ArrayList;

public class ArrayListPreview {

    public static void main(String[] args) {

        System.out.println("=== A brand new list is empty ===");
        // The <String> says what kind of thing this list holds. The empty <>
        // on the right just means "the same kind as the left" - Java can see it.
        ArrayList<String> guests = new ArrayList<>();
        System.out.println("  guests      -> " + guests);
        System.out.println("  size()      -> " + guests.size());

        System.out.println();
        System.out.println("=== add: puts one thing on the end and grows the list ===");
        guests.add("Ada");
        guests.add("Grace");
        guests.add("Alan");
        System.out.println("  after three adds -> " + guests);
        System.out.println("  size()           -> " + guests.size());

        System.out.println();
        System.out.println("=== get: one item by position, counting from 0 ===");
        System.out.println("  get(0) -> " + guests.get(0));
        System.out.println("  get(2) -> " + guests.get(2));
        try {
            guests.get(3);
        } catch (IndexOutOfBoundsException e) {
            // Three items live at 0, 1 and 2. There is no 3.
            System.out.println("  get(3) -> refused: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== for-each: visit every item, no counting involved ===");
        for (String guest : guests) {
            System.out.println("  hello " + guest);
        }

        System.out.println();
        System.out.println("=== the counted loop, for when you need the position ===");
        for (int i = 0; i < guests.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + guests.get(i));
        }

        System.out.println();
        System.out.println("=== remove(index): takes it out and hands it back ===");
        String gone = guests.remove(1);
        System.out.println("  remove(1) returned -> " + gone);
        System.out.println("  guests now         -> " + guests);
        System.out.println("  size()             -> " + guests.size());
        System.out.println("  note that Alan slid down from position 2 to position 1");

        System.out.println();
        System.out.println("=== contains(x): walks the list calling equals for you ===");
        System.out.println("  contains(\"Ada\")  -> " + guests.contains("Ada"));
        System.out.println("  contains(\"ada\")  -> " + guests.contains("ada") + "   (equals on String is case-sensitive)");
        System.out.println("  contains(\"Grace\")-> " + guests.contains("Grace") + "   (removed a moment ago)");

        System.out.println();
        System.out.println("=== a list holds references, so it can hold duplicates ===");
        guests.add("Ada");
        System.out.println("  after adding Ada again -> " + guests);
        System.out.println("  the list does not care. If duplicates matter, YOU check first.");

        System.out.println();
        System.out.println("That is the whole toolkit: add, get, size, remove, contains, for-each.");
        System.out.println("It is enough to build a contact book.");
    }
}
