package reflection.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javapy.RecordTypeAdapterFactory;

public class HelloGsonRecord {

    // Initialize Gson with support for records using a GsonBuilder and RecordTypeAdapterFactory
    static Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(new RecordTypeAdapterFactory())
            .create();

    public record QMPerson(String firstnames, String surname,
                           String email, int personId, boolean isCurrent) {
    }

    public static String toJsonString(QMPerson person) {
        return gson.toJson(person);
    }

    public static QMPerson fromJsonString(String json) {
        // Deserialize JSON string back into QMPerson record
        return gson.fromJson(json, QMPerson.class);
    }

    public static void main(String[] args) {
        // Create an instance of the QMPerson record
        QMPerson person = new QMPerson("Jane", "Doe",
                "jane.doe@qmul.ac.uk", 12345, true);

        // Convert the record instance to a JSON string
        String json = toJsonString(person);
        System.out.println("Serialized JSON: " + json);

        // Convert the JSON string back to a record instance
        QMPerson personCopy = fromJsonString(json);
        System.out.println("Deserialized Record:");
        System.out.println("Firstnames: " + personCopy.firstnames());
        System.out.println("Surname: " + personCopy.surname());
        System.out.println("Email: " + personCopy.email());
        System.out.println("Person ID: " + personCopy.personId());
        System.out.println("Is Current: " + personCopy.isCurrent());
    }
}