package reflection.gson;

import com.google.gson.Gson;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.Collections;

class MyObject {
    MyObject ref;
    String name;

    MyObject(String name) {
        this.name = name;
    }

    void setRef(MyObject ref) {
        this.ref = ref;
    }
}

public class CyclicGsonExample {

    public static class CyclicGraphException extends RuntimeException {
        public CyclicGraphException() {
            super("Gson cannot serialize cyclic graph");
        }
    }

    public static String serializeObject(MyObject obj) {
        // Use a set to track visited objects for cycle detection
        Set<MyObject> visited = Collections.newSetFromMap(new IdentityHashMap<>());
        if (detectCycle(obj, visited)) {
            throw new CyclicGraphException();
        }

        // If no cycle is detected, proceed with serialization
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    private static boolean detectCycle(MyObject obj, Set<MyObject> visited) {
        if (obj == null) return false;
        if (!visited.add(obj)) {
            return true; // Cycle detected
        }
        return detectCycle(obj.ref, visited); // Recursive check on referenced objects
    }

    public static void main(String[] args) {
        // Non-cyclic case
        MyObject obj1 = new MyObject("obj1");
        MyObject obj2 = new MyObject("obj2");
        obj1.setRef(obj2);

        try {
            String json = serializeObject(obj1);
            System.out.println("Non-cyclic serialization: " + json);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Cyclic case
        obj2.setRef(obj1);

        try {
            String json = serializeObject(obj1);
            System.out.println("Cyclic serialization: " + json);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}