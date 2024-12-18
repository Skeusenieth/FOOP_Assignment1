package lists;

public class IntLinkedList implements IntList {
    private IntNode head;
    private IntNode tail;
    private int len;

    public IntNode getHead() {
        return head;
    }

    public IntNode getTail() {
        return tail;
    }

    public IntLinkedList() {
        head = null;
        tail = null;
        len = 0;
    }

    @Override
    public boolean contains(int value) {
        IntNode current = head;
        while (current != null) {
            if (current.value == value) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public void append(int value) {
        IntNode newNode = new IntNode(value);
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
        return len;
    }

    public static void main(String[] args) {
        IntLinkedList list = new IntLinkedList();
        list.append(1);
        list.append(2);
        list.append(3);
        System.out.println(list.contains(2)); // Expected output: true
        System.out.println(list.contains(4)); // Expected output: false
        System.out.println(list.length()); // Expected output: 3
    }
}

// A node class representing each element in the linked list
class IntNode {
    int value;
    IntNode next;

    public IntNode(int value) {
        this.value = value;
        this.next = null;
    }
}