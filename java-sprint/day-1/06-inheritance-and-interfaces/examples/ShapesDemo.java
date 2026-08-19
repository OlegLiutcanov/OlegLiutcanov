// Run me with:  java ShapesDemo.java
//
// Shows: an abstract class you cannot instantiate, abstract methods every
// subclass must fill in, and polymorphism - one loop, one variable type,
// three different shapes each doing their own area() maths.

public class ShapesDemo {
    public static void main(String[] args) {
        // The array type is Shape. What is actually stored are Circles and
        // Rectangles. The loop below does not know or care which is which.
        Shape[] shapes = {
                new Circle(1.0),
                new Rectangle(3.0, 4.0),
                new Circle(2.5),
                new Rectangle(2.0, 2.0)
        };

        double total = 0.0;
        for (Shape shape : shapes) {
            System.out.println(shape.describe());
            total += shape.area();
        }

        System.out.printf("Total area of %d shapes: %.2f%n", shapes.length, total);

        // Shape blob = new Shape();
        // ^ uncomment that line and the compiler says:
        //   error: Shape is abstract; cannot be instantiated
    }
}

// abstract = this class is a half-finished idea. It exists to be extended.
// "Every shape has an area" is true; "here is how to compute it" is not
// answerable until you know which shape, so area() is left abstract.
abstract class Shape {
    abstract double area();

    abstract String name();

    // A normal method. Every subclass inherits it, and it leans on the
    // abstract methods above without knowing how they are implemented.
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
