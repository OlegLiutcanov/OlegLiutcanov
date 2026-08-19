// ReferencesAndAliasing.java
// A variable does not hold an object. It holds a REFERENCE to one - an arrow
// pointing at it. Two arrows can point at the same object, and then a change
// made through either one is visible through both.
// This is the single most common source of "but I never changed that!".
// Run it with:  java ReferencesAndAliasing.java

public class ReferencesAndAliasing {

    public static void main(String[] args) {
        System.out.println("=== One object, two names ===");
        Player p1 = new Player("Ada");
        Player p2 = p1;          // copies the ARROW, not the object

        p1.addPoints(10);
        p2.addPoints(5);

        // Both lines report 15. There was only ever one Player.
        System.out.println("  p1 -> " + p1.getName() + " " + p1.getScore());
        System.out.println("  p2 -> " + p2.getName() + " " + p2.getScore());
        System.out.println("  p1 == p2 ? " + (p1 == p2) + "   (same object)");

        System.out.println();
        System.out.println("=== Two genuinely separate objects ===");
        Player a = new Player("Grace");
        Player b = new Player("Grace");   // a second 'new' = a second object

        a.addPoints(7);

        System.out.println("  a -> " + a.getName() + " " + a.getScore());
        System.out.println("  b -> " + b.getName() + " " + b.getScore());
        System.out.println("  a == b ? " + (a == b) + "  (different objects,");
        System.out.println("                     even though the names match)");

        System.out.println();
        System.out.println("=== What == really compares ===");
        // On objects, == asks "are these two arrows pointing at the very same
        // thing?" - never "do these hold equal values?". This is the same trap
        // you met with String in module 03, and now you know why it exists:
        // a String is an object too.
        System.out.println("  Comparing contents by hand instead:");
        System.out.println("  same name? " + a.getName().equals(b.getName()));
        System.out.println("  same score? " + (a.getScore() == b.getScore()));

        System.out.println();
        System.out.println("=== Passing an object to a method ===");
        Player carol = new Player("Carol");
        System.out.println("  before        -> " + carol.getName() + " " + carol.getScore());

        // Module 04 taught that a method gets a COPY of what you pass. That is
        // still true. What gets copied here is the ARROW - and the copy points
        // at the same Player, so changes made through it are real.
        bonus(carol);
        System.out.println("  after bonus   -> " + carol.getName() + " " + carol.getScore());

        // ...but pointing the copied arrow somewhere else changes only the
        // copy. The caller's variable still points where it always did.
        replace(carol);
        System.out.println("  after replace -> " + carol.getName() + " " + carol.getScore());
        System.out.println("  (replace built a new Player that nobody kept)");
    }

    // Reaches through the reference and changes the object it finds.
    static void bonus(Player player) {
        player.addPoints(100);
    }

    // Reassigns the parameter itself. The caller never sees this.
    static void replace(Player player) {
        player = new Player("Someone else");
        player.addPoints(9999);
    }
}

class Player {
    private String name;
    private int score;

    Player(String name) {
        this.name = name;
        this.score = 0;
    }

    void addPoints(int points) {
        score = score + points;
    }

    String getName() {
        return name;
    }

    int getScore() {
        return score;
    }
}
