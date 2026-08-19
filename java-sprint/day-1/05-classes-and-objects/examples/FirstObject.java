// FirstObject.java
// The smallest possible class-and-object program.
// A class is a blueprint. Each 'new' builds one object from that blueprint,
// and every object carries its own copy of the fields.
// Run it with:  java FirstObject.java

public class FirstObject {

    public static void main(String[] args) {
        // 'new Dog()' does three things:
        //   1. sets aside memory for one Dog,
        //   2. fills its fields with default values,
        //   3. hands back a REFERENCE to it.
        // The variable 'rex' stores that reference.
        Dog rex = new Dog();

        // Fields you have not set yet are not garbage. Java gives every field a
        // default: 0 for whole numbers, 0.0 for decimals, false for boolean,
        // and null for anything that holds an object - String included.
        System.out.println("A brand-new Dog, before we touch it:");
        System.out.println("  name     = " + rex.name);
        System.out.println("  ageYears = " + rex.ageYears);
        System.out.println("  goodBoy  = " + rex.goodBoy);

        // The dot means "reach into this object and get the field called...".
        rex.name = "Rex";
        rex.ageYears = 4;
        rex.goodBoy = true;

        System.out.println();
        System.out.println("After filling it in:");
        describe(rex);

        // A second 'new' builds a SECOND, completely separate object.
        Dog nala = new Dog();
        nala.name = "Nala";
        nala.ageYears = 2;
        nala.goodBoy = true;

        System.out.println();
        System.out.println("A second Dog, with its own fields:");
        describe(nala);

        // Proof that they are independent: change one, the other is untouched.
        rex.ageYears = 5;
        System.out.println();
        System.out.println("After rex has a birthday:");
        System.out.println("  rex.ageYears  = " + rex.ageYears);
        System.out.println("  nala.ageYears = " + nala.ageYears + "   (unchanged)");
    }

    // Dog is now a TYPE, so it can be a parameter type just like int or String.
    static void describe(Dog dog) {
        String line = "  " + dog.name + ", aged " + dog.ageYears;
        if (dog.goodBoy) {
            line = line + ", good boy";
        }
        System.out.println(line);
    }
}

// A second class in the same file. Only ONE class per file may be public, and
// a public class's name must match the filename - so this one is written
// without the word 'public'. It is still perfectly usable from above.
class Dog {
    // These are FIELDS, also called instance variables: one full set per object.
    // They are declared inside the class but outside every method.
    String name;
    int ageYears;
    boolean goodBoy;
}
