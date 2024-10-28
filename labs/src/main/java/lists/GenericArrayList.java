package lists;


import java.util.Iterator;

class GenericArrayList<T> implements GenericList<T> {
    static int initialCapacity = 10;

    T[] values = (T[]) new Object[initialCapacity];
    int len = 0;

    public boolean contains(T value) {
        for (int i = 0; i < len; i++) {
            if (values[i] == value) {
                return true;
            }
        }
        return false;
    }

    public void append(T value) {
        T[] newValues = (T[]) new Object[len + 1];
        for (int i = 0; i < len; i++) {
            newValues[i] = values[i];
        }
        newValues[len] = value;
        values = newValues;
        len++;
    }

    @Override
    public Iterator<T> iterator() {
        return new GenericArrayListIterator<T>(this);
    }

    public int length() {
        return len;
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
        for (Integer item : list) {
            System.out.println(item);
        }
    }

}
