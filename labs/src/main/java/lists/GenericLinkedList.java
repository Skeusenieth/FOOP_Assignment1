package lists;

import java.util.Iterator;


// a IntNode for each element in LinkedList
class GenericNode<T> {
    T value;
    GenericNode<T> next;

    public GenericNode(T value) {
        this.value = value;
        this.next = null;
    }
}


public class GenericLinkedList<T> implements GenericList<T> {
    GenericNode<T> head;
    int len;

    public GenericLinkedList() {
        head = null;
        len = 0;
    }

    public boolean contains(T value) {
        // todo: implement this properly
        return false;
    }

    public void append(T value) {
        // todo: implement an efficient append method
    }

    public int length() {
        return len;
    }

    @Override
    public Iterator<T> iterator() {
        return new GenericLinkedListIterator<T>(this);
    }

    private static class GenericLinkedListIterator<T> implements Iterator<T> {
        private GenericNode<T> current;

        public GenericLinkedListIterator(GenericLinkedList<T> list) {
            current = list.head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T value = current.value;
            current = current.next;
            return value;
        }
    }

    public static void main(String[] args) {
        GenericLinkedList<Integer> list = new GenericLinkedList<>();
        list.append(1);
        list.append(2);
        list.append(3);
        for (Integer i : list) {
            System.out.println(i);
        }
    }
}
