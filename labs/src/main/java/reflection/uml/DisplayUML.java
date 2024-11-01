package reflection.uml;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;
import reflection.uml.ReflectionData.*;
import reflection.uml.UMLLayout.ClassLayout;

public class DisplayUML extends JPanel {

    private final Map<String, ClassLayout> layout;
    private final Map<String, List<String>> inheritanceRelations;
    private final Map<String, List<String>> dependencyRelations;
    private final Map<String, List<String>> fields;
    private final Map<String, List<String>> methods;

    public DisplayUML(Map<String, ClassLayout> layout,
                      Map<String, List<String>> inheritanceRelations,
                      Map<String, List<String>> dependencyRelations,
                      Map<String, List<String>> fields,
                      Map<String, List<String>> methods) {
        this.layout = layout;
        this.inheritanceRelations = inheritanceRelations;
        this.dependencyRelations = dependencyRelations;
        this.fields = fields;
        this.methods = methods;
        setPreferredSize(new Dimension(1200, 1000));  // Larger display area for good spacing
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawInheritanceLines(g2d);
        drawDependencyLines(g2d);

        g2d.setFont(new Font("Arial", Font.PLAIN, 12));

        for (Map.Entry<String, ClassLayout> entry : layout.entrySet()) {
            String className = entry.getKey();
            ClassLayout cl = entry.getValue();

            int x = (int) (cl.centerX() - cl.width() / 2);
            int y = (int) (cl.centerY() - cl.height() / 2);
            int width = (int) cl.width();
            int height = (int) cl.height();

            // Set color for class box background
            g2d.setColor(new Color(200, 220, 240));
            g2d.fillRect(x, y, width, height);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, width, height);

            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(className);
            g2d.drawString(className, x + (width - textWidth) / 2, y + fm.getAscent() + 5);

            // Draw fields section
            int fieldStartY = y + fm.getAscent() + 20;
            g2d.drawString("Fields:", x + 5, fieldStartY);
            int fieldY = fieldStartY + fm.getHeight();
            for (String field : fields.getOrDefault(className, List.of())) {
                g2d.drawString(field, x + 10, fieldY);
                fieldY += fm.getHeight();
            }

            // Draw methods section
            int methodStartY = fieldY + 10;
            g2d.drawString("Methods:", x + 5, methodStartY);
            int methodY = methodStartY + fm.getHeight();
            for (String method : methods.getOrDefault(className, List.of())) {
                g2d.drawString(method, x + 10, methodY);
                methodY += fm.getHeight();
            }
        }
    }

    private void drawInheritanceLines(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.setColor(new Color(50, 50, 200)); // Solid blue lines for inheritance
        for (Map.Entry<String, List<String>> entry : inheritanceRelations.entrySet()) {
            String subclass = entry.getKey();
            ClassLayout subclassLayout = layout.get(subclass);
            int x1 = (int) subclassLayout.centerX();
            int y1 = (int) subclassLayout.centerY();

            for (String superclass : entry.getValue()) {
                ClassLayout superclassLayout = layout.get(superclass);
                if (superclassLayout != null) {
                    int x2 = (int) superclassLayout.centerX();
                    int y2 = (int) superclassLayout.centerY();
                    g2d.drawLine(x1, y1, x2, y2);
                }
            }
        }
    }

    private void drawDependencyLines(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{8}, 0));
        g2d.setColor(new Color(150, 150, 150)); // Gray dashed lines for dependencies
        for (Map.Entry<String, List<String>> entry : dependencyRelations.entrySet()) {
            String dependentClass = entry.getKey();
            ClassLayout dependentLayout = layout.get(dependentClass);
            int x1 = (int) dependentLayout.centerX();
            int y1 = (int) dependentLayout.centerY();

            for (String dependency : entry.getValue()) {
                ClassLayout dependencyLayout = layout.get(dependency);
                if (dependencyLayout != null) {
                    int x2 = (int) dependencyLayout.centerX();
                    int y2 = (int) dependencyLayout.centerY();
                    g2d.draw(new Line2D.Float(x1, y1, x2, y2));
                }
            }
        }
    }

    public static void displayUMLDiagram(Map<String, ClassLayout> layout,
                                         Map<String, List<String>> inheritanceRelations,
                                         Map<String, List<String>> dependencyRelations,
                                         Map<String, List<String>> fields,
                                         Map<String, List<String>> methods) {
        JFrame frame = new JFrame("UML Class Diagram");
        DisplayUML panel = new DisplayUML(layout, inheritanceRelations, dependencyRelations, fields, methods);
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class UMLDiagramRunner {
    public static void main(String[] args) {
        List<Class<?>> classes = List.of(MyShape.class, MyCircle.class, MyCircle.InnerStatic.class, MyEllipse.class, Connector.class);

        DiagramData diagramData = new ProcessClasses().process(classes);
        UMLLayout umlLayout = new UMLLayout();
        Map<String, ClassLayout> layout = umlLayout.calculateLayout(diagramData);

        Map<String, List<String>> inheritanceRelations = generateInheritanceRelations(diagramData);
        Map<String, List<String>> dependencyRelations = generateDependencyRelations(diagramData);
        Map<String, List<String>> fields = generateFieldsMap(diagramData);
        Map<String, List<String>> methods = generateMethodsMap(diagramData);

        DisplayUML.displayUMLDiagram(layout, inheritanceRelations, dependencyRelations, fields, methods);
    }

    private static Map<String, List<String>> generateInheritanceRelations(DiagramData diagramData) {
        Map<String, List<String>> inheritanceRelations = new HashMap<>();
        for (Link link : diagramData.links()) {
            if (link.type() == LinkType.SUPERCLASS) {
                inheritanceRelations.computeIfAbsent(link.from(), k -> new ArrayList<>()).add(link.to());
            }
        }
        return inheritanceRelations;
    }

    private static Map<String, List<String>> generateDependencyRelations(DiagramData diagramData) {
        Map<String, List<String>> dependencyRelations = new HashMap<>();
        for (Link link : diagramData.links()) {
            if (link.type() == LinkType.DEPENDENCY) {
                dependencyRelations.computeIfAbsent(link.from(), k -> new ArrayList<>()).add(link.to());
            }
        }
        return dependencyRelations;
    }

    private static Map<String, List<String>> generateFieldsMap(DiagramData diagramData) {
        Map<String, List<String>> fieldsMap = new HashMap<>();
        for (ClassData classData : diagramData.classes()) {
            List<String> fieldNames = new ArrayList<>();
            for (FieldData field : classData.fields()) {
                fieldNames.add(field.name());
            }
            fieldsMap.put(classData.className(), fieldNames);
        }
        return fieldsMap;
    }

    private static Map<String, List<String>> generateMethodsMap(DiagramData diagramData) {
        Map<String, List<String>> methodsMap = new HashMap<>();
        for (ClassData classData : diagramData.classes()) {
            List<String> methodNames = new ArrayList<>();
            for (MethodData method : classData.methods()) {
                methodNames.add(method.name());
            }
            methodsMap.put(classData.className(), methodNames);
        }
        return methodsMap;
    }
}