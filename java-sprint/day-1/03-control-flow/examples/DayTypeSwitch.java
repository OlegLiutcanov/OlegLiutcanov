// DayTypeSwitch.java
// The modern arrow switch (Java 14 and later), which is what you should write
// today. Compare it with the if / else if chain doing the same job.
// Run it with:  java DayTypeSwitch.java

public class DayTypeSwitch {

    // A switch EXPRESSION: the whole switch produces one value, which we
    // return. Notice the semicolon after the closing brace - the switch is
    // part of a statement here, not a block on its own.
    static String dayType(String day) {
        return switch (day) {
            case "SATURDAY", "SUNDAY" -> "weekend";
            case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> "workday";
            default -> "not a day I recognise";
        };
    }

    // Exactly the same rules written as a chain. Correct, but wordier, and it
    // repeats the word "day" seven times.
    static String dayTypeWithIf(String day) {
        if (day.equals("SATURDAY") || day.equals("SUNDAY")) {
            return "weekend";
        } else if (day.equals("MONDAY") || day.equals("TUESDAY") || day.equals("WEDNESDAY")
                || day.equals("THURSDAY") || day.equals("FRIDAY")) {
            return "workday";
        } else {
            return "not a day I recognise";
        }
    }

    // When one branch needs more than a single value, give it a block and end
    // the block with 'yield', which means "this is the value of the switch".
    static String moodFor(String day) {
        return switch (day) {
            case "MONDAY" -> "brace yourself";
            case "FRIDAY" -> {
                String bonus = " (and the weekend is next)";
                yield "almost there" + bonus;
            }
            case "SATURDAY", "SUNDAY" -> "no alarm today";
            default -> "steady";
        };
    }

    static void report(String day) {
        System.out.println(day + " -> " + dayType(day)
                + " | if-version agrees: " + dayType(day).equals(dayTypeWithIf(day))
                + " | mood: " + moodFor(day));
    }

    public static void main(String[] args) {
        report("MONDAY");
        report("WEDNESDAY");
        report("FRIDAY");
        report("SATURDAY");
        report("SUNDAY");
        report("CATURDAY");

        System.out.println();

        // A switch STATEMENT with arrows: each branch just does something and
        // produces no value. Same syntax, no 'return', no 'yield'.
        String meal = "lunch";
        switch (meal) {
            case "breakfast" -> System.out.println("Porridge it is.");
            case "lunch" -> System.out.println("Sandwich time.");
            case "dinner" -> System.out.println("Something with rice.");
            default -> System.out.println("Not a meal, but fine.");
        }
    }
}
