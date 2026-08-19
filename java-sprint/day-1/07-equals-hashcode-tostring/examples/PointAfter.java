// PointAfter.java
// The same little class as PointBefore.java, with all three methods written in.
// Run it with:  java PointAfter.java
//
// Compare the output side by side with PointBefore.java. The class gained
// twenty lines and stopped being useless.

import java.util.Objects;

public class PointAfter {

    public static void main(String[] args) {
        Point a = new Point(3, 4);
        Point b = new Point(3, 4);
        Point alias = a;
        Point c = new Point(3, 5);

        System.out.println("=== toString: println finally says something ===");
        System.out.println("  println(a)     -> " + a);
        System.out.println("  \"start: \" + a  -> " + "start: " + a);
        System.out.println("  a.toString()   -> " + a.toString());
        System.out.println();
        System.out.println("  All three lines are the same call. println never had a");
        System.out.println("  special ability to print objects - it just asks the object.");

        System.out.println();
        System.out.println("=== equals: contents instead of identity ===");
        System.out.println("  a == b          -> " + (a == b) + "   (still two objects - == did not change)");
        System.out.println("  a.equals(b)     -> " + a.equals(b) + "    (same contents - this is the part we taught it)");
        System.out.println("  a.equals(alias) -> " + a.equals(alias));
        System.out.println("  a.equals(c)     -> " + a.equals(c) + "   (c is " + c + ")");

        System.out.println();
        System.out.println("=== equals handles the awkward cases without blowing up ===");
        System.out.println("  a.equals(a)             -> " + a.equals(a) + "    (the shortcut at the top)");
        System.out.println("  a.equals(null)          -> " + a.equals(null) + "   (never an exception)");
        System.out.println("  a.equals(\"Point(3, 4)\") -> " + a.equals("Point(3, 4)") + "   (a String is not a Point)");

        System.out.println();
        System.out.println("=== hashCode: equal objects, equal hash codes ===");
        System.out.println("  a.hashCode() -> " + a.hashCode());
        System.out.println("  b.hashCode() -> " + b.hashCode());
        System.out.println("  a.equals(b) && a.hashCode() == b.hashCode() -> "
                + (a.equals(b) && a.hashCode() == b.hashCode()));
        System.out.println();
        System.out.println("  c.hashCode() -> " + c.hashCode() + "   (different contents, so it is allowed");
        System.out.println("                        to differ - and here it does)");
        System.out.println();
        System.out.println("  Those numbers are not magic: Objects.hash(3, 4) mixes the");
        System.out.println("  fields together with a fixed recipe, so equal fields always");
        System.out.println("  produce the same number. Why that matters: HashCodeMatters.java.");
    }
}

class Point {
    private final int x;
    private final int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // WHAT PRINTLN CALLS. Any format you like - make it readable for a human
    // reading a log at 2am, and include the fields that identify the object.
    @Override
    public String toString() {
        return "Point(" + x + ", " + y + ")";
    }

    // Read this top to bottom - each line handles one case, in order.
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;              // same object: done, no field work needed
        }
        if (o == null || getClass() != o.getClass()) {
            return false;             // null, or not a Point at all
        }
        Point other = (Point) o;      // now the cast is guaranteed safe
        return x == other.x && y == other.y;   // ints: compare with ==
    }

    // The rule: if two objects are equal, these numbers MUST match.
    // Objects.hash does the mixing for you. List the same fields equals uses.
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
