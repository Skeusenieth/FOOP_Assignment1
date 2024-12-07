
package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ScribbleApp extends JFrame {
    private ScribblePanel scribblePanel;

    public ScribbleApp() {
        super("Scribble App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        scribblePanel = new ScribblePanel();
        add(scribblePanel, BorderLayout.CENTER);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> scribblePanel.clear());
        add(resetButton, BorderLayout.SOUTH);

        setSize(500, 400);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ScribbleApp::new);
    }
}

class ScribblePanel extends JComponent {
    private final ArrayList<Point> points = new ArrayList<>();

    public ScribblePanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                points.add(e.getPoint());
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                points.add(e.getPoint());
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    public void clear() {
        points.clear();
        repaint();
    }
}
