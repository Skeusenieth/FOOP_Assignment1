package lists;

import java.util.Iterator;

public class OddRange implements Iterable<Integer> {
    private final int start;
    private final int end;

    public OddRange(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new OddRangeIterator(start, end);
    }

    private static class OddRangeIterator implements Iterator<Integer> {
        private int current;
        private final int end;

        public OddRangeIterator(int start, int end) {
            this.current = (start % 2 == 0) ? start + 1 : start;
            this.end = end;
        }

        @Override
        public boolean hasNext() {
            return current < end;
        }

        @Override
        public Integer next() {
            int temp = current;
            current += 2;
            return temp;
        }
    }

    public static void main(String[] args) {
        OddRange range = new OddRange(0, 10);
        for (int num : range) {
            System.out.println(num); // Expected output: 1, 3, 5, 7, 9
        }
    }
}