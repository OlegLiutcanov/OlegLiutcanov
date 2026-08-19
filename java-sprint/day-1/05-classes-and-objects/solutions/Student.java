// Student.java - SOLUTION
//
// Run it with:  java Student.java

public class Student {

    // Private, so the only way in is through the methods below. That is what
    // lets this class promise that a grade is always between 0 and 100.
    private String name;
    private int grade;

    Student(String name, int newGrade) {
        // Reuse the setters rather than assigning the fields directly: the
        // validation then lives in exactly one place, and an object can never
        // be born in a state the setters would have rejected.
        if (!setName(name)) {
            this.name = "Unknown";
        }
        if (!setGrade(newGrade)) {
            this.grade = 0;
        }
    }

    String getName() {
        return name;
    }

    int getGrade() {
        return grade;
    }

    boolean setName(String newName) {
        if (newName == null || newName.isEmpty()) {
            return false;
        }
        name = newName;
        return true;
    }

    boolean setGrade(int newGrade) {
        if (newGrade < 0 || newGrade > 100) {
            return false;
        }
        grade = newGrade;
        return true;
    }

    boolean isPassing() {
        return grade >= 60;
    }

    // ---------------------------------------------------------------- test

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
