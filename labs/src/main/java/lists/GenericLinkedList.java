package lists;

import java.util.Iterator;

class GenericNode<T> {
    T value;
    GenericNode<T> next;

    public GenericNode(T value) {
        this.value = value;
        this.next = null;
    }
}

public class GenericLinkedList<T> implements GenericList<T> {
    private GenericNode<T> head;
    private GenericNode<T> tail;
    private int len;

    public GenericLinkedList() {
        head = null;
        tail = null;
        len = 0;
    }

    @Override
    public boolean contains(T value) {
        // Traverses through the list to find the value
        GenericNode<T> current = head;
        while (current != null) {
            if (current.value.equals(value)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public void append(T value) {
        // Creates a new node and updates head or tail as needed
        GenericNode<T> newNode = new GenericNode<>(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        len++;
    }

    @Override
    public int length() {
        return len; // Returns the current length of the list
    }

    @Override
    public Iterator<T> iterator() {
        return new GenericLinkedListIterator<>(this);
    }

    private static class GenericLinkedListIterator<T> implements Iterator<T> {
        private GenericNode<T> current;

        public GenericLinkedListIterator(GenericLinkedList<T> list) {
            current = list.head;
        }

        @Override
        public boolean hasNext() {
            return current != null; // Checks if there are more elements in the list
        }

        @Override
        public T next() {
            T value = current.value; // Retrieves the current value
            current = current.next;  // Moves to the next node
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

        System.out.println("Contains 2? " + list.contains(2)); // Expected: true
        System.out.println("Contains 4? " + list.contains(4)); // Expected: false
        System.out.println("Length: " + list.length()); // Expected: 3
    }
}