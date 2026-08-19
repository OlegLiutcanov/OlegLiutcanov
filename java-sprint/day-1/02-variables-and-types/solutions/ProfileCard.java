// ProfileCard.java - solution
// Run it with:  java ProfileCard.java

public class ProfileCard {

    static String header(String name) {
        return "=== " + name.toUpperCase() + " ===";
    }

    static String divider(String name) {
        // Build the header once and measure it, so the divider can never
        // drift out of sync with the line above it.
        return "-".repeat(header(name).length());
    }

    static String bioSummary(String bio) {
        return "Bio length: " + bio.length() + " characters";
    }

    static boolean mentionsJava(String bio) {
        return bio.contains("Java");
    }

    static String nameMatchReport(String stored, String typed) {
        return "equals -> " + stored.equals(typed)
                + " | equalsIgnoreCase -> " + stored.equalsIgnoreCase(typed);
    }

    public static void main(String[] args) {
        String name = "Ada Lovelace";
        String city = "London";
        String bio = "Wrote the first algorithm; loves Java and math.";
        String typedName = "ada lovelace";

        System.out.println(header(name));
        System.out.println(divider(name));
        System.out.println("City: " + city);
        System.out.println(bioSummary(bio));
        System.out.println("Mentions Java: " + mentionsJava(bio));
        System.out.println("Typed \"" + typedName + "\": " + nameMatchReport(name, typedName));
    }
}
