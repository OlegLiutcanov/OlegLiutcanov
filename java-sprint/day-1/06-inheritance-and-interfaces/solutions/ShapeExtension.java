// EXERCISE 2 - SOLUTION
//
// Note what did NOT change: Shape, Circle, Rectangle and main are untouched.

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

class Triangle extends Shape {
    private final double base;
    private final double height;

    Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    double area() {
        return 0.5 * base * height;
    }

    @Override
    String name() {
        return "Triangle";
    }
}
