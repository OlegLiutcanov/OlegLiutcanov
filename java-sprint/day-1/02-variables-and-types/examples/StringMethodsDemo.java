// StringMethodsDemo.java
// String is not a primitive - it is an object, and objects come with methods.
// Run it with:  java StringMethodsDemo.java

public class StringMethodsDemo {

    public static void main(String[] args) {

        String language = "Java";
        String tagline = "  Write once, run anywhere.  ";

        System.out.println("--- Asking a String about itself ---");
        System.out.println("language           = " + language);
        System.out.println("length()           = " + language.length());
        System.out.println("toUpperCase()      = " + language.toUpperCase());
        System.out.println("toLowerCase()      = " + language.toLowerCase());
        System.out.println("contains(\"av\")     = " + language.contains("av"));
        System.out.println("startsWith(\"J\")    = " + language.startsWith("J"));
        System.out.println("charAt(0)          = " + language.charAt(0));
        System.out.println("repeat(3)          = " + language.repeat(3));

        System.out.println();
        System.out.println("--- The original is never changed ---");
        String shouted = language.toUpperCase();
        System.out.println("shouted  = " + shouted);
        System.out.println("language = " + language + "   <-- still the original");

        System.out.println();
        System.out.println("--- trim() removes leading and trailing spaces ---");
        System.out.println("before trim: [" + tagline + "]");
        System.out.println("after  trim: [" + tagline.trim() + "]");

        System.out.println();
        System.out.println("--- Gluing text together with + ---");
        String firstName = "Ada";
        String lastName = "Lovelace";
        String fullName = firstName + " " + lastName;
        System.out.println("fullName = " + fullName);

        int year = 1843;
        // A number next to a String is turned into text automatically.
        System.out.println("Published in " + year + ".");

        // Watch the order! + goes left to right.
        System.out.println("1 + 2 + \" points\"  = " + (1 + 2 + " points"));
        System.out.println("\"Points: \" + 1 + 2 = " + ("Points: " + 1 + 2));

        System.out.println();
        System.out.println("--- Comparing text: use equals, not == ---");
        String a = "java";
        String b = new String("java");   // deliberately a separate object with the same letters
        System.out.println("a == b               = " + (a == b) + "   <-- asks 'same box?'");
        System.out.println("a.equals(b)          = " + a.equals(b) + "    <-- asks 'same letters?'");
        System.out.println("a.equalsIgnoreCase(\"JAVA\") = " + a.equalsIgnoreCase("JAVA"));
        System.out.println("Rule of thumb: for text you almost always want .equals()");
    }
}
