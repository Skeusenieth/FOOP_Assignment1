package lists;

public class PrintIntListBadly {

    static void testList(IntList list) {
        list.append(10);
        list.append(20);

        System.out.println(list.contains(10));
        System.out.println(list.contains(30));

        System.out.println(list.length());

        printListBadly(list);
    }

    public static void printListBadly(IntList list) {
        if (list instanceof IntLinkedList linkedList) {
            IntNode current = linkedList.head;
            while (current != null) {
                System.out.println(current.value);
                current = current.next;
            }
        } else if (list instanceof IntArrayList arrayList) {
            for (int i = 0; i < arrayList.len; i++) {
                System.out.println(arrayList.values[i]);
            }
        } else {
            throw new IllegalArgumentException(
                    "Unknown list implementation: " +
                            list.getClass().getName());
        }
    }

    public static void main(String[] args) {
        testList(new IntLinkedList());
        System.out.println();
        testList(new IntArrayList());
    }
}
