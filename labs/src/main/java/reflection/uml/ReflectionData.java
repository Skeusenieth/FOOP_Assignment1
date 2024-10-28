package reflection.uml;

import java.util.List;
import java.util.Set;

public class ReflectionData {

    public enum LinkType {SUPERCLASS, DEPENDENCY}
    public enum ClassType {CLASS, ABSTRACT, INTERFACE}
    public enum Visibility {PUBLIC, PRIVATE, PROTECTED, DEFAULT}

    public record FieldData (String name, String type) { }
    public record MethodData (String name, String returnType) { }

    public record ClassData (
            String className, // just a string
            ClassType classType, // enum
            List<FieldData> fields,
            List<MethodData> methods
    ) {}

    public record Link(String from, String to, LinkType type) { }

    public record DiagramData(List<ClassData> classes, Set<Link> links) { }
}


