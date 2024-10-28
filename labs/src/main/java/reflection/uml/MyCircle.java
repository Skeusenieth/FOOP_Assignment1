package reflection.uml;

import java.util.*;

public class MyCircle implements MyShape {
    private double radius = 0;
    // we'd normally use Math.PI here, we use this as an example static field
    static double pi = 3.14159;

    public Set<Connector> circles = new HashSet<>();
//    public Map<String, List<? extends MyShape>> map = new HashMap<>();
    public Map<String, Map<String, Integer>> map2 = new HashMap<>();
    Connector connector = new Connector(1.0);

    public MyCircle(double radius) {
        this.radius = radius;
    }

    public MyCircle(double radius, Set<Connector> circles) {
        this.radius = radius;
        this.circles = circles;
    }

    public double area() {
        return pi * radius * radius;
    }

    public double newMethod(Double myDouble) {
        return myDouble * 2;
    }

    public Connector getConnecter() {
        return connector;
    }

    public static class InnerStatic {
        public int x = 0;
    }

    public class Inner {
        public int y = 0;
    }
}

