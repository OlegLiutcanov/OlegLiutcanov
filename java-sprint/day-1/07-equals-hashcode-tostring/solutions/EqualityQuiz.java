// EXERCISE 3 - SOLUTION
// Every question should report: match? true
// Run it with:  java EqualityQuiz.java

public class EqualityQuiz {

    public static void main(String[] args) {
        String s1 = "java";
        String s2 = "java";
        String s3 = new String("java");
        String s4 = "ja" + "va";
        String prefix = "ja";
        String s5 = prefix + "va";

        Plain p1 = new Plain("red");
        Plain p2 = new Plain("red");
        Tag t1 = new Tag("red");
        Tag t2 = new Tag("red");

        String a1 = "true";    // two identical literals share one pooled object
        String a2 = "false";   // new String() forces a separate object
        String a3 = "true";    // equals compares characters, so the source stops mattering
        String a4 = "true";    // "ja" + "va" is two literals: joined at compile time
        String a5 = "false";   // a variable is involved, so this string is built at run time
        String a6 = "false";   // two 'new' calls, so never the same object
        String a7 = "false";   // Plain never overrides equals, so it inherits == behaviour
        String a8 = "true";    // Tag does override equals, and compares its name field
        String a9 = "true";    // equal objects must agree on hashCode - Tag holds up its end
        String a10 = "false";  // getClass() != o.getClass(): a String is not a Tag

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

    static void check(int number, String expression, String prediction, boolean actual) {
        String actualText = String.valueOf(actual);
        System.out.println("Q" + number + ":  " + expression);
        System.out.println("    you predicted : " + prediction);
        System.out.println("    Java prints   : " + actualText);
        System.out.println("    match? " + prediction.equals(actualText));
        System.out.println();
    }
}

class Plain {
    private final String name;

    Plain(String name) {
        this.name = name;
    }
}

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
