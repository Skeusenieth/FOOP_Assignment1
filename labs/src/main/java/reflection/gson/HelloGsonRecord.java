package reflection.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javapy.RecordTypeAdapterFactory;

import java.util.List;

public class HelloGsonRecord {

    // todo: modify this to support reading records using a GsonBuilder
    static Gson gson = new Gson();

    public record QMPerson(String firstnames, String surname,
                    String email, int personId, boolean isCurrent) {
    }

    public static String toJsonString(QMPerson person) {
        return gson.toJson(person);
    }

    public static QMPerson fromJsonString(String json) {
        // todo: return a QMPerson object from the json string
        return null;
    }


    public static void main(String[] args) {
        // declare a record class
        // create an instance of the record class
        QMPerson person = new QMPerson("Jane", "Doe",
                "jane.doe@qmul.ac.uk", 12345, true);
        // convert the record instance to a JSON string
        String json = gson.toJson(person);
        // print the JSON string
        System.out.println(json);
        // convert the JSON string back to a record instance
        QMPerson personCopy = fromJsonString(json);
        System.out.println(personCopy);
    }
}


