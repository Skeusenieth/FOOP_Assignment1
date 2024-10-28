package stats;

import java.util.List;

public class RunningSummary implements StatSummary {
    // todo: might some fields be useful here?  :)

    @Override
    public double mean() {
        // todo: implement this
        return 0;
    }

    @Override
    public int n() {
        // todo: implement this
        return 0;
    }

    @Override
    public double sum() {
        return 0;
    }

    @Override
    public double standardDeviation() {
        // todo: implement this
        return 0;
    }

    @Override
    public StatSummary add(double value) {
        // todo: implement this
        return null;
    }

    @Override
    public StatSummary add(Number value) {
        // todo: implement this
        return this.add(value.doubleValue());
    }

    @Override
    public StatSummary add(List<? extends Number> values) {
        // todo: implement this
        return null;
    }
}
