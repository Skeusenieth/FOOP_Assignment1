package reflection.gson;

import com.google.gson.Gson;

public class HelloGson {
    static Gson gson = new Gson();

    public static class PersonClass {
        String firstNames;
        String lastName;
        int personId;
        boolean isCurrent;
        String email;

        // Constructor that takes all the fields
        public PersonClass(String firstNames, String lastName, int personId, boolean isCurrent, String email) {
            this.firstNames = firstNames;
            this.lastName = lastName;
            this.personId = personId;
            this.isCurrent = isCurrent;
            this.email = email;
        }
    }

    public static String toJsonString(PersonClass person) {
        return gson.toJson(person);
    }

    public static PersonClass fromJsonString(String json) {
        // Deserialize JSON string back into PersonClass object
        return gson.fromJson(json, PersonClass.class);
    }

    public static void main(String[] args) {
        PersonClass simon = new PersonClass(
                "Simon",
                "Lucas",
                12345,
                true,
                "simon.lucas@qmul.ac.uk"
        );

        // Convert to JSON
        String json = toJsonString(simon);
        System.out.println("Serialized JSON: " + json);

        // Deserialize back to PersonClass
        PersonClass simonCopy = fromJsonString(json);
        System.out.println("Deserialized Object:");
        System.out.println("First Names: " + simonCopy.firstNames);
        System.out.println("Last Name: " + simonCopy.lastName);
        System.out.println("Person ID: " + simonCopy.personId);
        System.out.println("Is Current: " + simonCopy.isCurrent);
        System.out.println("Email: " + simonCopy.email);
    }
}