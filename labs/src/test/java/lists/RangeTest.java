package lists;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Iterator;

class RangeTest {

    @Test
    void testRangeIteration() {
        Range range = new Range(1, 5);
        Iterator<Integer> iterator = range.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(4, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testRangeWithNegativeStart() {
        Range range = new Range(-3, 2);
        Iterator<Integer> iterator = range.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(-3, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(-2, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(-1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(0, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testEmptyRange() {
        Range range = new Range(5, 5);
        Iterator<Integer> iterator = range.iterator();

        assertFalse(iterator.hasNext());
    }

    @Test
    void testSingleElementRange() {
        Range range = new Range(2, 3);
        Iterator<Integer> iterator = range.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testRangeBackwards() {
        Range range = new Range(5, 2);  // Invalid range
        Iterator<Integer> iterator = range.iterator();

        assertFalse(iterator.hasNext());
    }
}
