// OverloadingDemo.java
// Overloading: several methods sharing one name, told apart by their
// parameters. Java picks the right one at compile time based on what you pass.
// Run it with:  java OverloadingDemo.java

public class OverloadingDemo {

    // Same name, different parameter TYPES.
    static int add(int a, int b) {
        System.out.println("  [add(int, int) ran]");
        return a + b;
    }

    static double add(double a, double b) {
        System.out.println("  [add(double, double) ran]");
        return a + b;
    }

    // Same name, different NUMBER of parameters.
    static int add(int a, int b, int c) {
        System.out.println("  [add(int, int, int) ran]");
        return a + b + c;
    }

    static String add(String a, String b) {
        System.out.println("  [add(String, String) ran]");
        return a + b;
    }

    // A very common use of overloading: one short version for the everyday
    // case, one longer version with extra control. The short one hands the
    // work to the long one, so the actual logic exists in exactly one place.
    static String greet(String name) {
        return greet(name, "Hello");
    }

    static String greet(String name, String salutation) {
        return salutation + ", " + name + "!";
    }

    // NOT ALLOWED - uncomment either of these and the file stops compiling:
    //
    //   static double add(int a, int b) { return a + b; }
    //     error: method add(int,int) is already defined in class OverloadingDemo
    //     (the return type is not part of what distinguishes overloads)
    //
    //   static int add(int first, int second) { return first + second; }
    //     same error - renaming the parameters changes nothing, only their
    //     types and count matter

    public static void main(String[] args) {
        System.out.println("add(2, 3):");
        System.out.println("  -> " + add(2, 3));

        System.out.println("add(2.5, 3.5):");
        System.out.println("  -> " + add(2.5, 3.5));

        System.out.println("add(1, 2, 3):");
        System.out.println("  -> " + add(1, 2, 3));

        System.out.println("add(\"Ja\", \"va\"):");
        System.out.println("  -> " + add("Ja", "va"));

        // Mixed types: there is no add(int, double), so Java widens the 2 into
        // 2.0 and uses add(double, double). Widening is allowed when choosing
        // an overload; narrowing never is.
        System.out.println("add(2, 3.5):");
        System.out.println("  -> " + add(2, 3.5));

        System.out.println();
        System.out.println(greet("Ada"));
        System.out.println(greet("Ada", "Good morning"));

        System.out.println();
        System.out.println("You have been using overloads all along:");
        System.out.println(42);       // println(int)
        System.out.println(4.2);      // println(double)
        System.out.println(true);     // println(boolean)
        System.out.println('J');      // println(char)
        System.out.println("text");   // println(String)
        System.out.println("...five different println methods, one name.");
    }
}
