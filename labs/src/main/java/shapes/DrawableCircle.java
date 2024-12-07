package shapes;

import geometry.Vec2d;

import java.awt.*;

public class DrawableCircle extends Circle implements Drawable {
    private Color color;

    public DrawableCircle(Vec2d p, double radius, Color color) {
        super(p, radius);
        this.color = color;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        int diameter = (int) (2 * radius);
        g.fillOval((int) (position.x() - radius), (int) (position.y() - radius), diameter, diameter);
    }
}
