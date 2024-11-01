package lists;

public class IntArrayList implements IntList {
    private int[] values;
    private int len;

    public IntArrayList() {
        values = new int[0];
        len = 0;
    }

    public int[] getValues() {
        return values;
    }

    public int getLength() {
        return len;
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
        int[] newValues = new int[len + 1];
        for (int i = 0; i < len; i++) {
            newValues[i] = values[i];
        }
        newValues[len] = value;
        values = newValues;
        len++;
    }

    @Override
    public int length() {
        return len;
    }

    public static void main(String[] args) {
        IntArrayList list = new IntArrayList();
        list.append(1);
        list.append(2);
        list.append(3);
        System.out.println(list.contains(2)); // Expected output: true
        System.out.println(list.contains(4)); // Expected output: false
        System.out.println(list.length()); // Expected output: 3
    }
}