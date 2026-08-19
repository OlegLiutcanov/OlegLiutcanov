// TryCatchBasics.java
// The mechanics of try / catch / finally, one idea at a time.
// Run it with:  java TryCatchBasics.java

public class TryCatchBasics {

    public static void main(String[] args) {

        System.out.println("=== 1. Catching one specific problem ===");
        System.out.println("  parseOrDefault(\"42\", 0)   = " + parseOrDefault("42", 0));
        System.out.println("  parseOrDefault(\"lots\", 0) = " + parseOrDefault("lots", 0));
        System.out.println("  ...and the program is still running.");

        System.out.println();
        System.out.println("=== 2. What is inside the exception object ===");
        try {
            Integer.parseInt("7x");
        } catch (NumberFormatException e) {
            // 'e' is a normal object. You can ask it questions.
            System.out.println("  short type name : " + e.getClass().getSimpleName());
            System.out.println("  full type name  : " + e.getClass().getName());
            System.out.println("  message         : " + e.getMessage());
            System.out.println("  printed whole   : " + e);
        }

        System.out.println();
        System.out.println("=== 3. Several catch blocks: the first matching one wins ===");
        String[] words = { "42", null, "seven" };
        describe(words, 0);    // fine
        describe(words, 1);    // null in the array
        describe(words, 2);    // not a number
        describe(words, 5);    // no such slot

        System.out.println();
        System.out.println("=== 4. finally runs either way ===");
        System.out.println("  measuring \"hello\":");
        System.out.println("  result = " + riskyLength("hello"));
        System.out.println("  measuring null:");
        System.out.println("  result = " + riskyLength(null));

        System.out.println();
        System.out.println("Reached the end of main normally.");
    }

    // The shape you will write most often: try the thing that might fail, and
    // if it does, fall back to something sensible instead of crashing.
    static int parseOrDefault(String text, int fallback) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    // Four catch blocks. Java checks them top to bottom and runs the first one
    // whose type matches. Exception is last on purpose: it matches everything,
    // so anything below it could never run.
    static void describe(String[] words, int index) {
        try {
            String word = words[index];
            System.out.println("  slot " + index + " -> \"" + word + "\" has "
                    + word.length() + " characters and is the number " + Integer.parseInt(word));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("  slot " + index + " -> there is no slot " + index + " (" + e.getMessage() + ")");
        } catch (NullPointerException e) {
            System.out.println("  slot " + index + " -> the slot holds null, so there is nothing to measure");
        } catch (NumberFormatException e) {
            System.out.println("  slot " + index + " -> readable text, but not a number (" + e.getMessage() + ")");
        } catch (Exception e) {
            System.out.println("  slot " + index + " -> something unexpected: " + e);
        }
    }

    // finally runs on the way out no matter which door you leave by - the try
    // block returning, or the catch block returning.
    static int riskyLength(String text) {
        try {
            System.out.println("    try     : measuring " + text);
            return text.length();
        } catch (NullPointerException e) {
            System.out.println("    catch   : it was null, using -1");
            return -1;
        } finally {
            System.out.println("    finally : always runs, before the value actually leaves");
        }
    }
}
