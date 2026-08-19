// ContactBookApp.java - solution
// The finished capstone.
//
// Run it with:  java ContactBookApp.java
//
// Three classes, three jobs:
//
//   Contact         one person's data, and the rules about what counts as valid
//   ContactBook     the collection, and the operations on it
//   ContactBookApp  the conversation with the human at the keyboard
//
// Only ContactBookApp knows a keyboard exists. Only Contact knows what a valid
// email looks like. Only ContactBook knows the contacts live in an ArrayList.
// Keeping those three apart is most of what people mean by "design", and it is
// why you could swap the ArrayList for a database and change one class.

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class ContactBookApp {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ContactBook book = new ContactBook();
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Choose: ");
            String choice = in.nextLine().trim();

            switch (choice) {
                case "1" -> addContact(in, book);
                case "2" -> listContacts(book);
                case "3" -> searchContacts(in, book);
                case "4" -> deleteContact(in, book);
                case "5" -> {
                    System.out.println("  Bye!");
                    running = false;
                }
                default -> System.out.println("  I do not know the option \"" + choice + "\". Pick 1 to 5.");
            }
        }

        in.close();
    }

    static void printMenu() {
        System.out.println();
        System.out.println("=== Contact Book ===");
        System.out.println("  1) Add a contact");
        System.out.println("  2) List all contacts");
        System.out.println("  3) Search by name");
        System.out.println("  4) Delete by name");
        System.out.println("  5) Quit");
    }

    static void addContact(Scanner in, ContactBook book) {
        System.out.print("Name:  ");
        String name = in.nextLine();
        System.out.print("Email: ");
        String email = in.nextLine();
        System.out.print("Phone: ");
        String phone = in.nextLine();

        try {
            Contact contact = new Contact(name, email, phone);
            if (book.add(contact)) {
                System.out.println("  Added " + contact.getName() + ".");
            } else {
                System.out.println("  There is already a contact called " + contact.getName()
                        + ". Nothing changed.");
            }
        } catch (IllegalArgumentException e) {
            // The constructor refuses to build nonsense, which is its job.
            // Catching it here is what turns a crash into one friendly line -
            // the menu carries on and the user tries again.
            System.out.println("  Not added: " + e.getMessage());
        }
    }

    static void listContacts(ContactBook book) {
        if (book.size() == 0) {
            System.out.println("  The book is empty. Add someone with option 1.");
            return;
        }
        System.out.println("  " + book.size() + " " + contactWord(book.size()) + ":");
        printNumbered(book.all());
    }

    static void searchContacts(Scanner in, ContactBook book) {
        System.out.print("Search for: ");
        String term = in.nextLine().trim();

        if (term.isBlank()) {
            // Without this guard an empty search matches every contact, because
            // every name contains the empty string. Technically right, useless.
            System.out.println("  Type something to search for.");
            return;
        }

        ArrayList<Contact> matches = book.search(term);
        if (matches.size() == 0) {
            System.out.println("  Nothing matches \"" + term + "\".");
            return;
        }
        System.out.println("  " + matches.size() + " " + contactWord(matches.size())
                + " matching \"" + term + "\":");
        printNumbered(matches);
    }

    static void deleteContact(Scanner in, ContactBook book) {
        System.out.print("Name to delete: ");
        String name = in.nextLine().trim();

        if (name.isBlank()) {
            System.out.println("  Type the name of the contact to delete.");
            return;
        }

        Contact removed = book.delete(name);
        if (removed == null) {
            System.out.println("  No contact called \"" + name + "\". Nothing changed.");
        } else {
            // Printing the stored contact rather than what the user typed means
            // deleting "ada lovelace" reports "Deleted Ada Lovelace."
            System.out.println("  Deleted " + removed.getName() + ".");
        }
    }

    static void printNumbered(ArrayList<Contact> contacts) {
        int number = 1;
        for (Contact contact : contacts) {
            // "+ contact" calls contact.toString() for us.
            System.out.println("    " + number + ". " + contact);
            number++;
        }
    }

    static String contactWord(int count) {
        return count == 1 ? "contact" : "contacts";
    }
}

class ContactBook {

    // final means the field can never be pointed at a DIFFERENT list. The list
    // itself still grows and shrinks - that is the whole point of it.
    private final ArrayList<Contact> contacts = new ArrayList<>();

    int size() {
        return contacts.size();
    }

    boolean add(Contact contact) {
        // contains walks the list calling Contact.equals on each item. This one
        // line is the entire duplicate-name guard, and it only works because
        // Contact has an equals that compares names.
        if (contacts.contains(contact)) {
            return false;
        }
        contacts.add(contact);
        return true;
    }

    ArrayList<Contact> all() {
        // Hand back a copy. Return the real list and any caller could add or
        // remove behind our back, and the duplicate rule above would mean
        // nothing. (new ArrayList<>(contacts) is the one-line version of this
        // loop - you will meet it on day 2.)
        ArrayList<Contact> copy = new ArrayList<>();
        for (Contact contact : contacts) {
            copy.add(contact);
        }
        return copy;
    }

    ArrayList<Contact> search(String term) {
        String needle = term.trim().toLowerCase();
        ArrayList<Contact> matches = new ArrayList<>();
        for (Contact contact : contacts) {
            // Lower-case both sides and "ada" finds "Ada Lovelace".
            if (contact.getName().toLowerCase().contains(needle)) {
                matches.add(contact);
            }
        }
        return matches;
    }

    Contact delete(String name) {
        String wanted = name.trim();
        // A counted loop, because remove needs a position. Returning straight
        // away means we never keep looping over a list that just changed size.
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getName().equalsIgnoreCase(wanted)) {
                return contacts.remove(i);      // remove hands back what it removed
            }
        }
        return null;      // nothing matched, and null is how this method says so
    }
}

class Contact {

    private final String name;
    private final String email;
    private final String phone;

    Contact(String name, String email, String phone) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("a name is required");
        }
        if (email == null || !looksLikeEmail(email.trim())) {
            throw new IllegalArgumentException("\"" + email + "\" does not look like an email address");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("a phone number is required");
        }

        // Trim once, right here at the door. Everything downstream - equals,
        // search, printing - can then trust that there is no stray whitespace.
        this.name = name.trim();
        this.email = email.trim();
        this.phone = phone.trim();
    }

    // Deliberately loose: something before an @, a dot after it, and something
    // after the dot. Enough to catch a typo, nowhere near a full specification.
    // Real email validation is a famously miserable problem, and programs that
    // must be certain send a confirmation message instead of guessing.
    private static boolean looksLikeEmail(String candidate) {
        int at = candidate.indexOf('@');
        if (at < 1) {
            return false;                                  // no @, or nothing before it
        }
        int dot = candidate.indexOf('.', at + 2);          // a dot, not immediately after the @
        return dot > 0 && dot < candidate.length() - 1;    // and not the last character
    }

    String getName() {
        return name;
    }

    String getEmail() {
        return email;
    }

    String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return name + " <" + email + "> " + phone;
    }

    // The design decision: two contacts are the same person when the names
    // match, ignoring case. Email and phone are details that can change.
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact other = (Contact) o;
        return name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        // equals ignores case and ignores the other two fields, so hashCode
        // must do exactly the same. Equal objects, equal hash codes.
        return Objects.hash(name.toLowerCase());
    }
}
