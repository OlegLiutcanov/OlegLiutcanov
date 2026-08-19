// PointRecord.java
// The same Point as PointAfter.java, written as a record. All three methods
// come for free, and the class body is empty.
// Run it with:  java PointRecord.java
//
// NAMING NOTE: in real code this record would simply be called Point. It is
// called RecordPoint here only so that it can sit in the same folder as the
// hand-written Point in PointAfter.java - two types in one folder cannot share
// a name.

public class PointRecord {

    public static void main(String[] args) {
        RecordPoint a = new RecordPoint(3, 4);
        RecordPoint b = new RecordPoint(3, 4);
        RecordPoint c = new RecordPoint(3, 5);

        System.out.println("=== One line of code, three methods ===");
        System.out.println("  the whole declaration: record RecordPoint(int x, int y) { }");

        System.out.println();
        System.out.println("=== toString, free ===");
        System.out.println("  println(a) -> " + a);
        System.out.println("  The format is fixed: Type[field=value, field=value].");

        System.out.println();
        System.out.println("=== equals, free ===");
        System.out.println("  a == b            -> " + (a == b) + "   (== is unchanged, as always)");
        System.out.println("  a.equals(b)       -> " + a.equals(b));
        System.out.println("  a.equals(c)       -> " + a.equals(c) + "   (c is " + c + ")");
        System.out.println("  a.equals(null)    -> " + a.equals(null));
        System.out.println("  a.equals(\"three\") -> " + a.equals("three"));

        System.out.println();
        System.out.println("=== hashCode, free and consistent with equals ===");
        System.out.println("  a.equals(b) && a.hashCode() == b.hashCode() -> "
                + (a.equals(b) && a.hashCode() == b.hashCode()));

        System.out.println();
        System.out.println("=== Accessors, free (note: x(), not getX()) ===");
        System.out.println("  a.x() -> " + a.x());
        System.out.println("  a.y() -> " + a.y());
        System.out.println("  There is no setX. A record's fields are final - build a new");
        System.out.println("  one instead of changing this one.");

        System.out.println();
        System.out.println("=== You can still add your own methods ===");
        System.out.printf("  a.distanceFromOrigin() -> %.1f%n", a.distanceFromOrigin());
        System.out.println("  a.movedBy(1, 1)        -> " + a.movedBy(1, 1)
                + "   (a new record, a is untouched: " + a + ")");

        System.out.println();
        System.out.println("=== And you can still validate what goes in ===");
        Rating good = new Rating(4);
        System.out.println("  new Rating(4) -> " + good);
        try {
            Rating bad = new Rating(9);
            System.out.println("  new Rating(9) -> " + bad);
        } catch (IllegalArgumentException e) {
            System.out.println("  new Rating(9) -> rejected: " + e.getMessage());
        }

        System.out.println();
        System.out.println("Records fit when a type is a small bundle of values that");
        System.out.println("never changes. When it has real behaviour or mutable state,");
        System.out.println("write an ordinary class - and then write the three methods.");
    }
}

// x and y are called the record's COMPONENTS. From them Java generates the
// constructor, the accessors x() and y(), toString, equals and hashCode.
record RecordPoint(int x, int y) {

    double distanceFromOrigin() {
        return Math.sqrt(x * x + y * y);
    }

    // Returns a new record rather than modifying this one - the normal move
    // when your data is immutable.
    RecordPoint movedBy(int dx, int dy) {
        return new RecordPoint(x + dx, y + dy);
    }
}

record Rating(int stars) {

    // A COMPACT CONSTRUCTOR: no parameter list, no assignments. It runs before
    // the fields are set, so it is where you check the values you were handed.
    Rating {
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("stars must be 1 to 5, got " + stars);
        }
    }
}
