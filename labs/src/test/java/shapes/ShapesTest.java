package shapes;
import geometry.Vec2d;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ShapesTest {
    // test area and perimeter of Cicrle, Rectangle and Polygon

    @Test
    public void testCircle() {
        Circle c = new Circle(new Vec2d(0, 0), 1);
        assertEquals(Math.PI, c.area(), 0.0001);
        assertEquals(2 * Math.PI, c.perimeter(), 0.0001);
    }

    @Test
    public void testRectangle() {
        Rectangle r = new Rectangle(new Vec2d(0, 0), 1, 1);
        assertEquals(1, r.area(), 0.0001);
        assertEquals(4, r.perimeter(), 0.0001);
    }

    @Test
    public void testPolygon() {
        ArrayList<Vec2d> vertices = new ArrayList<>();
        vertices.add(new Vec2d(0, 0));
        vertices.add(new Vec2d(0, 1));
        vertices.add(new Vec2d(1, 1));
        vertices.add(new Vec2d(1, 0));

        Polygon p = new Polygon(new Vec2d(0, 0), vertices);
        assertEquals(1, p.area(), 0.0001);
        assertEquals(4, p.perimeter(), 0.0001);
    }
}
