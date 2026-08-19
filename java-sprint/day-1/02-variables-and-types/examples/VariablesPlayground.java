// VariablesPlayground.java
// A tour of the variable types you will actually use as a beginner.
// Run it with:  java VariablesPlayground.java

public class VariablesPlayground {

    // A constant: declared once, never allowed to change.
    // Constants live in SCREAMING_SNAKE_CASE by convention.
    static final int DAYS_IN_WEEK = 7;

    public static void main(String[] args) {

        // ---- Whole numbers ----
        int studentCount = 24;             // the everyday whole number
        long worldPopulation = 8_100_000_000L;  // too big for int, so long + the L suffix
        // The underscores are just visual separators. Java ignores them.

        // ---- Numbers with a decimal point ----
        double averageGrade = 87.5;        // the everyday decimal number

        // ---- True or false ----
        boolean coursePassed = true;

        // ---- A single character, in SINGLE quotes ----
        char letterGrade = 'B';

        // ---- Text, in DOUBLE quotes. Note the capital S. ----
        String courseName = "Intro to Java";

        System.out.println("Course:        " + courseName);
        System.out.println("Students:      " + studentCount);
        System.out.println("World pop:     " + worldPopulation);
        System.out.println("Average grade: " + averageGrade);
        System.out.println("Letter grade:  " + letterGrade);
        System.out.println("Passed?        " + coursePassed);
        System.out.println("Days in week:  " + DAYS_IN_WEEK);

        System.out.println();

        // ---- Declaring and initializing are two separate steps ----
        int seatsLeft;                     // declared: the box exists, but it is empty
        seatsLeft = 30 - studentCount;     // initialized: now the box holds a value
        System.out.println("Seats left:    " + seatsLeft);

        // ---- Variables vary: you can assign a new value any time ----
        studentCount = studentCount + 1;   // a new student enrolled
        System.out.println("After enrol:   " + studentCount);

        // ---- var: Java works the type out from the right-hand side ----
        var instructor = "Ada";            // this is a String, permanently
        var roomNumber = 214;              // this is an int, permanently
        System.out.println("Instructor:    " + instructor + " in room " + roomNumber);

        // ---- Constants cannot be reassigned ----
        // Uncomment the next line and the program refuses to compile:
        // DAYS_IN_WEEK = 8;
        System.out.println("A week still has " + DAYS_IN_WEEK + " days. Always.");
    }
}
