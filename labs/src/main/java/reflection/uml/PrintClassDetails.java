package reflection.uml;

import java.lang.reflect.*;

public class PrintClassDetails {

    public static void main(String[] args) {
        printClass(MyCircle.class);
    }

    public static void printClass(Class<?> c) {
        System.out.println("Inspecting class: " + c.getName());
        System.out.println("\nFields: ");
        // Inspect fields
        for (Field field : c.getDeclaredFields()) {
            printType(field.getName(), field.getGenericType());
        }

        // Inspect methods
        System.out.println("\nMethods: ");
        for (Method method : c.getDeclaredMethods()) {
            System.out.println(method);
            System.out.println("Return type: " + method.getGenericReturnType());
            Type[] parameterTypes = method.getGenericParameterTypes();
            for (Type paramType : parameterTypes) {
                printType(method.getName(), paramType);
            }
            System.out.println();
        }

        // Inspect constructors
        System.out.println("\nConstructors: ");
        for (Constructor<?> constructor : c.getDeclaredConstructors()) {
            System.out.println(constructor);
            Type[] parameterTypes = constructor.getGenericParameterTypes();
            for (Type paramType : parameterTypes) {
                printType(constructor.getName(), paramType);
            }
            System.out.println();
        }
    }

    public static void printType(String name, Type type) {
        System.out.println("Name: " + name + " : " + type.getTypeName());
    }
}

