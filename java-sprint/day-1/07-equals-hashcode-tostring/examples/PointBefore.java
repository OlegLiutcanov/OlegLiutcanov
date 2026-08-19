// PointBefore.java
// A class with nothing overridden - exactly what Java hands you by default.
// There are three surprises in here, and the third one is the one that matters.
// Run it with:  java PointBefore.java
//
// NOTE: the hex digits below are arbitrary and may differ on your machine.
// Nothing in this lesson ever depends on them.

public class PointBefore {

    public static void main(String[] args) {
        PlainPoint a = new PlainPoint(3, 4);
        PlainPoint b = new PlainPoint(3, 4);   // same numbers, a second 'new'
        PlainPoint alias = a;                  // same object, a second name

        System.out.println("=== Surprise 1: printing tells you nothing ===");
        System.out.println("  println(a) -> " + a);
        System.out.println("  println(b) -> " + b);
        System.out.println();
        System.out.println("  Two objects that both hold (3, 4), described as line noise.");
        System.out.println("  Java has no idea which fields you would want to see, so it");
        System.out.println("  falls back on the class name and a hash code.");

        System.out.println();
        System.out.println("=== Surprise 2: == says they are different ===");
        System.out.println("  a == b     -> " + (a == b) + "   (two separate objects)");
        System.out.println("  a == alias -> " + (a == alias) + "    (one object, two names)");
        System.out.println();
        System.out.println("  No surprise yet if you remember module 05: == compares");
        System.out.println("  arrows, not contents.");

        System.out.println();
        System.out.println("=== Surprise 3: equals says they are different TOO ===");
        System.out.println("  a.equals(b)     -> " + a.equals(b));
        System.out.println("  a.equals(alias) -> " + a.equals(alias));
        System.out.println();
        System.out.println("  This is the one that catches everybody.");
        System.out.println("  You never wrote an equals method, so that call runs the one");
        System.out.println("  every class inherits - and the inherited version does exactly");
        System.out.println("  what == does. Same object, or nothing.");

        System.out.println();
        System.out.println("=== The fields were equal the whole time ===");
        System.out.println("  a.getX() == b.getX() && a.getY() == b.getY() -> "
                + (a.getX() == b.getX() && a.getY() == b.getY()));
        System.out.println();
        System.out.println("  Java simply never looked at them. Nobody told it to.");
        System.out.println("  Next: PointAfter.java, where we tell it.");
    }
}

// Notice what is NOT in this class: no toString, no equals, no hashCode.
// It still has all three of them - inherited - which is the whole problem.
class PlainPoint {
    private final int x;
    private final int y;

    PlainPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }
}
