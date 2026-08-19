// EXERCISE 2 - Extend the shape hierarchy without touching what already works
//
// Run me with:  java ShapeExtension.java
//
// The point of this exercise: adding a new kind of shape should require ZERO
// edits to Shape, Circle, Rectangle, or main. That is the promise inheritance
// makes, and here you get to collect on it.
//
// EXPECTED OUTPUT when you are done:
//
// Circle     area =   3.14
// Rectangle  area =  12.00
// Triangle   area =  15.00
// Total area: 30.14
//
// Only edit the Triangle class at the bottom.

public class ShapeExtension {
    public static void main(String[] args) {
        Shape[] shapes = {
                new Circle(1.0),
                new Rectangle(3.0, 4.0),
                new Triangle(6.0, 5.0)
        };

        double total = 0.0;
        for (Shape shape : shapes) {
            System.out.println(shape.describe());
            total += shape.area();
        }
        System.out.printf("Total area: %.2f%n", total);
    }
}

// ---------- DO NOT EDIT ANYTHING BETWEEN HERE ----------

abstract class Shape {
    abstract double area();

    abstract String name();

    String describe() {
        return String.format("%-10s area = %6.2f", name(), area());
    }
}

class Circle extends Shape {
    private final double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }

    @Override
    String name() {
        return "Circle";
    }
}

class Rectangle extends Shape {
    private final double width;
    private final double height;

    Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    double area() {
        return width * height;
    }

    @Override
    String name() {
        return "Rectangle";
    }
}

// ---------- AND HERE. Your work starts below. ----------

class Triangle extends Shape {

    // TODO 1: give Triangle two private final double fields, base and height,
    //         and store the constructor arguments in them.
    Triangle(double base, double height) {
    }

    // TODO 2: return the triangle's area. It is half the base times the height.
    //         Careful: 1 / 2 is 0 in Java because both sides are whole numbers.
    //         Write 0.5 * base * height, or (base * height) / 2.0.
    @Override
    double area() {
        return 0;
    }

    // TODO 3: return "Triangle".
    @Override
    String name() {
        return "???";
    }
}
