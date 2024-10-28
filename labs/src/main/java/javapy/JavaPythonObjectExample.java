package javapy;

import jep.Jep;
import jep.JepConfig;
import jep.JepException;
import jep.SubInterpreter;

import java.util.HashMap;
import java.util.Map;

public class JavaPythonObjectExample {
    public static void main(String[] args) {
        try {
            // Use JepConfig to create the interpreter
            JepConfig config = new JepConfig();
            config.addIncludePaths(".", "/opt/miniconda3/bin/python3");
            System.out.println(config);
            try (Jep jep = new SubInterpreter(config)) {
                // Load the Python script
                jep.runScript("./labs/pysrc/javapy/person.py");

                // Call the create_person function to get a dictionary
                jep.eval("result = create_person('Alice', 30)");
                Map<String, Object> personMap = (Map<String, Object>) jep.getValue("result");

                // Print the result (dictionary)
                System.out.println("Person from Python: " + personMap);

                // Pass the object back to Python to describe it
                jep.set("person_dict", personMap);
                String description = (String) jep.getValue("describe_person(person_dict)");

                // Output the result
                System.out.println("Person description from Python: " + description);
            }
        } catch (JepException e) {
            e.printStackTrace();
        }
    }
}
