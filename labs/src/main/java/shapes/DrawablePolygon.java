package shapes;

import geometry.Vec2d;

import java.awt.*;
import java.util.ArrayList;

public class DrawablePolygon extends Polygon implements Drawable {
    private Color color;

    public DrawablePolygon(Vec2d p, ArrayList<Vec2d> vertices, Color color) {
        super(p, vertices);
        this.color = color;
    }

    public void draw(Graphics2D g) {
        ArrayList<Vec2d> verts = getVertices();
        int[] xPoints = new int[verts.size()];
        int[] yPoints = new int[verts.size()];
        for (int i = 0; i < verts.size(); i++) {
            xPoints[i] = (int) verts.get(i).x();
            yPoints[i] = (int) verts.get(i).y();
        }
        g.setColor(color);
        g.fillPolygon(xPoints, yPoints, verts.size());
        // g.drawPolygon();
    }

    public ArrayList<Vec2d> getVertices() {
        ArrayList<Vec2d> points = new ArrayList<>();
        for (Vec2d v : vertices) {
            points.add(v.add(position));
        }
        return points;
    }
}

