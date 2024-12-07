package geometry;


public record Vec2d(double x, double y) {
    public double distance(Vec2d other) {
        return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Vec2d add(Vec2d other) {
        return new Vec2d(x + other.x, y + other.y);
    }

    public Vec2d subtract(Vec2d other) {
        return new Vec2d(x - other.x, y - other.y);
    }

    public Vec2d multiply(double scalar) {
        return new Vec2d(x * scalar, y * scalar);
    }

    public double dot(Vec2d other) {
        return x * other.x + y * other.y;
    }

    public Vec2d perpendicular() {
        return new Vec2d(-y, x);
    }

    public double angleRadians() {
        return Math.atan2(y, x);
    }
}
