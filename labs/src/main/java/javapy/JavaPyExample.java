package javapy;

import jep.Jep;
import jep.JepConfig;
import jep.JepException;
import jep.SubInterpreter;
//import jep.PyMethod;


public class JavaPyExample {
    public static void main(String[] args) {
        // Use JepConfig to create the interpreter
        JepConfig config = new JepConfig();
        config.addIncludePaths(".", "/opt/miniconda3/bin/python3");
        System.out.println(config);
        Jep jep = config.createSubInterpreter();
        System.out.println(jep);
        try {
            // Load the Python script
            jep.runScript("./labs/pysrc/javapy/example.py");

            // Call the add function
            int a = 5;
            int b = 7;
            Object result = jep.invoke("my_add", a, b);

            // Output the result
            System.out.println("Result from Python: " + result);
        } catch (JepException e) {
            e.printStackTrace();
        }

    }
}
