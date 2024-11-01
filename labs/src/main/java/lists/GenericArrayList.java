package lists;

import java.util.Iterator;

public class GenericArrayList<T> implements GenericList<T> {
    private static final int INITIAL_CAPACITY = 10; // Initial capacity for the array
    private T[] values;
    private int len;

    public GenericArrayList() {
        values = (T[]) new Object[INITIAL_CAPACITY];
        len = 0;
    }

    // Checks if the list contains the given value
    @Override
    public boolean contains(T value) {
        for (int i = 0; i < len; i++) {
            if (values[i].equals(value)) {
                return true;
            }
        }
        return false;
    }

    // Appends a new value to the list
    @Override
    public void append(T value) {
        // Resizes the array when capacity is reached
        if (len == values.length) {
            resize();
        }
        values[len++] = value;
    }

    // Doubles the array size to optimize append operations
    private void resize() {
        T[] newValues = (T[]) new Object[values.length * 2];
        System.arraycopy(values, 0, newValues, 0, len);
        values = newValues;
    }

    @Override
    public int length() {
        return len;
    }

    @Override
    public Iterator<T> iterator() {
        return new GenericArrayListIterator<>(this);
    }

    private static class GenericArrayListIterator<T> implements Iterator<T> {
        private int index;
        private final GenericArrayList<T> list;

        public GenericArrayListIterator(GenericArrayList<T> list) {
            this.list = list;
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < list.len;
        }

        @Override
        public T next() {
            return list.values[index++];
        }
    }

    public static void main(String[] args) {
        GenericArrayList<Integer> list = new GenericArrayList<>();
        list.append(1);
        list.append(2);
        System.out.println("List contains 1: " + list.contains(1)); // Expected: true
        System.out.println("List contains 3: " + list.contains(3)); // Expected: false
        System.out.println("List length: " + list.length()); // Expected: 2
        for (Integer item : list) {
            System.out.println(item);
        }
    }
}