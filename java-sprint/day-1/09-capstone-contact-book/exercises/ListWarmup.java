// ListWarmup.java
//
// GOAL: five small methods over an ArrayList<String>. They are the warm-up for
// the capstone, because each one is a miniature of something the contact book
// needs: counting, finding the best item, collecting matches into a new list,
// removing by name, and checking whether something is already in there.
//
// The test harness at the bottom is already written. DO NOT CHANGE IT.
// The file compiles and runs as given - it just gets 6 of 12 checks right,
// which is what a file full of placeholders manages by accident. Look at WHICH
// six before you start: they are all the "should be nothing" ones. Placeholders
// are very good at finding nothing.
//
// Run it with:  java ListWarmup.java
//
// EXPECTED OUTPUT when you are finished:
//
// === ArrayList warm-up ===
//  1. countLongerThan(words, 5) is 3                     true
//  2. countLongerThan(words, 99) is 0                    true
//  3. longest(words) is apricot                          true
//  4. longest(empty) is null                             true
//  5. startingWith(words, "ap") is [apple, apricot]      true
//  6. startingWith(words, "z") is empty                  true
//  7. removeFirstMatch(words, "BANANA") is true          true
//  8.   ...and the list is now [apple, apricot, cherry]  true
//  9. removeFirstMatch(words, "durian") is false         true
// 10.   ...and the list still holds 4 words              true
// 11. containsIgnoreCase(words, "CHERRY") is true        true
// 12. containsIgnoreCase(words, "grape") is false        true
//
// 12 of 12 checks passed.

import java.util.ArrayList;

public class ListWarmup {

    static int passed = 0;
    static int total = 0;

    // TODO 1: count how many words are STRICTLY longer than minLength.
    // A for-each loop plus word.length() is all you need. "apple" has 5
    // letters, so it does not count as longer than 5.
    static int countLongerThan(ArrayList<String> words, int minLength) {
        return 0;
    }

    // TODO 2: return the longest word in the list, or null if the list is empty.
    // Hint: keep a "best so far" variable. Start it at null, and treat null as
    // "anything beats this" in your comparison.
    static String longest(ArrayList<String> words) {
        return null;
    }

    // TODO 3: return a NEW list holding only the words that start with prefix.
    // Do not change the list you were handed. String has a startsWith method.
    // This is exactly how the contact book will do its search.
    static ArrayList<String> startingWith(ArrayList<String> words, String prefix) {
        return new ArrayList<>();
    }

    // TODO 4: remove the first word that equals target ignoring case, and
    // return true. If nothing matches, change nothing and return false.
    // Hint: you need the POSITION to remove something, so use a counted loop
    // (for int i...) and return the moment you have removed one.
    static boolean removeFirstMatch(ArrayList<String> words, String target) {
        return false;
    }

    // TODO 5: true when some word equals target ignoring case.
    // This is the duplicate guard the contact book needs, in miniature.
    static boolean containsIgnoreCase(ArrayList<String> words, String target) {
        return false;
    }

    // ------------------------------------------------------------------
    // Test harness. Leave it alone.
    // ------------------------------------------------------------------

    static ArrayList<String> sample() {
        ArrayList<String> words = new ArrayList<>();
        words.add("apple");
        words.add("banana");
        words.add("apricot");
        words.add("cherry");
        return words;
    }

    static void check(String label, boolean condition) {
        total++;
        if (condition) {
            passed++;
        }
        System.out.printf("%2d. %-50s %s%n", total, label, condition);
    }

    public static void main(String[] args) {
        System.out.println("=== ArrayList warm-up ===");

        ArrayList<String> words = sample();
        ArrayList<String> empty = new ArrayList<>();

        check("countLongerThan(words, 5) is 3", countLongerThan(words, 5) == 3);
        check("countLongerThan(words, 99) is 0", countLongerThan(words, 99) == 0);

        check("longest(words) is apricot", "apricot".equals(longest(words)));
        check("longest(empty) is null", longest(empty) == null);

        check("startingWith(words, \"ap\") is [apple, apricot]",
                "[apple, apricot]".equals(startingWith(words, "ap").toString()));
        check("startingWith(words, \"z\") is empty", startingWith(words, "z").size() == 0);

        ArrayList<String> hit = sample();
        check("removeFirstMatch(words, \"BANANA\") is true", removeFirstMatch(hit, "BANANA"));
        check("  ...and the list is now [apple, apricot, cherry]",
                "[apple, apricot, cherry]".equals(hit.toString()));

        ArrayList<String> miss = sample();
        check("removeFirstMatch(words, \"durian\") is false", !removeFirstMatch(miss, "durian"));
        check("  ...and the list still holds 4 words", miss.size() == 4);

        check("containsIgnoreCase(words, \"CHERRY\") is true", containsIgnoreCase(words, "CHERRY"));
        check("containsIgnoreCase(words, \"grape\") is false", !containsIgnoreCase(words, "grape"));

        System.out.println();
        System.out.println(passed + " of " + total + " checks passed.");
    }
}
