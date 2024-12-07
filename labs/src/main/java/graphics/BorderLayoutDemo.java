
package graphics;

import javax.swing.*;
import java.awt.*;

public class BorderLayoutDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("BorderLayout Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Adding components to different regions
        frame.add(new ColorComponent(Color.RED, "North"), BorderLayout.NORTH);
        frame.add(new ColorComponent(Color.BLUE, "South"), BorderLayout.SOUTH);
        frame.add(new ColorComponent(Color.GREEN, "East"), BorderLayout.EAST);
        frame.add(new ColorComponent(Color.YELLOW, "West"), BorderLayout.WEST);
        frame.add(new ColorComponent(Color.CYAN, "Center"), BorderLayout.CENTER);

        frame.setSize(500, 400); // Set window size
        frame.setVisible(true);
    }

    // Inner class for colored components
    static class ColorComponent extends JComponent {
        private final Color color;
        private final String label;

        public ColorComponent(Color color, String label) {
            this.color = color;
            this.label = label;
            setPreferredSize(new Dimension(100, 50)); // Default size for border regions
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(color);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.BLACK);
            g.drawString(label, 10, 20); // Draw label
        }
    }
}
