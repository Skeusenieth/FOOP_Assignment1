package geometry;

import java.util.ArrayList;

public class PolyGeometry {
    // helper class for polygon overlap detection
    static class Range {
        double min;
        double max;

        public Range(double min, double max) {
            this.min = min;
            this.max = max;
        }

        public boolean overlaps(Range other) {
            return !(this.max < other.min || other.max < this.min);
        }
    }

    static Range projectionRange(ArrayList<Vec2d> polygon, Vec2d axis) {
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for (Vec2d p : polygon) {
            double projection = axis.dot(p);
            min = Math.min(projection, min);
            max = Math.max(projection, max);
        }
        return new Range(min, max);
    }

    private static boolean oneWayOverlapCheck(ArrayList<Vec2d> polygonA, ArrayList<Vec2d> polygonB) {
        for (int i = 0; i < polygonA.size(); i++) {
            Vec2d edge = polygonA.get(i).subtract(polygonA.get((i + 1) % polygonA.size()));
            Vec2d axis = edge.perpendicular();

            Range rangeA = projectionRange(polygonA, axis);
            Range rangeB = projectionRange(polygonB, axis);

            if (!rangeA.overlaps(rangeB)) {
                return false;
            }
        }
        return true;
    }

    public static boolean polygonsOverlap(ArrayList<Vec2d> polygonA, ArrayList<Vec2d> polygonB) {
        return oneWayOverlapCheck(polygonA, polygonB) && oneWayOverlapCheck(polygonB, polygonA);
    }

    public static boolean contains(ArrayList<Vec2d> polygon, Vec2d point) {
        // implementation of the ray casting algorithm
        boolean inside = false;
        int n = polygon.size();
        for (int i = 0, j = n - 1; i < n; j = i++) {
            Vec2d pi = polygon.get(i), pj = polygon.get(j);

            if (((pi.y() > point.y()) != (pj.y() > point.y())) &&
                    (point.x() < (pj.x() - pi.x()) * (point.y() - pi.y()) / (pj.y() - pi.y()) + pi.x())) {
                inside = !inside;
            }
        }
        return inside;
    }

    public static boolean contains(ArrayList<Vec2d> polygon, ArrayList<Vec2d> other) {
        for (Vec2d p : other) {
            if (!contains(polygon, p)) {
                return false;
            }
        }
        return true;
    }
}

