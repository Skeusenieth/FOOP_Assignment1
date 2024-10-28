package reflection.gson;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CyclicGsonExampleTest {

    @Test
    public void testNonCyclicSerialization() {
        // Non-cyclic case: obj1 references obj2, but obj2 does not reference back
        MyObject obj1 = new MyObject("obj1");
        MyObject obj2 = new MyObject("obj2");
        obj1.setRef(obj2);

        String json = CyclicGsonExample.serializeObject(obj1);

        // Expecting valid JSON serialization since there's no cyclic reference
        String expectedJson = "{\"ref\":{\"name\":\"obj2\"},\"name\":\"obj1\"}";
        assertEquals(expectedJson, json);
    }

    @Test
    public void testCyclicSerializationThrowsException() {
        // Cyclic case: obj1 references obj2 and obj2 references back to obj1
        MyObject obj1 = new MyObject("obj1");
        MyObject obj2 = new MyObject("obj2");
        obj1.setRef(obj2);
        obj2.setRef(obj1);  // Cyclic reference created

        // Expecting CyclicGraphException to be thrown due to the cyclic reference
        CyclicGsonExample.CyclicGraphException exception = assertThrows(
                CyclicGsonExample.CyclicGraphException.class,
                () -> CyclicGsonExample.serializeObject(obj1)
        );

        assertEquals("Gson cannot serialize cyclic graph", exception.getMessage());
    }
}
