package stats;

/*
A simple class to demonstrate the use of generics.
It retains the most recent object of type T that was added to it.
This will overwrite any previous object of type T that was added.
 */

public class MostRecentObject<T> {
    private T recentObject;

    public void store(T obj) {
        this.recentObject = obj;
    }

    public T get() {
        return this.recentObject;
    }
}


