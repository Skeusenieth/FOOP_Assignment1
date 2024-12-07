package shapes;

import geometry.Vec2d;

public class Circle extends MovableShape {
    double radius;

    public Circle(Vec2d p, double radius) {
        super(p);
        this.radius = radius;
    }

    public double area() {
        return Math.PI * radius * radius;
    }

    public double perimeter() {
        return 2 * Math.PI * radius;
    }
}
