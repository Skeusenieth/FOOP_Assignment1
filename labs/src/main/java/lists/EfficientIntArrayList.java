package lists;

public class EfficientIntArrayList implements IntList {
    private int[] values;
    private int len;
    private static final int INITIAL_CAPACITY = 10;
    private static final double GROWTH_FACTOR = 1.5; // Increase capacity by 50% on resize

    public EfficientIntArrayList() {
        values = new int[INITIAL_CAPACITY];
        len = 0;
    }

    public EfficientIntArrayList(int initialCapacity) {
        values = new int[Math.max(initialCapacity, INITIAL_CAPACITY)];
        len = 0;
    }

    @Override
    public boolean contains(int value) {
        for (int i = 0; i < len; i++) {
            if (values[i] == value) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void append(int value) {
        // Check if resizing is necessary
        if (len == values.length) {
            resize();
        }
        values[len++] = value;
    }

    private void resize() {
        int newCapacity = (int) (values.length * GROWTH_FACTOR);
        int[] newValues = new int[newCapacity];
        System.arraycopy(values, 0, newValues, 0, len);
        values = newValues;
    }

    @Override
    public int length() {
        return len;
    }

    public static void main(String[] args) {
        EfficientIntArrayList list = new EfficientIntArrayList();
        list.append(1);
        list.append(2);
        list.append(3);
        System.out.println(list.contains(2)); // Expected output: true
        System.out.println(list.contains(4)); // Expected output: false
        System.out.println(list.length()); // Expected output: 3
    }
}