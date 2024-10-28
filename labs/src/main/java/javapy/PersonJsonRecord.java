package javapy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jep.Jep;
import jep.JepConfig;
import jep.JepException;
import jep.SubInterpreter;

import java.util.Map;

public record PersonJsonRecord(String name, int age) {

    // Serialize the record to JSON using Gson
    public String toJson(Gson gson) {
        return gson.toJson(this);
    }

    // Deserialize the JSON string to a PersonJson object
    public static PersonJsonRecord fromJson(String json, Gson gson) {
        return gson.fromJson(json, PersonJsonRecord.class);
    }

    public static void main(String[] args) {
        jepExample();
    }

    public static void jepExample() {
        try {
            // Use JepConfig to create the interpreter
            JepConfig config = new JepConfig();
            config.addIncludePaths(".", "/opt/miniconda3/bin/python3");
            System.out.println(config);
            try (Jep jep = new SubInterpreter(config)) {
                // Load the Python script
                jep.runScript("./labs/pysrc/javapy/person_pydantic.py");

                PersonJsonRecord person = new PersonJsonRecord("Alice", 30);
                Gson gson = new GsonBuilder()
                        .registerTypeAdapterFactory(new RecordTypeAdapterFactory())
                        .create();
                String json = person.toJson(gson);

                // Set the JSON string in Python
                jep.set("json_str", json);

                // Call the Python function to parse the JSON
                jep.eval("person = parse_person(json_str)");

                // Access the parsed object and print it from Python
                String pythonPerson = (String) jep.getValue("person.json()");
                System.out.println("Person object from Python (JSON): " + pythonPerson);

                PersonJsonRecord newPerson = PersonJsonRecord.fromJson(pythonPerson, gson);
                System.out.println("Deserialized object: " + newPerson);

            }
        } catch (JepException e) {
            e.printStackTrace();
        }
    }

    public static void simpleTest() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new RecordTypeAdapterFactory())
                .create();

        // Create a PersonJson record
        PersonJsonRecord person = new PersonJsonRecord("Alice", 30);

        // Serialize to JSON
        String json = person.toJson(gson);
        System.out.println("JSON representation: " + json);

        // Deserialize from JSON
        PersonJsonRecord newPerson = PersonJsonRecord.fromJson(json, gson);
        System.out.println("Deserialized object: " + newPerson.name() + ", " + newPerson.age());

        

    }


}
