// ScopeAndPassByValue.java
// Two rules about what a method can and cannot see or touch:
//   1. SCOPE       - a variable lives inside the braces it was declared in.
//   2. PASS BY VALUE - a method receives a COPY of what you pass it.
// Run it with:  java ScopeAndPassByValue.java

public class ScopeAndPassByValue {

    // Declared outside every method, so every method in this class can see it.
    // A variable in this position is called a FIELD.
    static int callsMade = 0;

    // ---------------------------------------------------------------- scope

    static void scopeTour() {
        int outer = 1;                 // lives until scopeTour ends

        if (outer == 1) {
            int insideIf = 2;          // lives until this if-block ends
            System.out.println("  inside the if : outer=" + outer + " insideIf=" + insideIf);
        }
        // System.out.println(insideIf);
        //   error: cannot find symbol - insideIf died with its closing brace

        for (int i = 0; i < 3; i++) {  // i lives only inside the loop
            int doubled = i * 2;
            System.out.println("  inside the loop: i=" + i + " doubled=" + doubled);
        }
        // System.out.println(i);
        //   error: cannot find symbol - same story

        System.out.println("  after both    : outer=" + outer + " (still here)");
    }

    static void shadowingTour() {
        int callsMade = 99;   // a LOCAL variable with the same name as the field
        System.out.println("  local callsMade : " + callsMade);
        System.out.println("  the field       : " + ScopeAndPassByValue.callsMade);
        // The nearest declaration wins. This is called shadowing, and it is
        // usually an accident - one good reason to pick distinct names.
    }

    // -------------------------------------------------------- pass by value

    static void tryToChangeNumber(int number) {
        number = number + 100;
        System.out.println("  inside the method, number = " + number);
    }

    static void tryToChangeText(String text) {
        text = text + " (edited)";
        System.out.println("  inside the method, text   = " + text);
    }

    // An array is different, and it is worth seeing now so it does not surprise
    // you later. The COPY handed over is a copy of the arrow pointing at the
    // array, not a copy of the array itself - so both arrows lead to the same
    // boxes, and edits through either one are visible from both.
    static void changeFirstSlot(int[] values) {
        values[0] = 999;
    }

    // Reassigning the parameter itself, though, only moves the local arrow.
    static void tryToReplaceArray(int[] values) {
        values = new int[] { -1, -1, -1 };
        System.out.println("  inside the method, values[0] = " + values[0]);
    }

    static int registerCall() {
        callsMade = callsMade + 1;   // a field CAN be changed from any method
        return callsMade;
    }

    public static void main(String[] args) {
        System.out.println("SCOPE");
        scopeTour();

        System.out.println();
        System.out.println("SHADOWING");
        shadowingTour();

        System.out.println();
        System.out.println("PASS BY VALUE - a primitive");
        int score = 10;
        System.out.println("  before the call,  score = " + score);
        tryToChangeNumber(score);
        System.out.println("  after the call,   score = " + score + "  <- unchanged");

        System.out.println();
        System.out.println("PASS BY VALUE - a String");
        String name = "Ada";
        System.out.println("  before the call,  name  = " + name);
        tryToChangeText(name);
        System.out.println("  after the call,   name  = " + name + "  <- unchanged");

        System.out.println();
        System.out.println("An array is the exception worth knowing");
        int[] numbers = { 1, 2, 3 };
        System.out.println("  before changeFirstSlot,   numbers[0] = " + numbers[0]);
        changeFirstSlot(numbers);
        System.out.println("  after  changeFirstSlot,   numbers[0] = " + numbers[0] + "  <- CHANGED");
        tryToReplaceArray(numbers);
        System.out.println("  after  tryToReplaceArray, numbers[0] = " + numbers[0] + "  <- unchanged");

        System.out.println();
        System.out.println("If a method must report something back, return it");
        int updated = tryToChangeNumberProperly(score);
        System.out.println("  score   = " + score + " (still untouched)");
        System.out.println("  updated = " + updated + " (the returned value)");

        System.out.println();
        System.out.println("Fields are shared across methods, so they do change");
        registerCall();
        registerCall();
        System.out.println("  callsMade = " + callsMade);
    }

    // The fix for "my method could not change the caller's variable": hand the
    // new value back and let the caller decide what to do with it.
    static int tryToChangeNumberProperly(int number) {
        return number + 100;
    }
}
