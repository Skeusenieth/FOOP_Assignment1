package reflection.uml;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import reflection.uml.ReflectionData.*;

public class ProcessClasses {

    List<Link> getSuperclasses(Class<?> c, List<Class<?>> javaClasses) {
        List<Link> links = new ArrayList<>();
        Class<?> superclass = c.getSuperclass();

        // Check for superclass
        if (superclass != null && javaClasses.contains(superclass)) {
            links.add(new Link(c.getSimpleName(), superclass.getSimpleName(), LinkType.SUPERCLASS));
        }

        // Check for interfaces
        for (Class<?> interf : c.getInterfaces()) {
            if (javaClasses.contains(interf)) {
                links.add(new Link(c.getSimpleName(), interf.getSimpleName(), LinkType.SUPERCLASS));
            }
        }

        return links;
    }

    ClassType getClassType(Class<?> c) {
        if (c.isInterface()) {
            return ClassType.INTERFACE;
        } else if (java.lang.reflect.Modifier.isAbstract(c.getModifiers())) {
            return ClassType.ABSTRACT;
        } else {
            return ClassType.CLASS;
        }
    }

    List<FieldData> getFields(Class<?> c) {
        List<FieldData> fields = new ArrayList<>();
        for (Field field : c.getDeclaredFields()) {
            fields.add(new FieldData(field.getName(), field.getType().getSimpleName()));
        }
        return fields;
    }

    List<MethodData> getMethods(Class<?> c) {
        List<MethodData> methods = new ArrayList<>();
        for (Method method : c.getDeclaredMethods()) {
            // Exclude synthetic and generated methods (like Jacoco's $jacocoInit)
            if (!method.isSynthetic() && !method.getName().contains("$")) {
                methods.add(new MethodData(method.getName(), method.getReturnType().getSimpleName()));
            }
        }
        return methods;
    }

    List<Link> getFieldDependencies(Class<?> c, List<Class<?>> javaClasses) {
        List<Link> dependencies = new ArrayList<>();
        for (Field field : c.getDeclaredFields()) {
            if (javaClasses.contains(field.getType())) {
                dependencies.add(new Link(c.getSimpleName(), field.getType().getSimpleName(), LinkType.DEPENDENCY));
            }
        }
        return dependencies;
    }

    List<Link> getMethodDependencies(Class<?> c, List<Class<?>> javaClasses) {
        Set<Link> dependencies = new HashSet<>();

        for (Method method : c.getDeclaredMethods()) {
            // Check return type dependency
            Class<?> returnType = method.getReturnType();
            if (javaClasses.contains(returnType)) {
                dependencies.add(new Link(c.getSimpleName(), returnType.getSimpleName(), LinkType.DEPENDENCY));
            }

            // Check parameter type dependencies
            for (Parameter parameter : method.getParameters()) {
                Class<?> paramType = parameter.getType();
                if (javaClasses.contains(paramType)) {
                    dependencies.add(new Link(c.getSimpleName(), paramType.getSimpleName(), LinkType.DEPENDENCY));
                }
            }
        }

        // Convert Set back to List to match method signature
        return new ArrayList<>(dependencies);
    }


    DiagramData process(List<Class<?>> javaClasses) {
        List<ClassData> classData = new ArrayList<>();
        Set<Link> links = new HashSet<>();

        for (Class<?> c : javaClasses) {
            String className = c.getSimpleName();
            ClassType classType = getClassType(c);
            List<FieldData> fields = getFields(c);
            List<MethodData> methods = getMethods(c);
            classData.add(new ClassData(className, classType, fields, methods));

            // Get all links
            links.addAll(getSuperclasses(c, javaClasses));
            links.addAll(getFieldDependencies(c, javaClasses));
            links.addAll(getMethodDependencies(c, javaClasses));
        }
        return new DiagramData(classData, links);
    }

    public static void main(String[] args) {
        List<Class<?>> classes = new ArrayList<>();
        classes.add(MyShape.class);
        classes.add(MyCircle.class);
        classes.add(MyCircle.InnerStatic.class);
        classes.add(MyEllipse.class);
        classes.add(Connector.class);

        ProcessClasses processor = new ProcessClasses();
        DiagramData dd = processor.process(classes);

        System.out.println("Classes in DiagramData:");
        for (ClassData cd : dd.classes()) {
            System.out.println(cd);
        }
        System.out.println("\nLinks in DiagramData:");
        for (Link l : dd.links()) {
            System.out.println(l);
        }
    }
}