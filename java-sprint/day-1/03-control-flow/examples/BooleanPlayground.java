// BooleanPlayground.java
// Comparison operators, the boolean type, and the && || ! trio.
// Run it with:  java BooleanPlayground.java

public class BooleanPlayground {

    public static void main(String[] args) {

        int temperature = 18;
        int wallet = 12;

        System.out.println("temperature = " + temperature + ", wallet = " + wallet);
        System.out.println();

        // ---- Every comparison hands back a boolean: true or false ----
        // The extra parentheses matter. Without them, + glues text onto the
        // number first and the comparison never happens.
        System.out.println("temperature > 20   -> " + (temperature > 20));
        System.out.println("temperature < 20   -> " + (temperature < 20));
        System.out.println("temperature >= 18  -> " + (temperature >= 18));
        System.out.println("temperature == 18  -> " + (temperature == 18));
        System.out.println("temperature != 18  -> " + (temperature != 18));
        System.out.println();

        // ---- A boolean is a value, so it can live in a variable ----
        boolean chilly = temperature < 20;
        boolean broke = wallet < 5;
        System.out.println("chilly = " + chilly + ", broke = " + broke);
        System.out.println();

        // ---- && (and), || (or), ! (not) ----
        System.out.println("chilly && broke  -> " + (chilly && broke));   // both must be true
        System.out.println("chilly || broke  -> " + (chilly || broke));   // at least one true
        System.out.println("!chilly          -> " + (!chilly));           // flips it
        System.out.println();

        // Combining them reads best with parentheses, even when Java does
        // not strictly need them.
        boolean goodCoffeeWeather = (temperature < 20) && (wallet >= 5);
        System.out.println("Coffee weather? " + goodCoffeeWeather);
        System.out.println();

        // ---- Strings: == asks a different question than you think ----
        String a = "java";

        String prefix = "ja";
        String built = prefix + "va";   // same letters, assembled while running

        System.out.println("a     = " + a);
        System.out.println("built = " + built);
        System.out.println("a == built      -> " + (a == built));        // false!
        System.out.println("a.equals(built) -> " + a.equals(built));     // true
        System.out.println();
        System.out.println("For text, always compare with .equals(...), never with ==.");
    }
}
