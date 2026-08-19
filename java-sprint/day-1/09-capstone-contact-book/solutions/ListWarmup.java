// ListWarmup.java - solution
// Run it with:  java ListWarmup.java

import java.util.ArrayList;

public class ListWarmup {

    static int passed = 0;
    static int total = 0;

    static int countLongerThan(ArrayList<String> words, int minLength) {
        int count = 0;
        for (String word : words) {
            if (word.length() > minLength) {
                count++;
            }
        }
        return count;
    }

    static String longest(ArrayList<String> words) {
        // Starting at null rather than "" means the empty list answers null,
        // and the very first word always wins the first comparison.
        String best = null;
        for (String word : words) {
            if (best == null || word.length() > best.length()) {
                best = word;
            }
        }
        return best;
    }

    static ArrayList<String> startingWith(ArrayList<String> words, String prefix) {
        ArrayList<String> matches = new ArrayList<>();
        for (String word : words) {
            if (word.startsWith(prefix)) {
                matches.add(word);
            }
        }
        return matches;
    }

    static boolean removeFirstMatch(ArrayList<String> words, String target) {
        // Counted loop, because remove needs a position. Returning immediately
        // means the loop never continues over a list that just changed size.
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).equalsIgnoreCase(target)) {
                words.remove(i);
                return true;
            }
        }
        return false;
    }

    static boolean containsIgnoreCase(ArrayList<String> words, String target) {
        for (String word : words) {
            if (word.equalsIgnoreCase(target)) {
                return true;
            }
        }
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
