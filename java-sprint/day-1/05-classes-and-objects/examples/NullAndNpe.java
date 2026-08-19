// NullAndNpe.java
// null is the value a reference has when it points at nothing at all.
// Calling a method on nothing is a NullPointerException - the crash you will
// see more than any other. This program triggers three of them ON PURPOSE and
// catches each one so it can print the message for you to read.
//
// ABOUT try/catch: it appears below only as a safety net, so this file can
// survive its own crashes and keep printing. Read
//     try { ... } catch (NullPointerException e) { ... }
// as "attempt the first block; if it blows up in this particular way, run the
// second block instead of stopping the program". That is all you need here -
// exceptions get a module of their own later. In your own code you fix the
// null, you do not catch it.
//
// Run it with:  java NullAndNpe.java

public class NullAndNpe {

    public static void main(String[] args) {
        System.out.println("=== null is a real value you can hold and test ===");
        Contact nobody = null;               // a valid reference pointing nowhere
        System.out.println("  nobody        = " + nobody);
        System.out.println("  nobody == null? " + (nobody == null));

        System.out.println();
        System.out.println("=== Crash 1: calling a method on null ===");
        try {
            // Reading a null reference is fine. Following it is not.
            System.out.println(nobody.getName());
        } catch (NullPointerException e) {
            System.out.println("  NullPointerException:");
            System.out.println("    " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Crash 2: a field that was never given a value ===");
        // The constructor sets 'name' but leaves 'email' alone, so email keeps
        // its default of null. Nothing goes wrong until something follows it.
        Contact ada = new Contact("Ada");
        System.out.println("  ada.getName()  = " + ada.getName());
        System.out.println("  ada.getEmail() = " + ada.getEmail() + "   (no crash: just printing it)");
        try {
            System.out.println(ada.getEmail().toUpperCase());
        } catch (NullPointerException e) {
            System.out.println("  NullPointerException:");
            System.out.println("    " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Crash 3: a method handed a null ===");
        try {
            System.out.println(shout(null));
        } catch (NullPointerException e) {
            System.out.println("  NullPointerException:");
            System.out.println("    " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Fix 1: check before you follow ===");
        printEmail(ada);
        printEmail(new Contact("Grace", "grace@example.com"));

        System.out.println();
        System.out.println("=== Fix 2: never let it be null in the first place ===");
        // The better cure is usually prevention: have the constructor put in a
        // real value so no caller ever meets a null.
        Contact safe = new Contact("Alan", null);
        System.out.println("  safe.getEmail() = " + safe.getEmail());
        System.out.println("  ...so this is simply safe: " + safe.getEmail().toUpperCase());

        System.out.println();
        System.out.println("=== null is not the same as an empty string ===");
        String empty = "";
        String missing = null;
        System.out.println("  empty.length() = " + empty.length() + "   (an actual String, with 0 characters)");
        System.out.println("  missing        = " + missing + " (no String at all - .length() would crash)");
    }

    static String shout(String text) {
        return text.toUpperCase();
    }

    static void printEmail(Contact contact) {
        if (contact == null || contact.getEmail() == null) {
            System.out.println("  no email on file");
            return;
        }
        System.out.println("  " + contact.getEmail());
    }
}

class Contact {
    private String name;
    private String email;

    Contact(String name) {
        this.name = name;
        // email is deliberately not set here, so it stays null. This is how
        // most accidental nulls are born.
    }

    Contact(String name, String email) {
        this.name = name;
        // A constructor is a good place to refuse null and substitute
        // something harmless.
        if (email == null) {
            this.email = "none@example.com";
        } else {
            this.email = email;
        }
    }

    String getName() {
        return name;
    }

    String getEmail() {
        return email;
    }
}
