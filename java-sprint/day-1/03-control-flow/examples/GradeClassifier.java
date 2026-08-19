// GradeClassifier.java
// An if / else if / else chain, and a demonstration of why the ORDER of the
// branches decides whether the chain is correct.
// Run it with:  java GradeClassifier.java

public class GradeClassifier {

    // The chain is checked top to bottom. The first test that is true wins,
    // and every branch below it is skipped.
    static String letterFor(int score) {
        if (score < 0 || score > 100) {
            return "invalid";
        } else if (score >= 90) {
            return "A";
        } else if (score >= 80) {
            return "B";
        } else if (score >= 70) {
            return "C";
        } else if (score >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    // The same tests in the wrong order. Nothing here fails to compile, and
    // nothing crashes - it just quietly gives wrong answers.
    static String letterForBroken(int score) {
        if (score >= 60) {
            return "D";
        } else if (score >= 70) {
            return "C";
        } else if (score >= 80) {
            return "B";
        } else if (score >= 90) {
            return "A";
        } else {
            return "F";
        }
    }

    static void show(int score) {
        System.out.println("  score " + score + "\tcorrect: " + letterFor(score)
                + "\twrong order: " + letterForBroken(score));
    }

    public static void main(String[] args) {
        System.out.println("Two versions of the same grading rules:");
        show(97);
        show(85);
        show(73);
        show(64);
        show(41);

        System.out.println();
        System.out.println("Every score of 60 or more comes out as D on the right,");
        System.out.println("because the first test that matches ends the chain.");

        System.out.println();
        System.out.println("Scores outside 0-100 are caught by the very first test:");
        System.out.println("  letterFor(-5)  = " + letterFor(-5));
        System.out.println("  letterFor(130) = " + letterFor(130));
        System.out.println("  letterFor(100) = " + letterFor(100));

        // An if with no else is perfectly normal: do something, or do nothing.
        int myScore = 88;
        if (letterFor(myScore).equals("B")) {
            System.out.println();
            System.out.println("A score of " + myScore + " is a solid B. Nice.");
        }
    }
}
