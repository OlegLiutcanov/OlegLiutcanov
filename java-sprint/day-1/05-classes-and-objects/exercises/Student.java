// Student.java
//
// GOAL: build a small class that protects its own data. A Student holds a name
// and a grade, and the class must guarantee two things no matter what any
// caller does:
//     - the grade is always between 0 and 100 (inclusive)
//     - the name is never empty and never null
//
// The test main at the bottom is already written. DO NOT CHANGE IT. Your job
// is everything above it. Run the file first: it compiles as given and prints
// nulls and zeros. Fill in the TODOs until the output matches exactly.
//
// THE SPEC
//   fields    private String name, private int grade
//   Student(String name, int newGrade)
//             builds a valid student. If the name is rejected, use "Unknown".
//             If the grade is rejected, use 0.
//   getName() returns the name
//   getGrade() returns the grade
//   setName(String newName)
//             returns false and changes NOTHING if newName is null or empty;
//             otherwise stores it and returns true
//   setGrade(int newGrade)
//             returns false and changes NOTHING if newGrade is below 0 or
//             above 100; otherwise stores it and returns true
//   isPassing() returns true when the grade is 60 or more
//
// Run it with:  java Student.java
//
// EXPECTED OUTPUT when you are finished:
//
// === Two students ===
//   Ada (grade 91) passing? true
//   Grace (grade 55) passing? false
//
// === The grade setter refuses bad values ===
//   ada.setGrade(105) accepted? false
//   ada.setGrade(-1)  accepted? false
//   Ada's grade is still 91
//   ada.setGrade(100) accepted? true
//   Ada's grade is now 100
//
// === The name setter refuses empty names ===
//   grace.setName("") accepted? false
//   grace.setName("Grace Hopper") accepted? true
//   Grace Hopper (grade 55) passing? false
//
// === A student born with an impossible grade ===
//   new Student("Alan", 500) -> grade 0
//
// === The boundaries count ===
//   setGrade(0)   accepted? true
//   setGrade(100) accepted? true
//   setGrade(101) accepted? false
//   final grade: 100
//
// === Two students, two separate states ===
//   x -> 95, y -> 70

public class Student {

    // TODO 1: declare the two fields here, both private:
    //   a String called name, and an int called grade.
    // Fields go inside the class but outside every method.

    // TODO 2: finish the constructor.
    // Tip: instead of assigning the fields directly, call your own setName and
    // setGrade and check what they return. The validation then lives in one
    // place only, and no Student can ever be born in a state the setters would
    // have refused. If a setter returns false, fall back to "Unknown" or 0.
    Student(String name, int newGrade) {
    }

    // TODO 3: return the name field.
    String getName() {
        return null;
    }

    // TODO 4: return the grade field.
    int getGrade() {
        return 0;
    }

    // TODO 5: reject null or empty, otherwise store and return true.
    // Careful with the order of the two checks - see the hint in LESSON.md.
    boolean setName(String newName) {
        return false;
    }

    // TODO 6: reject anything below 0 or above 100, otherwise store it.
    // Note that 0 and 100 themselves are valid.
    boolean setGrade(int newGrade) {
        return false;
    }

    // TODO 7: true when the grade is 60 or more.
    boolean isPassing() {
        return false;
    }

    // ------------------------------------------------------------------
    // Everything below this line is already written. Leave it alone.
    // ------------------------------------------------------------------

    static void printStudent(Student student) {
        System.out.println("  " + student.getName()
                + " (grade " + student.getGrade() + ") passing? "
                + student.isPassing());
    }

    public static void main(String[] args) {
        System.out.println("=== Two students ===");
        Student ada = new Student("Ada", 91);
        Student grace = new Student("Grace", 55);
        printStudent(ada);
        printStudent(grace);

        System.out.println();
        System.out.println("=== The grade setter refuses bad values ===");
        System.out.println("  ada.setGrade(105) accepted? " + ada.setGrade(105));
        System.out.println("  ada.setGrade(-1)  accepted? " + ada.setGrade(-1));
        System.out.println("  Ada's grade is still " + ada.getGrade());
        System.out.println("  ada.setGrade(100) accepted? " + ada.setGrade(100));
        System.out.println("  Ada's grade is now " + ada.getGrade());

        System.out.println();
        System.out.println("=== The name setter refuses empty names ===");
        System.out.println("  grace.setName(\"\") accepted? " + grace.setName(""));
        System.out.println("  grace.setName(\"Grace Hopper\") accepted? "
                + grace.setName("Grace Hopper"));
        printStudent(grace);

        System.out.println();
        System.out.println("=== A student born with an impossible grade ===");
        Student alan = new Student("Alan", 500);
        System.out.println("  new Student(\"Alan\", 500) -> grade " + alan.getGrade());

        System.out.println();
        System.out.println("=== The boundaries count ===");
        Student edge = new Student("Edge", 50);
        System.out.println("  setGrade(0)   accepted? " + edge.setGrade(0));
        System.out.println("  setGrade(100) accepted? " + edge.setGrade(100));
        System.out.println("  setGrade(101) accepted? " + edge.setGrade(101));
        System.out.println("  final grade: " + edge.getGrade());

        System.out.println();
        System.out.println("=== Two students, two separate states ===");
        Student x = new Student("Xavier", 70);
        Student y = new Student("Yolanda", 70);
        x.setGrade(95);
        System.out.println("  x -> " + x.getGrade() + ", y -> " + y.getGrade());
    }
}
