package reflection.gson;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HelloGsonRecordTest {

    @Test
    public void testToJsonString() {
        HelloGsonRecord.QMPerson person = new HelloGsonRecord.QMPerson(
                "Jane", "Doe", "jane.doe@qmul.ac.uk", 12345, true
        );

        String json = HelloGsonRecord.toJsonString(person);
        String expectedJson = "{\"firstnames\":\"Jane\",\"surname\":\"Doe\",\"email\":\"jane.doe@qmul.ac.uk\",\"personId\":12345,\"isCurrent\":true}";

        assertEquals(expectedJson, json);
    }

    @Test
    public void testFromJsonString() {
        String json = "{\"firstnames\":\"Jane\",\"surname\":\"Doe\",\"email\":\"jane.doe@qmul.ac.uk\",\"personId\":12345,\"isCurrent\":true}";

        HelloGsonRecord.QMPerson person = HelloGsonRecord.fromJsonString(json);

        assertEquals("Jane", person.firstnames());
        assertEquals("Doe", person.surname());
        assertEquals("jane.doe@qmul.ac.uk", person.email());
        assertEquals(12345, person.personId());
        assertTrue(person.isCurrent());
    }

    @Test
    public void testRoundTripConversion() {
        HelloGsonRecord.QMPerson originalPerson = new HelloGsonRecord.QMPerson(
                "Alice", "Smith", "alice.smith@example.com", 67890, false
        );

        String json = HelloGsonRecord.toJsonString(originalPerson);
        HelloGsonRecord.QMPerson deserializedPerson = HelloGsonRecord.fromJsonString(json);
        assertEquals(originalPerson, deserializedPerson);

    }
}
