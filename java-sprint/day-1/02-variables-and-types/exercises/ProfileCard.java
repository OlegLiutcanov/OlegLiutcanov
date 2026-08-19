// ProfileCard.java
//
// GOAL: build a little text "profile card" using String methods.
// Run it with:  java ProfileCard.java
//
// EXPECTED OUTPUT when you are finished:
//
// === ADA LOVELACE ===
// --------------------
// City: London
// Bio length: 47 characters
// Mentions Java: true
// Typed "ada lovelace": equals -> false | equalsIgnoreCase -> true
//
// Useful String methods for this exercise:
//   text.length()                 how many characters
//   text.toUpperCase()            a new ALL CAPS copy
//   text.contains("abc")          true if "abc" appears somewhere
//   text.repeat(5)                a new String, five copies glued together
//   one.equals(two)               true if the letters match exactly
//   one.equalsIgnoreCase(two)     true if they match ignoring capitalisation

public class ProfileCard {

    static String header(String name) {
        // TODO: return "=== " + the name in capitals + " ==="
        // For "Ada Lovelace" that is: === ADA LOVELACE ===
        return null;
    }

    static String divider(String name) {
        // TODO: return a line of '-' characters exactly as long as header(name).
        // Hint: ask the header for its length, then use "-".repeat(...)
        return null;
    }

    static String bioSummary(String bio) {
        // TODO: return "Bio length: 47 characters" (with the real length, of course)
        return null;
    }

    static boolean mentionsJava(String bio) {
        // TODO: return true when the bio contains the word "Java"
        return false;
    }

    static String nameMatchReport(String stored, String typed) {
        // TODO: return "equals -> false | equalsIgnoreCase -> true"
        // using the two real comparison results, not hard-coded words.
        return null;
    }

    // main is already written for you. Do not change it.
    public static void main(String[] args) {
        String name = "Ada Lovelace";
        String city = "London";
        String bio = "Wrote the first algorithm; loves Java and math.";
        String typedName = "ada lovelace";

        System.out.println(header(name));
        System.out.println(divider(name));
        System.out.println("City: " + city);
        System.out.println(bioSummary(bio));
        System.out.println("Mentions Java: " + mentionsJava(bio));
        System.out.println("Typed \"" + typedName + "\": " + nameMatchReport(name, typedName));
    }
}
