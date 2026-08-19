// ToStringPreview.java
// What happens when you println an object you wrote yourself - and why the
// result looks like line noise.
// Run it with:  java ToStringPreview.java
//
// NOTE: the hex digits you see are arbitrary. They may differ on your machine
// from the ones in the lesson, and nothing should ever depend on them. This
// file explains where they come from.

public class ToStringPreview {

    public static void main(String[] args) {
        Sticker star = new Sticker("star", "gold");

        System.out.println("=== Printing the things you already know ===");
        System.out.println("  an int    : " + 42);
        System.out.println("  a double  : " + 3.5);
        System.out.println("  a boolean : " + true);
        System.out.println("  a String  : " + "hello");

        System.out.println();
        System.out.println("=== Printing an object of your own class ===");
        System.out.println("  println(star) below:");
        System.out.print("  ");
        System.out.println(star);

        // Concatenating with + gives exactly the same text: whenever Java needs
        // an object as text, it asks the object to describe itself.
        System.out.println("  \"\" + star  : " + star);

        System.out.println();
        System.out.println("=== That string is not random ===");
        // It is two pieces glued with an @:
        System.out.println("  class name        : " + star.getClass().getName());
        System.out.println("  hash code, in hex : "
                + Integer.toHexString(star.hashCode()));
        System.out.println("  ...which is why you get ClassName@thathexnumber.");

        System.out.println();
        System.out.println("The hex part is a hash code - roughly an id for the");
        System.out.println("object, not an address, and not any of its fields.");
        System.out.println("Java has no idea which fields you would want shown,");
        System.out.println("so it falls back on something universally true.");

        System.out.println();
        System.out.println("=== Meanwhile, the fields are right there ===");
        System.out.println("  " + star.getShape() + ", " + star.getColour());
        System.out.println();
        System.out.println("Writing that by hand every time gets old fast.");
        System.out.println("Module 07 shows how to teach a class to describe");
        System.out.println("itself, so println(star) prints something useful.");
    }
}

class Sticker {
    private String shape;
    private String colour;

    Sticker(String shape, String colour) {
        this.shape = shape;
        this.colour = colour;
    }

    String getShape() {
        return shape;
    }

    String getColour() {
        return colour;
    }
}
