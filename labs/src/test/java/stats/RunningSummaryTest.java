package stats;

public class RunningSummaryTest extends AbstractStatSummaryTest {

    @Override
    protected StatSummary createSummary() {
        return new RunningSummary();
    }
}
