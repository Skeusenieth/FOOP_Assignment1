
package graphics;

import javax.swing.*;
import java.awt.*;

public class GreenComponent extends JComponent {
    // Constructor to set default size and color
    public GreenComponent() {
        setPreferredSize(new Dimension(400, 200)); // Default size
        setBackground(Color.GREEN); // Background color
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    // Main method to display the component
    public static void main(String[] args) {
        JFrame frame = new JFrame("Green Component");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new GreenComponent());
        frame.pack();
        frame.setVisible(true);
    }
}
