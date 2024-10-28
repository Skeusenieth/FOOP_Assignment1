package stats;

import java.util.ArrayList;
import java.util.List;

public class ListBasedSummary implements StatSummary {

    final private List<Number> values = new ArrayList<>();


    @Override
    public double mean() {
        if (n() == 0) {
            throw new NotEnoughDataException("Need at least one value for mean, we have zero");
        }
        return sum() / n();
    }

    @Override
    public int n() {
        return values.size();
    }

    @Override
    public double sum() {
        double sum = 0;
        for (Number value : values) {
            sum += value.doubleValue();
        }
        return sum;
    }

    @Override
    public double standardDeviation() {
        if (n() < 2) {
            throw new NotEnoughDataException("Need at least two values for standard deviation, we have " + n());
        }
        double mean = mean();
        double sumOfSquareDiffs = 0;
        for (Number value : values) {
            double diff = value.doubleValue() - mean;
            sumOfSquareDiffs += diff * diff;
        }
        return Math.sqrt(sumOfSquareDiffs / (n() - 1));
    }

    @Override
    public StatSummary add(double value) {
        values.add(value);
        return this;
    }

    @Override
    public StatSummary add(Number value) {
        values.add(value);
        return this;
    }

    @Override
    public StatSummary add(List<? extends Number> newValues) {
        values.addAll(newValues);
        return this;
    }

    public static void main(String[] args) {
        ListBasedSummary summary = new ListBasedSummary();
        summary.add(1).add(2).add(3);
        System.out.println("Mean: " + summary.mean());
        System.out.println("Standard deviation: " + summary.standardDeviation());
    }
}
