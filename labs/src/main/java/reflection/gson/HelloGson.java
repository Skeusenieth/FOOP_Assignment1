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

        // make a constructor that takes all the fields
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
        // todo: return a PersonClass object from the json string
        return null;
    }

    public static void main(String[] args) {
        PersonClass simon = new PersonClass(
                "Simon",
                "Lucas",
                12345,
                true,
                "simon.lucas@qmul.ac.uk"
        );
        String json = toJsonString(simon);
        System.out.println(json);
        PersonClass simonCopy = fromJsonString(json);
        System.out.println(simonCopy.firstNames);
    }
}
