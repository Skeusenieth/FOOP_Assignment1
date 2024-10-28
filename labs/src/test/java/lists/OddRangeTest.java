package lists;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Iterator;

class OddRangeTest {

    @Test
    void testOddRangeIteration() {
        OddRange range = new OddRange(1, 10);
        Iterator<Integer> iterator = range.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(5, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(7, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(9, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testOddRangeWithEvenStart() {
        OddRange range = new OddRange(2, 10);  // Start is even, should begin at 3
        Iterator<Integer> iterator = range.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(5, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(7, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(9, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testOddRangeWithNegativeValues() {
        OddRange range = new OddRange(-6, 5);  // Should start at -5
        Iterator<Integer> iterator = range.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(-5, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(-3, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(-1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testOddRangeWithOddStart() {
        OddRange range = new OddRange(7, 15);
        Iterator<Integer> iterator = range.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(7, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(9, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(11, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(13, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testEmptyOddRange() {
        OddRange range = new OddRange(5, 5);  // No elements in range
        Iterator<Integer> iterator = range.iterator();

        assertFalse(iterator.hasNext());
    }

    @Test
    void testOddRangeBackwards() {
        OddRange range = new OddRange(5, -5);  // Invalid range, should be empty
        Iterator<Integer> iterator = range.iterator();

        assertFalse(iterator.hasNext());
    }
}
