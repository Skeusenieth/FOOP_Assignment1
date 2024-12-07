package geometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;

class PolyGeometryTest {

    @Test
    void testRangeOverlap() {
        PolyGeometry.Range range1 = new PolyGeometry.Range(0, 5);
        PolyGeometry.Range range2 = new PolyGeometry.Range(3, 8);
        PolyGeometry.Range range3 = new PolyGeometry.Range(6, 10);

        assertTrue(range1.overlaps(range2));
        assertFalse(range1.overlaps(range3));
    }

    @Test
    void testProjectionRange() {
        ArrayList<Vec2d> polygon = new ArrayList<>(Arrays.asList(
                new Vec2d(1, 2), new Vec2d(3, 4), new Vec2d(5, 0)
        ));
        Vec2d axis = new Vec2d(1, 1);
        PolyGeometry.Range range = PolyGeometry.projectionRange(polygon, axis);

        // Expected projections
        double minProjection = axis.dot(new Vec2d(1, 2)); // 3.0
        double maxProjection = axis.dot(new Vec2d(3, 4)); // 7.0

        assertEquals(minProjection, range.min, 1e-9);
        assertEquals(maxProjection, range.max, 1e-9);
    }

    @Test
    void testPolygonsOverlap() {
        ArrayList<Vec2d> square1 = new ArrayList<>(Arrays.asList(
                new Vec2d(0, 0), new Vec2d(0, 2), new Vec2d(2, 2), new Vec2d(2, 0)
        ));
        ArrayList<Vec2d> square2 = new ArrayList<>(Arrays.asList(
                new Vec2d(1, 1), new Vec2d(1, 3), new Vec2d(3, 3), new Vec2d(3, 1)
        ));
        ArrayList<Vec2d> square3 = new ArrayList<>(Arrays.asList(
                new Vec2d(3, 3), new Vec2d(3, 5), new Vec2d(5, 5), new Vec2d(5, 3)
        ));

        assertTrue(PolyGeometry.polygonsOverlap(square1, square2));
        assertFalse(PolyGeometry.polygonsOverlap(square1, square3));
    }

    @Test
    void testContainsPoint() {
        ArrayList<Vec2d> polygon = new ArrayList<>(Arrays.asList(
                new Vec2d(0, 0), new Vec2d(0, 5), new Vec2d(5, 5), new Vec2d(5, 0)
        ));
        Vec2d pointInside = new Vec2d(2, 2);
        Vec2d pointOutside = new Vec2d(6, 6);

        assertTrue(PolyGeometry.contains(polygon, pointInside));
        assertFalse(PolyGeometry.contains(polygon, pointOutside));
    }

    @Test
    void testContainsPolygon() {
        ArrayList<Vec2d> outerPolygon = new ArrayList<>(Arrays.asList(
                new Vec2d(0, 0), new Vec2d(0, 10), new Vec2d(10, 10), new Vec2d(10, 0)
        ));
        ArrayList<Vec2d> innerPolygon = new ArrayList<>(Arrays.asList(
                new Vec2d(2, 2), new Vec2d(2, 8), new Vec2d(8, 8), new Vec2d(8, 2)
        ));
        ArrayList<Vec2d> overlappingPolygon = new ArrayList<>(Arrays.asList(
                new Vec2d(5, 5), new Vec2d(5, 12), new Vec2d(12, 12), new Vec2d(12, 5)
        ));

        assertTrue(PolyGeometry.contains(outerPolygon, innerPolygon));
        assertFalse(PolyGeometry.contains(outerPolygon, overlappingPolygon));
    }
}
