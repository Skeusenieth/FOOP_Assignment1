package reflection.uml;

import java.util.Set;

public class MyEllipse implements MyShape {
    private final double majorAxis;
    private final double minorAxis;

    public Set<MyCircle> addConnectorSet(Set<Connector> connectors) {
//        this.connectors = connectors;
        return null;
    }

    public MyEllipse(double majorAxis, double minorAxis) {
        this.majorAxis = majorAxis;
        this.minorAxis = minorAxis;
    }

    @Override
    public double area() {
        return Math.PI * majorAxis * minorAxis;
    }

}

