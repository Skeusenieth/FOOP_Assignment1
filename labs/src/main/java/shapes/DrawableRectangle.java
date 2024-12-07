package shapes;

import geometry.Vec2d;

import java.awt.*;

public class DrawableRectangle extends Rectangle implements Drawable {
    final private Color color;

    public DrawableRectangle(Vec2d p, double width, double height, Color color) {
        super(p, width, height);
        this.color = color;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        int x = (int) (position.x() - width / 2);
        int y = (int) (position.y() - height / 2);
        g.fillRect(x, y, (int) width, (int) height);
    }
}
