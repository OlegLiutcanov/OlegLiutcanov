// EXERCISE 2 - SOLUTION
//
// The entire hand-written class collapsed into one line plus a validation
// block. Everything main calls still works, because a record generates exactly
// the accessors, toString, equals and hashCode that were missing.

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

record Book(String title, String author, int year) {

    Book {
        if (year < 1) {
            throw new IllegalArgumentException("year must be a real year, got " + year);
        }
    }
}
