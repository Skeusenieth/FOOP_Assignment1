package stats;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

public abstract class AbstractStatSummaryTest {

    // This method will be implemented by concrete subclasses
    protected abstract StatSummary createSummary();

    @Test
    public void testMeanWithValidData() {
        StatSummary summary = createSummary();
        summary.add(Arrays.asList(1, 2, 3, 4, 5));
        assertEquals(3.0, summary.mean(), 0.0001);
    }

    @Test
    public void testMeanWithNoData() {
        StatSummary summary = createSummary();
        assertThrows(NotEnoughDataException.class, summary::mean);
    }

    @Test
    public void testStandardDeviationWithValidData() {
        StatSummary summary = createSummary();
        summary.add(Arrays.asList(1, 2, 3, 4, 5));
        assertEquals(1.5811, summary.standardDeviation(), 0.0001);
    }

    @Test
    public void testStandardDeviationWithInsufficientData() {
        StatSummary summary = createSummary();
        summary.add(1);
        assertThrows(NotEnoughDataException.class, summary::standardDeviation);
    }

    @Test
    public void testNWithMultipleValues() {
        StatSummary summary = createSummary();
        summary.add(Arrays.asList(1, 2, 3));
        assertEquals(3, summary.n());
    }

    @Test
    public void testNWithNoValues() {
        StatSummary summary = createSummary();
        assertEquals(0, summary.n());
    }

    @Test
    public void testAddSingleValue() {
        StatSummary summary = createSummary();
        summary.add(42);
        assertEquals(1, summary.n());
        assertEquals(42, summary.mean(), 0.0001);
    }

    @Test
    public void testAddMultipleValues() {
        StatSummary summary = createSummary();
        summary.add(Arrays.asList(10, 20, 30));
        assertEquals(3, summary.n());
        assertEquals(20, summary.mean(), 0.0001);
    }

    @Test
    public void testSumWithValidData() {
        StatSummary summary = createSummary();
        summary.add(Arrays.asList(1, 2, 3, 4, 5));
        assertEquals(15.0, summary.sum(), 0.0001);
    }

    @Test
    public void testSumWithNoData() {
        StatSummary summary = createSummary();
        assertEquals(0.0, summary.sum(), 0.0001);  // Sum of an empty list should be zero
    }

}
