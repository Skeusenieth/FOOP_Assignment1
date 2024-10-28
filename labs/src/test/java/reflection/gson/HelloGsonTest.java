package reflection.gson;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HelloGsonTest {

    @Test
    public void testToJsonString() {
        HelloGson.PersonClass person = new HelloGson.PersonClass(
                "Simon", "Lucas", 12345, true, "simon.lucas@qmul.ac.uk"
        );

        String json = HelloGson.toJsonString(person);
        String expectedJson = "{\"firstNames\":\"Simon\",\"lastName\":\"Lucas\",\"personId\":12345,\"isCurrent\":true,\"email\":\"simon.lucas@qmul.ac.uk\"}";

        assertEquals(expectedJson, json);
    }

    @Test
    public void testFromJsonString() {
        String json = "{\"firstNames\":\"Simon\",\"lastName\":\"Lucas\",\"personId\":12345,\"isCurrent\":true,\"email\":\"simon.lucas@qmul.ac.uk\"}";

        HelloGson.PersonClass person = HelloGson.fromJsonString(json);

        assertEquals("Simon", person.firstNames);
        assertEquals("Lucas", person.lastName);
        assertEquals(12345, person.personId);
        assertTrue(person.isCurrent);
        assertEquals("simon.lucas@qmul.ac.uk", person.email);
    }

    @Test
    public void testRoundTripConversion() {
        HelloGson.PersonClass originalPerson = new HelloGson.PersonClass(
                "Alice", "Smith", 67890, false, "alice.smith@example.com"
        );

        String json = HelloGson.toJsonString(originalPerson);
        HelloGson.PersonClass deserializedPerson = HelloGson.fromJsonString(json);

        assertEquals(originalPerson.firstNames, deserializedPerson.firstNames);
        assertEquals(originalPerson.lastName, deserializedPerson.lastName);
        assertEquals(originalPerson.personId, deserializedPerson.personId);
        assertEquals(originalPerson.isCurrent, deserializedPerson.isCurrent);
        assertEquals(originalPerson.email, deserializedPerson.email);
    }
}
