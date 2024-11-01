package lists;

public class EfficientIntLinkedList implements IntList {
    private EfficientIntNode head;
    private EfficientIntNode tail;
    private int len;

    public EfficientIntLinkedList() {
        head = null;
        tail = null;
        len = 0;
    }

    @Override
    public boolean contains(int value) {
        EfficientIntNode current = head;
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
        EfficientIntNode newNode = new EfficientIntNode(value);
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
        EfficientIntLinkedList list = new EfficientIntLinkedList();
        list.append(1);
        list.append(2);
        list.append(3);
        System.out.println(list.contains(2)); // Expected output: true
        System.out.println(list.contains(4)); // Expected output: false
        System.out.println(list.length()); // Expected output: 3
    }
}

// A node class representing each element in the linked list
class EfficientIntNode {
    int value;
    EfficientIntNode next;

    public EfficientIntNode(int value) {
        this.value = value;
        this.next = null;
    }
}