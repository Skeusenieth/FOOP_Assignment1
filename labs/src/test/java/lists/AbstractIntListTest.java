package lists;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public abstract class AbstractIntListTest {

    // Factory method to create a new IntList instance
    protected abstract IntList createList();

    @Test
    public void testEmptyList() {
        IntList list = createList();
        assertEquals(0, list.length(), "New list should have length 0");
        assertFalse(list.contains(0), "New list should not contain any element");
    }

    @Test
    public void testAppendAndLength() {
        IntList list = createList();
        list.append(1);
        assertEquals(1, list.length(), "List should have length 1 after one append");
        list.append(2);
        assertEquals(2, list.length(), "List should have length 2 after two appends");
    }

    @Test
    public void testContains() {
        IntList list = createList();
        list.append(1);
        list.append(2);
        list.append(3);
        assertTrue(list.contains(1), "List should contain 1");
        assertTrue(list.contains(2), "List should contain 2");
        assertTrue(list.contains(3), "List should contain 3");
        assertFalse(list.contains(4), "List should not contain 4");
    }

    @Test
    public void testAppendAndContains() {
        IntList list = createList();
        for (int i = 0; i < 10; i++) {
            list.append(i);
            assertTrue(list.contains(i), "List should contain " + i);
            assertEquals(i + 1, list.length(), "List length should be " + (i + 1));
        }
    }

    @Test
    public void testContainsNonExisting() {
        IntList list = createList();
        list.append(1);
        list.append(2);
        list.append(3);
        assertFalse(list.contains(4), "List should not contain 4");
        assertFalse(list.contains(-1), "List should not contain -1");
    }

    @Test
    public void testMultipleAppends() {
        IntList list = createList();
        int[] values = {5, 3, 7, 1, 9};
        for (int val : values) {
            list.append(val);
        }
        assertEquals(values.length, list.length(), "List length should match number of appended values");
        for (int val : values) {
            assertTrue(list.contains(val), "List should contain " + val);
        }
    }

    // Add more tests as needed
}
