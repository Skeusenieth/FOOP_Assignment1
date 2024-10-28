package lists;

import java.util.Iterator;


record GenericNodeRecord<T>(T value, GenericNodeRecord<T> next) {
}

public class GenericLinkedListRecord<T> implements GenericList<T> {
    GenericNodeRecord<T> head;
    int len;

    public GenericLinkedListRecord() {
        head = null;
        len = 0;
    }

    public boolean contains(T value) {
        GenericNodeRecord<T> current = head;
        while (current != null) {
            if (current.value() == value) {
                return true;
            }
            current = current.next();
        }
        return false;
    }

    public void append(T value) {
        head = appendRecord(head, value);
        len++;
    }

    private GenericNodeRecord<T> appendRecord(GenericNodeRecord<T> node, T value) {
        // todo: implement this properly!
        return new GenericNodeRecord<T>(value, null);
    }

    private GenericNodeRecord<T> prependRecord(GenericNodeRecord<T> node, T value) {
        if (node == null) {
            return new GenericNodeRecord<T>(value, null);
        } else {
            return new GenericNodeRecord<T>(value, prependRecord(node.next(), node.value()));
        }
    }


    public int length() {
        return len;
    }

    @Override
    public Iterator<T> iterator() {
        return new GenericLinkedListRecordIterator<T>(this);
    }

    private static class GenericLinkedListRecordIterator<T> implements Iterator<T> {
        private GenericNodeRecord<T> current;

        public GenericLinkedListRecordIterator(GenericLinkedListRecord<T> list) {
            current = list.head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T value = current.value();
            current = current.next();
            return value;
        }
    }

    public static void main(String[] args) {
        GenericLinkedListRecord<Integer> list = new GenericLinkedListRecord<>();
        list.append(1);
        list.append(2);
        list.append(3);
        list.append(4);
        for (Integer i : list) {
            System.out.println(i);
        }
    }
}
