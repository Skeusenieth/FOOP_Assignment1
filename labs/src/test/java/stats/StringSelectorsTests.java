package stats;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringSelectorsTest {

    @Test
    void testLongestString() {
        StringSelectors stringSelectors = new StringSelectors();
        Selector<String> selector = stringSelectors.longestString();

        selector.add("apple");
        selector.add("banana");
        selector.add("cherry");

        assertEquals("banana", selector.getSelection(), "The longest string should be 'banana'.");
    }

    @Test
    void testShortestString() {
        StringSelectors stringSelectors = new StringSelectors();
        Selector<String> selector = stringSelectors.shortestString();

        selector.add("apple");
        selector.add("banana");
        selector.add("cherry");

        assertEquals("apple", selector.getSelection(), "The shortest string should be 'apple'.");
    }

    @Test
    void testEmptyLongestStringSelector() {
        StringSelectors stringSelectors = new StringSelectors();
        Selector<String> selector = stringSelectors.longestString();

        assertNull(selector.getSelection(), "Selection should be null for an empty selector.");
    }

    @Test
    void testEmptyShortestStringSelector() {
        StringSelectors stringSelectors = new StringSelectors();
        Selector<String> selector = stringSelectors.shortestString();

        assertNull(selector.getSelection(), "Selection should be null for an empty selector.");
    }
}
