package reflection.uml;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import reflection.uml.ReflectionData.DiagramData;
import reflection.uml.UMLLayout.ClassLayout;

public class UMLDiagramViewer {

    public static void main(String[] args) {
        // Step 1: Generate DiagramData from classes using ProcessClasses
        ProcessClasses processor = new ProcessClasses();
        List<Class<?>> classesToProcess = List.of(
                MyShape.class,
                MyCircle.class,
                MyEllipse.class,
                Connector.class
        );

        // Run ProcessClasses to produce DiagramData
        DiagramData diagramData = processor.process(classesToProcess);

        // Step 2: Run the layout algorithm (UMLLayout) to get positioning of each class
        UMLLayout layoutAlgorithm = new UMLLayout();
        Map<String, ClassLayout> layout = layoutAlgorithm.calculateLayout(diagramData);

        // Step 3: Display the UML diagram in a JFrame using DisplayUML
        // DisplayUML displayPanel = new DisplayUML(layout, layoutAlgorithm);
        JFrame frame = new JFrame("UML Class Diagram Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.add(displayPanel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }
}