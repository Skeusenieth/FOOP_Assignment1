package shapes;

import geometry.Vec2d;

public abstract class MovableShape implements Shape {
    Vec2d position;

    public MovableShape(Vec2d p) {
        this.position = p;
    }

    void moveTo(Vec2d position) {
        this.position = position;
    }

    Vec2d getPosition() {
        return position;
    }
}
