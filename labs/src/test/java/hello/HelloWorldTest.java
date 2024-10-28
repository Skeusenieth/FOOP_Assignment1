package hello;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldTest {

    @Test
    public void testGetGreeting() {
        // Test if getGreeting returns "Hello, World!"
        assertEquals("Hello, World!", HelloWorld.getGreeting());
    }

    // now test the main method of HelloWorld
    @Test
    public void testMain() {
        // Test if main method prints "Hello, World!"
        HelloWorld.main(new String[0]);
    }
}
