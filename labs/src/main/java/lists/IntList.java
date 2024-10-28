package lists;

public interface IntList {
    // this non-generic interface
    // is used as a starting point
    boolean contains(int value);

    void append(int value);

    int length();

}

