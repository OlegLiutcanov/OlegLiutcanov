// HardenTheParser.java - solution
//
// Run it with:  java HardenTheParser.java

public class HardenTheParser {

    public static void main(String[] args) {
        String[] rows = {
            "Ada,36",
            "Grace,45",
            "Alan,abc",
            "Katherine",
            ",30",
            "Linus,-4",
            " Sofia , 28 ",
            "Barbara,89",
            null
        };

        int accepted = 0;

        System.out.println("=== Signup report ===");
        for (String row : rows) {
            try {
                System.out.println("  OK      " + describe(row));
                accepted++;
            } catch (IllegalArgumentException e) {
                System.out.println("  SKIPPED " + label(row) + " -> " + e.getMessage());
            }
        }

        System.out.println("Accepted " + accepted + " of " + rows.length + " rows.");
    }

    static String describe(String row) {
        if (row == null) {
            throw new IllegalArgumentException("a row must not be null");
        }

        String[] parts = row.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("a row must look like name,age");
        }

        String name = parts[0].trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("the name must not be empty");
        }

        String ageText = parts[1].trim();
        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            // Catch the low-level failure and rethrow it as the one this method
            // promises. The caller deals in rows, not in parseInt.
            throw new IllegalArgumentException("age is not a whole number: \"" + ageText + "\"");
        }

        if (age < 0 || age > 130) {
            throw new IllegalArgumentException("age must be between 0 and 130, got " + age);
        }

        return name + " is " + age;
    }

    static String label(String row) {
        if (row == null) {
            return "(missing row)";
        }
        return "\"" + row + "\"";
    }
}
