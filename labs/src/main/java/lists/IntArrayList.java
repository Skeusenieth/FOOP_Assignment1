package lists;

public class IntArrayList implements IntList {
    int[] values;
    int len;

    public IntArrayList() {
        values = new int[0];
        len = 0;
    }

    public boolean contains(int value) {
        // todo implement this properly
        return false;
    }

    public void append(int value) {
        // this is inefficient but leave as is for now
        int[] newValues = new int[len + 1];
        for (int i = 0; i < len; i++) {
            newValues[i] = values[i];
        }
        newValues[len] = value;
        values = newValues;
        len++;
    }

    public int length() {
        // todo: fix this!
        return 0;
    }
}
