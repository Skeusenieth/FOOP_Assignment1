package shapes;


import geometry.Vec2d;

import java.util.ArrayList;

public class Polygon extends MovableShape {
    ArrayList<Vec2d> vertices;

    public Polygon(Vec2d p, ArrayList<Vec2d> vertices) {
        super(p);
        this.vertices = vertices;
    }

    public double area() {
        double area = 0;
        int n = vertices.size();
        for (int i = 0; i < n; i++) {
            Vec2d v1 = vertices.get(i);
            Vec2d v2 = vertices.get((i + 1) % n);
            area += v1.x() * v2.y() - v2.x() * v1.y();
        }
        return Math.abs(area) / 2.0;
    }

    public double perimeter() {
        double perimeter = 0;
        int n = vertices.size();
        for (int i = 0; i < n; i++) {
            Vec2d v1 = vertices.get(i);
            Vec2d v2 = vertices.get((i + 1) % n);
            perimeter += v1.distance(v2);
        }
        return perimeter;
    }
}
