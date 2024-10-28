package stats;

/*
A simple class to demonstrate the use of generics.
It retains the most recent object of type T that was added to it.
This will overwrite any previous object of type T that was added.
 */

public class MostRecentObject<T> {
    private T mostRecentObject;

    public void add(T object) {
        mostRecentObject = object;
    }

    public T getMostRecentObject() {
        return mostRecentObject;
    }
}


