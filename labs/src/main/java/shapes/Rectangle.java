package shapes;

import geometry.Vec2d;

public class Rectangle extends MovableShape {
    double width;
    double height;

    public Rectangle(Vec2d p, double width, double height) {
        super(p);
        this.width = width;
        this.height = height;
    }

    public double area() {
        return width * height;
    }

    public double perimeter() {
        return 2 * (width + height);
    }
}

