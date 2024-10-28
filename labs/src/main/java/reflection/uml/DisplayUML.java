package reflection.uml;

/**
 *
 * This class will lay out a class diagram based on its inheritance relationships.
 * It will be based on the UML diagram layout from the
 * Map<String, ClassLayout> calculateLayout(DiagramData diagram) method.
 *
 * Hence all the work we do here is purely in rendering the class diagram,
 * in fact to begin with we will just render the class boxes, not even the lines connecting them.
 */

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import reflection.uml.UMLLayout.ClassLayout;

public class DisplayUML extends JPanel {

    private final Map<String, ClassLayout> layout; // This holds the layout of the class boxes

    // Constructor that takes the layout map
    public DisplayUML(Map<String, ClassLayout> layout) {
        this.layout = layout;
        setPreferredSize(new Dimension(800, 600)); // Set the preferred size for the component
    }

    // Override the paintComponent method to draw the class boxes
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set a font for the class names
        g2d.setFont(new Font("Arial", Font.BOLD, 14));

        // Loop through the layout map and draw each class box
        for (Map.Entry<String, ClassLayout> entry : layout.entrySet()) {
            String className = entry.getKey();
            ClassLayout cl = entry.getValue();

            // Get the position and size of the class box
            double x = cl.centerX() - cl.width() / 2;
            double y = cl.centerY() - cl.height() / 2;
            double width = cl.width();
            double height = cl.height();

            // Draw the class box (rectangle)
            g2d.drawRect((int) x, (int) y, (int) width, (int) height);

            // Draw the class name centered in the box
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(className);
            int textX = (int) (x + (width - textWidth) / 2);
            int textY = (int) (y + fm.getAscent());
            g2d.drawString(className, textX, textY);
        }
    }

    // Main method to set up a simple frame and display the panel
    public static void main(String[] args) {
        // Mock-up example layout to display
        Map<String, ClassLayout> exampleLayout = Map.of(
                "MyShape", new ClassLayout(200, 100, 150, 60),
                "MyCircle", new ClassLayout(200, 200, 150, 120),
                "MyEllipse", new ClassLayout(400, 200, 150, 90),
                "Connector", new ClassLayout(300, 300, 150, 60)
        );

        // Create the panel to display the UML diagram
        DisplayUML display = new DisplayUML(exampleLayout);

        // Create a frame to hold the panel
        JFrame frame = new JFrame("UML Class Diagram");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(display);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }
}
