// StringIdentityDemo.java
// Modules 02 and 03 told you: compare text with .equals, never with ==.
// They also admitted that == sometimes appears to work, and promised an
// explanation in module 07. This file is that explanation.
// Run it with:  java StringIdentityDemo.java

public class StringIdentityDemo {

    public static void main(String[] args) {
        String s1 = "java";
        String s2 = "java";                 // the same literal, written twice
        String s3 = new String("java");     // forced into a brand new object
        String s4 = "ja" + "va";            // two literals glued at compile time

        String prefix = "ja";
        String s5 = prefix + "va";          // built at run time from a variable

        System.out.println("=== Two identical literals ===");
        System.out.println("  s1 == s2      -> " + (s1 == s2) + "    <- this is why people stop trusting the rule");
        System.out.println();
        System.out.println("  Java keeps a pool of literal strings. Both lines asked for");
        System.out.println("  \"java\", and the pool handed out the same object twice.");
        System.out.println("  == is still comparing identity. There just happens to be");
        System.out.println("  only one object here, so it accidentally agrees.");

        System.out.println();
        System.out.println("=== The same text, deliberately a separate object ===");
        System.out.println("  s1 == s3      -> " + (s1 == s3) + "   (new String always makes a new object)");
        System.out.println("  s1.equals(s3) -> " + s1.equals(s3) + "    (equals looks at the characters)");

        System.out.println();
        System.out.println("=== Glued together from literals ===");
        System.out.println("  s1 == s4      -> " + (s1 == s4) + "    (\"ja\" + \"va\" is worked out at compile");
        System.out.println("                           time, so s4 IS the pooled \"java\")");

        System.out.println();
        System.out.println("=== Built at run time ===");
        System.out.println("  s1 == s5      -> " + (s1 == s5) + "   (a variable is involved, so the joining");
        System.out.println("                           happens while the program runs)");
        System.out.println("  s1.equals(s5) -> " + s1.equals(s5) + "    (equals does not care where it came from)");

        System.out.println();
        System.out.println("=== So what changed between s4 and s5? ===");
        System.out.println("  Nothing you can see, and that is the point. Whether == works");
        System.out.println("  on text depends on invisible details: literal or not, folded");
        System.out.println("  at compile time or not, typed by a user or not.");
        System.out.println();
        System.out.println("  Text that arrives from a Scanner, a file, or a network call is");
        System.out.println("  built while the program runs, so == on it is false essentially");
        System.out.println("  always - and your if statement silently never fires.");

        System.out.println();
        System.out.println("=== The takeaway ===");
        System.out.println("  String is just an object whose equals compares characters.");
        System.out.println("  == on any object asks one question: same object?");
        System.out.println("  Use .equals for text. Always. No exceptions worth learning.");
    }
}
