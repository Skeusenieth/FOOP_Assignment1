package reflection.uml;

import java.util.*;
import reflection.uml.ReflectionData.*;

public class UMLLayout {

    // Record to store the layout of each class
    public record ClassLayout(double centerX, double centerY, double width, double height) {}

    private final double classWidth = 150.0;
    private final double heightFac = 30.0;
    private final double verticalSpacing = 70.0; // Increased vertical spacing
    private final double horizontalSpacing = 70.0; // Increased horizontal spacing for better spacing

    // Method to calculate the layout of each class
    public Map<String, ClassLayout> calculateLayout(DiagramData diagram) {
        Map<String, ClassLayout> layout = new HashMap<>();
        List<String> topologicalSortedClasses = topologicalSort(diagram);

        Map<String, List<String>> childrenMap = buildChildrenMap(diagram);
        Map<String, Integer> depthMap = calculateDepths(topologicalSortedClasses, diagram);

        // Track the x position for each depth level to space out classes horizontally
        Map<Integer, Double> nextXPositionForDepth = new HashMap<>();
        Map<String, ClassData> classDataMap = new HashMap<>();

        for (ClassData classData : diagram.classes()) {
            classDataMap.put(classData.className(), classData);
        }

        // Assign positions for each class based on its hierarchy level and content
        for (String className : topologicalSortedClasses) {
            int depth = depthMap.get(className);
            double x = nextXPositionForDepth.getOrDefault(depth, 0.0) + horizontalSpacing;
            double y = depth * (verticalSpacing + heightFac);

            ClassData classData = classDataMap.get(className);
            int numberOfFields = classData.fields().size();
            int numberOfMethods = classData.methods().size();

            // Calculate height based on number of fields and methods with additional padding for large content
            double height = heightFac + (numberOfFields + numberOfMethods) * 18 + 50; // Adjusted values

            layout.put(classData.className(), new ClassLayout(x + classWidth / 2, y + height / 2, classWidth, height));

            // Update x position for the next class at this depth level
            nextXPositionForDepth.put(depth, x + classWidth + horizontalSpacing);
        }

        return layout;
    }

    // Perform topological sort to order classes based on the hierarchy
    private List<String> topologicalSort(DiagramData diagram) {
        Map<String, Integer> inDegree = new HashMap<>();
        Map<String, List<String>> adjList = new HashMap<>();

        // Initialize the adjacency list and in-degree count
        for (ClassData classData : diagram.classes()) {
            inDegree.put(classData.className(), 0);
            adjList.put(classData.className(), new ArrayList<>());
        }

        for (Link link : diagram.links()) {
            if (link.type() == LinkType.SUPERCLASS) {
                adjList.get(link.from()).add(link.to());
                inDegree.put(link.to(), inDegree.get(link.to()) + 1);
            }
        }

        // Kahn's algorithm for topological sorting
        Queue<String> queue = new LinkedList<>();
        for (String className : inDegree.keySet()) {
            if (inDegree.get(className) == 0) {
                queue.add(className);
            }
        }

        List<String> sortedClasses = new ArrayList<>();
        while (!queue.isEmpty()) {
            String current = queue.poll();
            sortedClasses.add(current);

            for (String neighbor : adjList.get(current)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        return sortedClasses;
    }

    // Build a map from classes to their direct subclasses
    private Map<String, List<String>> buildChildrenMap(DiagramData diagram) {
        Map<String, List<String>> childrenMap = new HashMap<>();

        for (ClassData classData : diagram.classes()) {
            childrenMap.put(classData.className(), new ArrayList<>());
        }

        for (Link link : diagram.links()) {
            if (link.type() == LinkType.SUPERCLASS) {
                childrenMap.get(link.to()).add(link.from());
            }
        }

        return childrenMap;
    }

    // Calculate the depth (vertical level) for each class in the hierarchy
    private Map<String, Integer> calculateDepths(List<String> sortedClasses, DiagramData diagram) {
        Map<String, Integer> depthMap = new HashMap<>();

        for (String className : sortedClasses) {
            depthMap.put(className, 0); // Root classes have depth 0
        }

        for (String className : sortedClasses) {
            for (Link link : diagram.links()) {
                if (link.type() == LinkType.SUPERCLASS && link.from().equals(className)) {
                    depthMap.put(link.to(), depthMap.get(className) + 1);
                }
            }
        }

        return depthMap;
    }
}