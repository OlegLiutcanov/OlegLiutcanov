// EXERCISE 2 - Delete most of a class and make it better
//
// Run me with:  java ConvertToRecord.java
//
// Book below is written the long way: private final fields, a constructor, and
// one accessor per field. It is 25 lines that say almost nothing, and it is
// still missing toString, equals and hashCode.
//
// A record gives you all of it. Your job is to throw the class away and
// replace it with one.
//
// EXPECTED OUTPUT when you are done:
//
// 1. book1                -> Book[title=Dune, author=Frank Herbert, year=1965]
// 2. book1.equals(book2)  -> true
// 3. book1 == book2       -> false
// 4. same hash code       -> true
// 5. book1.title()        -> Dune
// 6. book1.equals(book3)  -> false
// 7. new Book(..., 0)     -> rejected: year must be a real year, got 0
//
// Line 3 stays false forever, by the way. Two separate objects are two
// separate objects no matter how good your equals is.
//
// The starter runs today, but line 1 shows a Book@... hash, lines 2 and 4 are
// false, and line 7 happily accepts a book published in year zero.

public class ConvertToRecord {

    public static void main(String[] args) {
        Book book1 = new Book("Dune", "Frank Herbert", 1965);
        Book book2 = new Book("Dune", "Frank Herbert", 1965);
        Book book3 = new Book("Neuromancer", "William Gibson", 1984);

        System.out.println("1. book1                -> " + book1);
        System.out.println("2. book1.equals(book2)  -> " + book1.equals(book2));
        System.out.println("3. book1 == book2       -> " + (book1 == book2));
        System.out.println("4. same hash code       -> " + (book1.hashCode() == book2.hashCode()));
        System.out.println("5. book1.title()        -> " + book1.title());
        System.out.println("6. book1.equals(book3)  -> " + book1.equals(book3));

        try {
            Book bad = new Book("Bad Data", "Nobody", 0);
            System.out.println("7. new Book(..., 0)     -> accepted: " + bad);
        } catch (IllegalArgumentException e) {
            System.out.println("7. new Book(..., 0)     -> rejected: " + e.getMessage());
        }
    }
}

// TODO 1: replace this entire class - all of it, from "class Book" to its
//         closing brace - with a single record declaration:
//
//             record Book(String title, String author, int year) { }
//
//         That one line regenerates the constructor, the three accessors,
//         toString, equals and hashCode. main needs no changes at all.
//
//         The accessors below are deliberately named title(), author() and
//         year() rather than getTitle() and friends, because those are exactly
//         the names a record generates. That is the only reason main keeps
//         working after you swap them out.
//
// TODO 2: then add a compact constructor inside the record's braces so that a
//         year below 1 is refused:
//
//             record Book(String title, String author, int year) {
//                 Book {
//                     if (year < 1) {
//                         throw new IllegalArgumentException(
//                                 "year must be a real year, got " + year);
//                     }
//                 }
//             }
//
//         Note what a compact constructor does NOT have: a parameter list, and
//         any this.title = title assignments. Java still does all of that for
//         you. The block is purely your chance to inspect the values first.
class Book {
    private final String title;
    private final String author;
    private final int year;

    Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    String title() {
        return title;
    }

    String author() {
        return author;
    }

    int year() {
        return year;
    }
}

// STRETCH GOAL (optional):
// Add a method to the record - records may have those - such as
//
//     String citation() { return author + ", \"" + title + "\" (" + year + ")"; }
//
// and print it from main. Then try to add a setter, something like
// void setYear(int year) { this.year = year; }, and read the compiler error
// you get. It is a good one: a record's fields are final on purpose.
