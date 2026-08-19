// ConstructorTour.java
// Constructors: the method-shaped thing that runs at 'new'.
// This file shows the free one you get when you write none, what happens the
// moment you write your own, several constructors on one class, and how one
// constructor calls another.
// Run it with:  java ConstructorTour.java

public class ConstructorTour {

    public static void main(String[] args) {
        System.out.println("=== The free constructor ===");
        // Bookmark declares NO constructor, so Java supplies an invisible
        // no-argument one. It builds the object and leaves the fields at their
        // defaults - nothing more.
        Bookmark mark = new Bookmark();
        System.out.println("  label = " + mark.label + ", page = " + mark.page);

        System.out.println();
        System.out.println("=== Three ways to build a Book ===");

        // The full constructor: every field supplied.
        Book full = new Book("Dune", "Frank Herbert", 1965);
        System.out.println("  full    -> " + describe(full));

        // The two-argument one: no year known yet, so it fills in a sensible
        // stand-in rather than leaving a half-built object behind.
        Book noYear = new Book("Piranesi", "Susanna Clarke");
        System.out.println("  no year -> " + describe(noYear));

        // The no-argument one. Because Book writes its own constructors, this
        // exists ONLY because it was written out by hand below.
        Book blank = new Book();
        System.out.println("  blank   -> " + describe(blank));

        System.out.println();
        System.out.println("=== Every object gets its own fields ===");
        Book a = new Book("Emma", "Jane Austen", 1815);
        Book b = new Book("Emma", "Jane Austen", 1815);
        System.out.println("  a and b were built with identical values.");
        a.year = 1816;
        System.out.println("  after a.year = 1816 -> a.year is " + a.year
                + ", b.year is still " + b.year);
    }

    static String describe(Book book) {
        return book.title + " by " + book.author + " (" + book.year + ")";
    }
}

// No constructor written, so Java hands this class a free no-argument one.
class Bookmark {
    String label;
    int page;
}

class Book {
    String title;
    String author;
    int year;

    // 1. The full constructor. Same name as the class, and NO return type -
    //    not even void. Writing 'void Book(...)' would silently make it an
    //    ordinary method instead, which is a classic and very confusing bug.
    Book(String title, String author, int year) {
        // Each parameter has the same name as its field, which is the normal
        // Java style. 'this.title' is the field of the object being built;
        // bare 'title' is the parameter. Drop the 'this.' and you assign the
        // parameter to itself, leaving the field null - no error, just a bug.
        this.title = title;
        this.author = author;
        this.year = year;
    }

    // 2. Two arguments. Rather than repeating the three assignments, it hands
    //    the work to the constructor above with this(...). That call must be
    //    the very first statement in the constructor.
    Book(String title, String author) {
        this(title, author, 0);
    }

    // 3. No arguments. Once a class writes any constructor of its own, the
    //    free one disappears - so if you still want 'new Book()' to work, you
    //    have to write it yourself. This one also chains upward.
    Book() {
        this("Untitled", "Unknown", 0);
    }
}
