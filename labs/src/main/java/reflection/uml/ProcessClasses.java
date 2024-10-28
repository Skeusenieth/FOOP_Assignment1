package reflection.uml;

import java.util.*;
import reflection.uml.ReflectionData.*;

public class ProcessClasses {

    List<Link> getSuperclasses(Class<?> c, List<Class<?>> javaClasses) {
        // todo: implement this method
        return null;
    }

    ClassType getClassType(Class<?> c) {
        // todo: fix this broken implementation
        return ClassType.CLASS;
    }

    List<FieldData> getFields(Class<?> c) {
        List<FieldData> fields = new ArrayList<>();
        for (java.lang.reflect.Field f : c.getDeclaredFields()) {
            fields.add(new FieldData(f.getName(), f.getType().getSimpleName()));
        }
        return fields;
    }

    List<MethodData> getMethods(Class<?> c) {
        // todo: implement this method
        return null;
    }

    List<Link> getFieldDependencies(Class<?> c, List<Class<?>> javaClasses) {
        // todo: implement this method - return dependent classes that are in javaClasses
        return null;
    }

    List<Link> getMethodDependencies(Class<?> c, List<Class<?>> javaClasses) {
        // todo: implement this method - return dependent classes that are in javaClasses
        return null;
    }

    DiagramData process(List<Class<?>> javaClasses) {
        // we're going to process the classes here to build up the class data
        List<ClassData> classData = new ArrayList<>();
        Set<Link> links = new HashSet<>();

        for (Class<?> c : javaClasses) {
            String className = c.getSimpleName();
            ClassType classType = getClassType(c);
            List<FieldData> fields = getFields(c);
            List<MethodData> methods = getMethods(c);
            classData.add(new ClassData(className, classType, fields, methods));
            links.addAll(getSuperclasses(c, javaClasses));
            links.addAll(getFieldDependencies(c, javaClasses));
            links.addAll(getMethodDependencies(c, javaClasses));
        }
        return new DiagramData(classData, links);
    }


    public static void main(String[] args) {
        List<Class<?>> classes = new ArrayList<>();
        // add in all the classes we wish to generate UML for
        classes.add(MyShape.class);
        classes.add(MyCircle.class);
        classes.add(MyCircle.InnerStatic.class);
        classes.add(MyEllipse.class);
        classes.add(Connector.class);
        System.out.println(classes);
        System.out.println();
        DiagramData dd = new ProcessClasses().process(classes);
        System.out.println(dd);
        System.out.println();
        for (ClassData cd : dd.classes()) {
            System.out.println(cd);
        }
        System.out.println();
        for (Link l : dd.links()) {
            System.out.println(l);
        }
    }
}
