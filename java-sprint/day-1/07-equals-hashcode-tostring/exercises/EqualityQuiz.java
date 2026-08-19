// EXERCISE 3 - Predict before you run
//
// GOAL: find out which parts of == versus equals you actually believe and
// which parts you only half-remember. Guessing and being wrong here is worth
// more than reading the answer, so commit to a prediction before running.
//
// For each question, replace "TODO" with what you think Java prints - either
// "true" or "false", written as a String. Then run the file.
//
// Run it with:  java EqualityQuiz.java
//
// EXPECTED OUTPUT when you are finished: every question ends with
//
//   match? true
//
// The starter prints "match? false" ten times, which is exactly right for a
// file full of TODOs. Do not change the expressions - only the predictions.
//
// Read the two classes at the bottom before you start. One of them overrides
// equals and hashCode. The other does not. That difference is the whole quiz.

public class EqualityQuiz {

    public static void main(String[] args) {
        String s1 = "java";
        String s2 = "java";                 // the same literal, typed twice
        String s3 = new String("java");     // forced into its own object
        String s4 = "ja" + "va";            // two literals, joined
        String prefix = "ja";
        String s5 = prefix + "va";          // a variable, joined

        Plain p1 = new Plain("red");        // Plain does NOT override equals
        Plain p2 = new Plain("red");
        Tag t1 = new Tag("red");            // Tag DOES override equals
        Tag t2 = new Tag("red");

        // TODO: replace each "TODO" with "true" or "false".
        String a1 = "TODO";
        String a2 = "TODO";
        String a3 = "TODO";
        String a4 = "TODO";
        String a5 = "TODO";
        String a6 = "TODO";
        String a7 = "TODO";
        String a8 = "TODO";
        String a9 = "TODO";
        String a10 = "TODO";

        check(1, "s1 == s2", a1, s1 == s2);
        check(2, "s1 == s3", a2, s1 == s3);
        check(3, "s1.equals(s3)", a3, s1.equals(s3));
        check(4, "s1 == s4", a4, s1 == s4);
        check(5, "s1 == s5", a5, s1 == s5);
        check(6, "p1 == p2", a6, p1 == p2);
        check(7, "p1.equals(p2)", a7, p1.equals(p2));
        check(8, "t1.equals(t2)", a8, t1.equals(t2));
        check(9, "t1.hashCode() == t2.hashCode()", a9, t1.hashCode() == t2.hashCode());
        check(10, "t1.equals(\"red\")", a10, t1.equals("red"));
    }

    // Helper. Already written for you - just read it and move on.
    static void check(int number, String expression, String prediction, boolean actual) {
        String actualText = String.valueOf(actual);
        System.out.println("Q" + number + ":  " + expression);
        System.out.println("    you predicted : " + prediction);
        System.out.println("    Java prints   : " + actualText);
        System.out.println("    match? " + prediction.equals(actualText));
        System.out.println();
    }
}

// No toString, no equals, no hashCode. Whatever Object hands down is what
// this class gets.
class Plain {
    private final String name;

    Plain(String name) {
        this.name = name;
    }
}

// The same field, plus the two methods written properly.
class Tag {
    private final String name;

    Tag(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tag other = (Tag) o;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
