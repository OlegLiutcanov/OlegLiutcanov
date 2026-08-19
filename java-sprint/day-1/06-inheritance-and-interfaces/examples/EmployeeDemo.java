// Run me with:  java EmployeeDemo.java
//
// Shows: extends, inherited fields and methods, super(...) in a constructor,
// @Override, protected, and the fact that a Manager IS-A Employee.

public class EmployeeDemo {
    public static void main(String[] args) {
        Employee ada = new Employee("Ada", 50000);
        Manager grace = new Manager("Grace", 90000, 4);

        // describe() is written once, in Employee. Manager inherits it for free.
        System.out.println(ada.describe());
        System.out.println(grace.describe());

        System.out.println();
        System.out.println("Ada's yearly pay:   " + ada.yearlyPay());
        System.out.println("Grace's yearly pay: " + grace.yearlyPay());
        System.out.println("Grace leads " + grace.getTeamSize() + " people.");

        System.out.println();
        System.out.println("Is Grace an Employee? " + (grace instanceof Employee));
        System.out.println("Is Ada a Manager?     " + (ada instanceof Manager));
    }
}

class Employee {
    // protected = visible to this class AND its subclasses, but not to unrelated code.
    protected final String name;

    // private = only this class can touch it, not even Manager.
    private final int baseSalary;

    Employee(String name, int baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }

    int getBaseSalary() {
        return baseSalary;
    }

    int yearlyPay() {
        return baseSalary;
    }

    // Notice: describe() calls yearlyPay(). When a Manager runs this inherited
    // method, Java picks Manager's yearlyPay() - not Employee's. That is the
    // whole point of overriding.
    String describe() {
        return name + " takes home " + yearlyPay() + " a year.";
    }
}

class Manager extends Employee {
    private final int teamSize;

    Manager(String name, int baseSalary, int teamSize) {
        // super(...) builds the Employee part of this object first.
        // It must be the very first statement in the constructor.
        super(name, baseSalary);
        this.teamSize = teamSize;
    }

    int getTeamSize() {
        return teamSize;
    }

    @Override
    int yearlyPay() {
        // getBaseSalary() because baseSalary itself is private to Employee.
        // "name" below works because it is protected, not private.
        return getBaseSalary() + 1000 * teamSize;
    }

    @Override
    String describe() {
        // super.describe() runs the Employee version, then we add to it.
        return super.describe() + " (manages " + teamSize + ")";
    }
}
