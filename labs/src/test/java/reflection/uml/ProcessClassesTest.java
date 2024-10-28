package reflection.uml;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import reflection.uml.ReflectionData.*;

// Define a simple interface for testing
interface TestInterface {
}

// Define a simple abstract class for testing
abstract class TestAbstractClass {
    public int fieldInAbstract;
    public abstract void abstractMethod();
}

// Define a regular class for testing
class TestRegularClass extends TestAbstractClass implements TestInterface {
    public int regularField;
    public String regularMethod() {
        return "Hello";
    }

    @Override
    public void abstractMethod() {
        // Implementing abstract method
    }
}

// Define a class to represent dependencies
class TestDependencyClass {
}


public class ProcessClassesTest {


    @Test
    public void testGetClassType() {
        ProcessClasses processor = new ProcessClasses();

        // Test for interface
        ClassType interfaceType = processor.getClassType(TestInterface.class);
        assertEquals(ClassType.INTERFACE, interfaceType, "Expected TestInterface to be classified as INTERFACE.");

        // Test for abstract class
        ClassType abstractType = processor.getClassType(TestAbstractClass.class);
        assertEquals(ClassType.ABSTRACT, abstractType, "Expected TestAbstractClass to be classified as ABSTRACT.");

        // Test for regular class
        ClassType regularType = processor.getClassType(TestRegularClass.class);
        assertEquals(ClassType.CLASS, regularType, "Expected TestRegularClass to be classified as CLASS.");
    }

    @Test
    public void testGetSuperclasses() {
        ProcessClasses processor = new ProcessClasses();
        List<Class<?>> classList = Arrays.asList(TestRegularClass.class, TestAbstractClass.class, TestInterface.class);

        List<Link> superclasses = processor.getSuperclasses(TestRegularClass.class, classList);

        assertEquals(2, superclasses.size(), "Expected TestRegularClass to have 2 superclasses (abstract and interface).");
        assertTrue(superclasses.contains(new Link("TestRegularClass", "TestAbstractClass", LinkType.SUPERCLASS)));
        assertTrue(superclasses.contains(new Link("TestRegularClass", "TestInterface", LinkType.SUPERCLASS)));
    }

    @Test
    public void testGetFields() {
        ProcessClasses processor = new ProcessClasses();
        List<FieldData> fields = processor.getFields(TestRegularClass.class);

        assertEquals(1, fields.size(), "Expected TestRegularClass to have 1 declared field.");
        assertEquals("regularField", fields.get(0).name(), "Expected field name to be 'regularField'.");
        assertEquals("int", fields.get(0).type(), "Expected field type to be 'int'.");
    }

    @Test
    public void testGetMethods() {
        ProcessClasses processor = new ProcessClasses();
        List<MethodData> methods = processor.getMethods(TestRegularClass.class);
        System.out.println(methods);

        assertEquals(2, methods.size(), "Expected TestRegularClass to have 2 declared methods.");
        assertTrue(methods.stream().anyMatch(m -> m.name().equals("regularMethod") && m.returnType().equals("String")));
        assertTrue(methods.stream().anyMatch(m -> m.name().equals("abstractMethod") && m.returnType().equals("void")));
    }

    @Test
    public void testGetFieldDependencies() {
        ProcessClasses processor = new ProcessClasses();
        List<Class<?>> classList = Arrays.asList(TestDependencyClass.class);

        // Define a test class with a dependency
        class TestWithDependency {
            TestDependencyClass dependencyField;
        }

        List<Link> dependencies = processor.getFieldDependencies(TestWithDependency.class, classList);

        assertEquals(1, dependencies.size(), "Expected TestWithDependency to have 1 field dependency.");
        assertEquals(new Link("TestWithDependency", "TestDependencyClass", LinkType.DEPENDENCY), dependencies.get(0));
    }

    @Test
    public void testGetMethodDependencies() {
        ProcessClasses processor = new ProcessClasses();
        List<Class<?>> classList = Arrays.asList(TestDependencyClass.class);

        // Define a test class with a method dependency
        class TestWithMethodDependency {
            public TestDependencyClass methodWithDependency() {
                return new TestDependencyClass();
            }
        }

        List<Link> dependencies = processor.getMethodDependencies(TestWithMethodDependency.class, classList);

        assertEquals(1, dependencies.size(), "Expected TestWithMethodDependency to have 1 method return type dependency.");
        assertEquals(new Link("TestWithMethodDependency", "TestDependencyClass", LinkType.DEPENDENCY), dependencies.get(0));
    }

    @Test
    public void testProcess() {
        ProcessClasses processor = new ProcessClasses();
        class TestWithMethodDependency {
            public TestDependencyClass methodWithDependency() {
                return new TestDependencyClass();
            }
        }

        List<Class<?>> classList = Arrays.asList(
                TestRegularClass.class,
                TestAbstractClass.class,
                TestInterface.class,
                TestDependencyClass.class,
                TestWithMethodDependency.class);

        DiagramData diagramData = processor.process(classList);

        System.out.println("In ProcessClassesTest.testProcess():");
        System.out.println(diagramData);

        assertEquals(5, diagramData.classes().size(), "Expected 3 classes in DiagramData.");
        assertEquals(3, diagramData.links().size(), "Expected 3 links in DiagramData (2 superclasses and 1 dependency).");
    }
}
