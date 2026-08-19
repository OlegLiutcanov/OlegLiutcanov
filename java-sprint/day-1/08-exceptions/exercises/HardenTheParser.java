// HardenTheParser.java
//
// This file COMPILES as given. It also crashes on the third row, which is the
// point: your job is to make it survive every row in the list.
//
// Run it once before changing anything, and read the stack trace. Which method?
// Which line? Which row was being processed?
//
// Expected output when you are done:
//
// === Signup report ===
//   OK      Ada is 36
//   OK      Grace is 45
//   SKIPPED "Alan,abc" -> age is not a whole number: "abc"
//   SKIPPED "Katherine" -> a row must look like name,age
//   SKIPPED ",30" -> the name must not be empty
//   SKIPPED "Linus,-4" -> age must be between 0 and 130, got -4
//   OK      Sofia is 28
//   OK      Barbara is 89
//   SKIPPED (missing row) -> a row must not be null
// Accepted 4 of 9 rows.
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
            // TODO 6: a bad row must not stop the report. Wrap these two lines
            //         in a try block, and add:
            //
            //         catch (IllegalArgumentException e) {
            //             System.out.println("  SKIPPED " + label(row) + " -> " + e.getMessage());
            //         }
            //
            //         Note that 'accepted++' belongs INSIDE the try, after the
            //         println - a skipped row must not be counted.
            System.out.println("  OK      " + describe(row));
            accepted++;
        }

        System.out.println("Accepted " + accepted + " of " + rows.length + " rows.");
    }

    // Turns one raw row into a sentence, or refuses it with an
    // IllegalArgumentException that explains exactly what was wrong.
    // The messages below are copied character for character into the expected
    // output above, so use them exactly as written.
    static String describe(String row) {

        // TODO 1: if row is null, throw new IllegalArgumentException("a row must not be null")
        //         This has to come first - every line below would crash on a null.

        String[] parts = row.split(",");

        // TODO 2: if parts.length is not exactly 2, throw
        //         new IllegalArgumentException("a row must look like name,age")

        String name = parts[0];

        // TODO 3: trim the spaces off name (String has a .trim() method), then
        //         if it is empty, throw
        //         new IllegalArgumentException("the name must not be empty")
        //         Hint: "".isEmpty() is true.

        int age = Integer.parseInt(parts[1]);

        // TODO 4: parts[1] may not be a number at all. Wrap that parseInt call
        //         in its own try/catch, trim parts[1] first, and in the catch
        //         throw new IllegalArgumentException(
        //                 "age is not a whole number: \"" + parts[1].trim() + "\"")
        //         This is the useful move: catch a low-level exception and
        //         rethrow it as one that means something to the caller.

        // TODO 5: if age is below 0 or above 130, throw
        //         new IllegalArgumentException("age must be between 0 and 130, got " + age)

        return name + " is " + age;
    }

    // Given to you, so the report lines line up exactly. Nothing to change.
    static String label(String row) {
        if (row == null) {
            return "(missing row)";
        }
        return "\"" + row + "\"";
    }
}
