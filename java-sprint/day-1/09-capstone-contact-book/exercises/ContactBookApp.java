// ContactBookApp.java
//
// THE CAPSTONE. Work through the six milestones in LESSON.md, in order, running
// the program after every change. This file compiles and runs exactly as it is,
// so you start green and stay green.
//
// The TODOs are numbered by milestone: TODO M1 belongs to milestone 1, and so
// on. Do not go hunting for M4 while M2 is unfinished - the whole point of a
// milestone is that you can see it work before you build the next thing on it.
//
// Run it with:  java ContactBookApp.java
// Or drive it without typing:
//     printf '1\nAda Lovelace\nada@calc.org\n555-0101\n2\n5\n' | java ContactBookApp.java
//
// EXPECTED SESSION when all six milestones are done (what you type is shown
// after each prompt):
//
// === Contact Book ===
//   1) Add a contact
//   2) List all contacts
//   3) Search by name
//   4) Delete by name
//   5) Quit
// Choose: 1
// Name:  Ada Lovelace
// Email: ada@calc.org
// Phone: 555-0101
//   Added Ada Lovelace.
//
// === Contact Book ===
//   ...
// Choose: 1
// Name:  Ada Lovelace
// Email: other@example.com
// Phone: 555-9999
//   There is already a contact called Ada Lovelace. Nothing changed.
//
// === Contact Book ===
//   ...
// Choose: 1
// Name:  Grace Hopper
// Email: grace.navy.mil
// Phone: 555-0102
//   Not added: "grace.navy.mil" does not look like an email address
//
// === Contact Book ===
//   ...
// Choose: 2
//   1 contact:
//     1. Ada Lovelace <ada@calc.org> 555-0101
//
// === Contact Book ===
//   ...
// Choose: 3
// Search for: ada
//   1 contact matching "ada":
//     1. Ada Lovelace <ada@calc.org> 555-0101
//
// === Contact Book ===
//   ...
// Choose: 4
// Name to delete: ada lovelace
//   Deleted Ada Lovelace.
//
// === Contact Book ===
//   ...
// Choose: 5
//   Bye!

import java.util.ArrayList;
import java.util.Objects;      // you will need this in milestone 6
import java.util.Scanner;

public class ContactBookApp {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ContactBook book = new ContactBook();

        // TODO M1: build the menu loop.
        //   - a boolean called running, starting at true
        //   - while (running): call printMenu(), print "Choose: " with
        //     System.out.print (no ln - the answer should appear on the same line),
        //     then read one line with in.nextLine().trim()
        //   - switch on that line:
        //       "1" -> addContact(in, book);
        //       "2" -> listContacts(book);
        //       "3" -> searchContacts(in, book);
        //       "4" -> deleteContact(in, book);
        //       "5" -> print "  Bye!" and set running to false
        //       default -> print: I do not know the option "x". Pick 1 to 5.
        // Until you write it, the program starts and stops without asking anything.

        in.close();
    }

    // Written for you, so your menu matches the checkpoints exactly.
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
        // TODO M3: ask for Name, Email and Phone with System.out.print prompts
        // ("Name:  ", "Email: ", "Phone: " - two spaces after Name lines them
        // up), reading each answer with in.nextLine().
        //
        // Then, inside a try block:
        //   - build a new Contact from the three answers
        //   - if book.add(contact) returned true, print "  Added <name>."
        //   - otherwise print
        //     "  There is already a contact called <name>. Nothing changed."
        // and catch IllegalArgumentException e, printing
        //     "  Not added: " + e.getMessage()
        System.out.println("  (add is not built yet)");
    }

    static void listContacts(ContactBook book) {
        // TODO M3: if the book is empty, print
        //     "  The book is empty. Add someone with option 1."
        // and return. Otherwise print
        //     "  <n> <contact/contacts>:"
        // using contactWord(n) for the plural, then hand book.all() to
        // printNumbered.
        System.out.println("  (list is not built yet)");
    }

    static void searchContacts(Scanner in, ContactBook book) {
        // TODO M4: print "Search for: ", read a line, trim it.
        //   - if the term is blank, print "  Type something to search for."
        //     and return (see milestone 6 for why this guard matters)
        //   - ask book.search(term) for the matches
        //   - no matches: print "  Nothing matches \"<term>\"."
        //   - otherwise print
        //     "  <n> <contact/contacts> matching \"<term>\":"
        //     and hand the matches to printNumbered
        System.out.println("  (search is not built yet)");
    }

    static void deleteContact(Scanner in, ContactBook book) {
        // TODO M5: print "Name to delete: ", read a line, trim it.
        //   - blank: print "  Type the name of the contact to delete." and return
        //   - call book.delete(name). It hands back the Contact it removed, or
        //     null if it found nobody.
        //   - null: print "  No contact called \"<name>\". Nothing changed."
        //   - otherwise: print "  Deleted <the removed contact's name>."
        System.out.println("  (delete is not built yet)");
    }

    // Written for you.
    static void printNumbered(ArrayList<Contact> contacts) {
        int number = 1;
        for (Contact contact : contacts) {
            System.out.println("    " + number + ". " + contact);
            number++;
        }
    }

    // Written for you.
    static String contactWord(int count) {
        return count == 1 ? "contact" : "contacts";
    }
}

class ContactBook {

    // TODO M3.1: one private final field here:
    //     an ArrayList<Contact> called contacts, created on this line with
    //     new ArrayList<>(). Nothing outside this class should ever see it.

    // TODO M3.2: return how many contacts the book holds.
    int size() {
        return 0;
    }

    // TODO M3.3: add the contact and return true.
    // TODO M6.2: first refuse duplicates - if the list already contains an
    // equal contact, change nothing and return false.
    boolean add(Contact contact) {
        return false;
    }

    // TODO M3.4: return a COPY of the list - a new ArrayList with every contact
    // added to it. Handing out the real list would let any caller add or remove
    // behind this class's back.
    ArrayList<Contact> all() {
        return new ArrayList<>();
    }

    // TODO M4: return a new list of every contact whose name CONTAINS term,
    // ignoring case. Lower-case both sides before comparing, and remember that
    // String has a contains method too.
    ArrayList<Contact> search(String term) {
        return new ArrayList<>();
    }

    // TODO M5: find the first contact whose name equals name ignoring case,
    // remove it from the list, and return it. Return null if there is no such
    // contact. Use a counted loop - remove needs a position - and return the
    // moment you have removed one.
    Contact delete(String name) {
        return null;
    }
}

class Contact {

    // TODO M2.1: three private final String fields: name, email, phone.

    Contact(String name, String email, String phone) {
        // TODO M2.2: validate, then assign. Throw IllegalArgumentException with
        // a message the user can read when something is wrong:
        //   - name null or blank        -> "a name is required"
        //   - email null or failing
        //     looksLikeEmail            -> "\"<email>\" does not look like an email address"
        //   - phone null or blank       -> "a phone number is required"
        // Check for null FIRST in each pair: calling isBlank() on a null String
        // throws NullPointerException before your message ever gets a chance.
        //
        // Then assign the fields, calling .trim() on each one. Trimming here,
        // once, means nothing downstream has to worry about stray spaces.
    }

    // TODO M2.3: return true when candidate looks vaguely like an email.
    // Keep it deliberately loose - you are catching typos, not writing a
    // specification. Something before an @, then a dot somewhere after it with
    // at least one character on each side, is plenty:
    //   int at = candidate.indexOf('@');            -1 when there is no @ at all
    //   int dot = candidate.indexOf('.', at + 2);   search starting after the @
    // Then decide what makes those two numbers acceptable.
    private static boolean looksLikeEmail(String candidate) {
        return false;
    }

    // TODO M2.4: return the three fields.
    String getName() {
        return null;
    }

    String getEmail() {
        return null;
    }

    String getPhone() {
        return null;
    }

    // TODO M2.5: describe the contact the way the checkpoints show it:
    //     Ada Lovelace <ada@calc.org> 555-0101
    @Override
    public String toString() {
        return "a Contact (toString is not written yet)";
    }

    // TODO M6.1: add equals and hashCode here.
    // equals: two contacts are the same person when their names match ignoring
    // case. Follow the four steps from module 07 - same-object shortcut, null
    // and getClass check, cast, then compare with equalsIgnoreCase.
    // hashCode: Objects.hash(name.toLowerCase()). It must ignore case too, or
    // two equal contacts would disagree about which drawer they belong in.
}
