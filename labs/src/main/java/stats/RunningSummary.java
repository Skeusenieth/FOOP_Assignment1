package stats;

import java.util.List;

public class RunningSummary implements StatSummary {
    private int count;
    private double sum;
    private double sumOfSquares;

    public RunningSummary() {
        this.count = 0;
        this.sum = 0.0;
        this.sumOfSquares = 0.0;
    }

    @Override
    public double mean() {
        if (count == 0) {
            throw new NotEnoughDataException("No data to calculate mean.");
        }
        return sum / count;
    }

    @Override
    public int n() {
        return count;
    }

    @Override
    public double sum() {
        return sum;
    }

    @Override
    public double standardDeviation() {
        if (count < 2) {
            throw new NotEnoughDataException("Not enough data to calculate standard deviation.");
        }
        double mean = mean();
        double variance = (sumOfSquares - (sum * sum) / count) / (count - 1);
        return Math.sqrt(variance);
    }

    @Override
    public StatSummary add(double value) {
        count++;
        sum += value;
        sumOfSquares += value * value;
        return this;
    }

    @Override
    public StatSummary add(Number value) {
        return add(value.doubleValue());
    }

    @Override
    public StatSummary add(List<? extends Number> values) {
        for (Number value : values) {
            add(value.doubleValue());
        }
        return this;
    }
}