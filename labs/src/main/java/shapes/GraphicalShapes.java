package shapes;


import geometry.Vec2d;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;



public class GraphicalShapes {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Graphical Shapes Demo");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            DrawableCircle circle = new DrawableCircle(new Vec2d(100, 200), 50, Color.blue);
            DrawableRectangle rectangle = new DrawableRectangle(new Vec2d(250, 200), 100, 100, Color.red);
            DrawablePolygon polygon = new DrawablePolygon(new Vec2d(325, 150), new ArrayList<>(List.of(
                    new Vec2d(0, 0),
                    new Vec2d(100, 0),
                    new Vec2d(150, 50),
                    new Vec2d(100, 100),
                    new Vec2d(0, 100)
            )), Color.green);
            List<Drawable> shapes = List.of(circle, rectangle, polygon);

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Drawable shape : shapes) {
                    shape.draw((Graphics2D) g);
                }
            }
        };

        frame.add(panel);
        frame.setVisible(true);
    }
}
