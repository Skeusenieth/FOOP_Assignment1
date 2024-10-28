package stats;

public class ListBasedSummaryTest extends AbstractStatSummaryTest {

    @Override
    protected StatSummary createSummary() {
        return new ListBasedSummary();
    }
}
