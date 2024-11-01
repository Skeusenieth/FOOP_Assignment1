package lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EfficientIntLinkedListTest {

    private EfficientIntLinkedList list;

    @BeforeEach
    void setUp() {
        list = new EfficientIntLinkedList();
    }

    @Test
    void testAppend() {
        list.append(5);
        list.append(15);
        assertEquals(2, list.length());
        assertTrue(list.contains(5));
        assertTrue(list.contains(15));
    }

    @Test
    void testContains() {
        list.append(25);
        assertTrue(list.contains(25));
        assertFalse(list.contains(35));
    }

    @Test
    void testLength() {
        assertEquals(0, list.length());
        list.append(5);
        assertEquals(1, list.length());
        list.append(10);
        assertEquals(2, list.length());
    }

    @Test
    void testAppendMultiple() {
        for (int i = 0; i < 15; i++) {
            list.append(i);
        }
        assertEquals(15, list.length());
        assertTrue(list.contains(10));
        assertFalse(list.contains(20));
    }
}