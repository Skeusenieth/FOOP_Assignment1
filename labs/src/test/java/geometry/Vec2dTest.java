package geometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Vec2dTest {

    @Test
    void testDistance() {
        Vec2d v1 = new Vec2d(3, 4);
        Vec2d v2 = new Vec2d(0, 0);
        assertEquals(5.0, v1.distance(v2), 1e-9);
    }

    @Test
    void testMagnitude() {
        Vec2d v = new Vec2d(3, 4);
        assertEquals(5.0, v.magnitude(), 1e-9);
    }

    @Test
    void testAdd() {
        Vec2d v1 = new Vec2d(1, 2);
        Vec2d v2 = new Vec2d(3, 4);
        Vec2d result = v1.add(v2);
        assertEquals(new Vec2d(4, 6), result);
    }

    @Test
    void testSubtract() {
        Vec2d v1 = new Vec2d(5, 7);
        Vec2d v2 = new Vec2d(2, 3);
        Vec2d result = v1.subtract(v2);
        assertEquals(new Vec2d(3, 4), result);
    }

    @Test
    void testMultiply() {
        Vec2d v = new Vec2d(2, 3);
        Vec2d result = v.multiply(2);
        assertEquals(new Vec2d(4, 6), result);
    }

    @Test
    void testDotProduct() {
        Vec2d v1 = new Vec2d(1, 2);
        Vec2d v2 = new Vec2d(3, 4);
        double result = v1.dot(v2);
        assertEquals(11.0, result, 1e-9);
    }

    @Test
    void testPerpendicular() {
        Vec2d v = new Vec2d(1, 2);
        Vec2d result = v.perpendicular();
        assertEquals(new Vec2d(-2, 1), result);
    }

    @Test
    void testAngleRadians() {
        Vec2d v = new Vec2d(1, 0);
        assertEquals(0.0, v.angleRadians(), 1e-9);

        Vec2d v2 = new Vec2d(0, 1);
        assertEquals(Math.PI / 2, v2.angleRadians(), 1e-9);

        Vec2d v3 = new Vec2d(-1, 0);
        assertEquals(Math.PI, v3.angleRadians(), 1e-9);

        Vec2d v4 = new Vec2d(0, -1);
        assertEquals(-Math.PI / 2, v4.angleRadians(), 1e-9);
    }
}
