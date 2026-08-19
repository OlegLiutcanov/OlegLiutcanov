// CastingQuiz.java
//
// GOAL: predict before you run. This is the fastest way to find out which
// number rules you actually believe and which ones you only half-remember.
//
// For each question, replace "TODO" with the exact text you think Java
// would print. Write it as a String, for example "3" or "3.5" or "true".
// Then run the file and see how you did.
//
// Run it with:  java CastingQuiz.java
//
// EXPECTED OUTPUT when you are finished: every question ends with
//
//   match? true
//
// The starter file prints "match? false" ten times, which is correct
// for a file full of TODOs. Do not change the expressions themselves -
// only the prediction Strings.

public class CastingQuiz {

    public static void main(String[] args) {

        // TODO: replace each "TODO" with your prediction.
        String p1 = "TODO";
        String p2 = "TODO";
        String p3 = "TODO";
        String p4 = "TODO";
        String p5 = "TODO";
        String p6 = "TODO";
        String p7 = "TODO";
        String p8 = "TODO";
        String p9 = "TODO";
        String p10 = "TODO";

        check(1, "7 / 2", p1, String.valueOf(7 / 2));
        check(2, "7 / 2.0", p2, String.valueOf(7 / 2.0));
        check(3, "(double) (7 / 2)", p3, String.valueOf((double) (7 / 2)));
        check(4, "7 % 3", p4, String.valueOf(7 % 3));
        check(5, "(int) 9.99", p5, String.valueOf((int) 9.99));
        check(6, "(int) -9.99", p6, String.valueOf((int) -9.99));
        check(7, "0.1 + 0.2", p7, String.valueOf(0.1 + 0.2));
        check(8, "\"Total: \" + 1 + 2", p8, String.valueOf("Total: " + 1 + 2));
        check(9, "1 + 2 + \" points\"", p9, String.valueOf(1 + 2 + " points"));
        check(10, "Integer.MAX_VALUE + 1", p10, String.valueOf(Integer.MAX_VALUE + 1));
    }

    // Helper. Already written for you - just read it and move on.
    static void check(int number, String expression, String prediction, String actual) {
        System.out.println("Q" + number + ":  " + expression);
        System.out.println("    you predicted : " + prediction);
        System.out.println("    Java prints   : " + actual);
        System.out.println("    match? " + prediction.equals(actual));
        System.out.println();
    }
}
