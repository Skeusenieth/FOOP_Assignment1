package shapes;

import geometry.PolyGeometry;
import geometry.Vec2d;

import java.awt.*;
import java.util.ArrayList;

public class Tile extends DrawablePolygon {

    public Tile(Vec2d p, ArrayList<Vec2d> vertices, Color color) {
        super(p, vertices, color);
    }

    // use this to help select a tile
    public boolean contains(Vec2d point) {
        return PolyGeometry.contains(this.vertices, point);
    }

    // use this to check if a tile is inside the box
    public boolean contains(Tile other) {
        return PolyGeometry.contains(this.vertices, other.vertices);
    }

    // use this to check if a tile overlaps another tile
    public boolean intersects(Tile other) {
        return PolyGeometry.polygonsOverlap(this.vertices, other.vertices);
    }
}