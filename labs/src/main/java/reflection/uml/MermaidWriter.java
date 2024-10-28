package reflection.uml;

import java.util.ArrayList;
import java.util.List;

import reflection.uml.ReflectionData.*;

public class MermaidWriter {
    final static String superclass = " --|> ";
    final static String dependency = " ..> ";
    String writeMermaid(DiagramData diagramData) {
        // write out the diagram data in Mermaid format
        // first process each class
        StringBuilder sb = new StringBuilder();
        sb.append("classDiagram\n");
        for (ClassData cd : diagramData.classes()) {
            sb.append("class ").append(cd.className());
            sb.append(" {\n");
            for (FieldData fd : cd.fields()) {
                sb.append(fd.name()).append(" : ").append(fd.type()).append("\n");
            }
            for (MethodData md : cd.methods()) {
                sb.append(md.name()).append("() : ").append(md.returnType()).append("\n");
            }
            sb.append("}\n");
        }

        // then to each link
        for (Link l : diagramData.links()) {
            sb.append(l.from());
            switch (l.type()) {
                case SUPERCLASS -> sb.append(superclass);
                case DEPENDENCY -> sb.append(dependency);
            }
            sb.append(l.to()).append("\n");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        List<Class<?>> classes = new ArrayList<>();
        // add in all the classes we wish to generate UML for
        classes.add(MyShape.class);
        classes.add(MyCircle.class);
//        classes.add(MyCircle.InnerStatic.class);
        classes.add(MyEllipse.class);
        classes.add(Connector.class);
        System.out.println(classes);
        System.out.println();
        DiagramData dd = new ProcessClasses().process(classes);
        System.out.println();
        System.out.println(new MermaidWriter().writeMermaid(dd));
    }
}

