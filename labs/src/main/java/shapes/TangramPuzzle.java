package shapes;


import geometry.Vec2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TangramPuzzle {

    static Vec2d pointToVec2d(Point p) {
        return new Vec2d(p.getX(), p.getY());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tangram Puzzle Prototype");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            final PuzzleModel model = new PuzzleModel(TangramBetterTiles.getBox(), TangramBetterTiles.getTiles());
            DrawablePolygon currentShape = null;
            Vec2d lastDragPoint = null;
            String statusText = "";

            {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        currentShape = model.getTileAt(pointToVec2d(e.getPoint()));
                        if (currentShape != null) {
                            lastDragPoint = pointToVec2d(e.getPoint()); // Store the initial point
                            // repaint as it may have moved to the front
                            repaint();
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        currentShape = null;
                        lastDragPoint = null;
                    }
                });

                addMouseMotionListener(new MouseAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        if (currentShape != null && lastDragPoint != null) {
                            Vec2d currentPoint = pointToVec2d(e.getPoint());
                            Vec2d delta = currentPoint.subtract(lastDragPoint);
                            // moveTo is absolute, so we need to add the delta to the current position
                            currentShape.moveTo(currentShape.getPosition().add(delta));
                            lastDragPoint = currentPoint; // Update the last drag point
                            repaint(); // Repaint the panel to show the updated shape position
                        }
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                model.box.draw((Graphics2D) g);
                for (DrawablePolygon shape : model.getTiles()) {
                    shape.draw((Graphics2D) g);
                }
                statusText = model.getStatusText();
                g.setColor(Color.black);
                g.drawString(statusText, 50, 700);
            }
        };

        frame.add(panel);
        frame.setVisible(true);
    }
}
