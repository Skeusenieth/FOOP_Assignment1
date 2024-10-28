package stats;


import java.util.List;

public interface StatSummary {
    double mean();
    int n();
    double sum();
    double standardDeviation();
    StatSummary add(double value);
    StatSummary add(Number value);
    // the list Type must be a subclass of Number
    StatSummary add(List<? extends Number> values);
}

