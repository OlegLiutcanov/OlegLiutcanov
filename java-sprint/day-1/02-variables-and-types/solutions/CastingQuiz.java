// CastingQuiz.java - solution
// Every question should report: match? true
// Run it with:  java CastingQuiz.java

public class CastingQuiz {

    public static void main(String[] args) {

        String p1 = "3";                    // int / int drops the remainder
        String p2 = "3.5";                  // one double makes the whole division double
        String p3 = "3.0";                  // 7 / 2 ran first as ints, the cast came too late
        String p4 = "1";                    // 7 = 2*3 + 1, so the remainder is 1
        String p5 = "9";                    // casting a double to int truncates
        String p6 = "-9";                   // truncation goes toward zero, not down
        String p7 = "0.30000000000000004";  // double is binary and cannot hold 0.1 exactly
        String p8 = "Total: 12";            // text + 1 makes text, then + 2 appends again
        String p9 = "3 points";             // 1 + 2 are still numbers here, so they add
        String p10 = "-2147483648";         // int overflowed and wrapped to its minimum

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

    static void check(int number, String expression, String prediction, String actual) {
        System.out.println("Q" + number + ":  " + expression);
        System.out.println("    you predicted : " + prediction);
        System.out.println("    Java prints   : " + actual);
        System.out.println("    match? " + prediction.equals(actual));
        System.out.println();
    }
}
