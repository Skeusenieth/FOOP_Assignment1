package lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EfficientIntArrayListTest {

    private EfficientIntArrayList list;

    @BeforeEach
    void setUp() {
        list = new EfficientIntArrayList();
    }

    @Test
    void testAppend() {
        list.append(10);
        list.append(20);
        assertEquals(2, list.length());
        assertTrue(list.contains(10));
        assertTrue(list.contains(20));
    }

    @Test
    void testContains() {
        list.append(30);
        list.append(40);
        assertTrue(list.contains(30));
        assertFalse(list.contains(50));
    }

    @Test
    void testLength() {
        assertEquals(0, list.length());
        list.append(10);
        assertEquals(1, list.length());
        list.append(20);
        assertEquals(2, list.length());
    }

    @Test
    void testResize() {
        for (int i = 0; i < 20; i++) {
            list.append(i);
        }
        assertEquals(20, list.length());
        assertTrue(list.contains(15));
        assertFalse(list.contains(25));
    }
}