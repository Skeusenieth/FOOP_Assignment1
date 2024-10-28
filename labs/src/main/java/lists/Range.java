package lists;

import java.util.Iterator;

public class Range implements Iterable<Integer> {
    private final int start;
    private final int end;

    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new RangeIterator(start, end);
    }

    private static class RangeIterator implements Iterator<Integer> {
        private int current;
        private final int end;

        public RangeIterator(int start, int end) {
            this.current = start;
            this.end = end;
        }

        @Override
        public boolean hasNext() {
            return current < end;
        }

        @Override
        public Integer next() {
            return current++;
        }

    }

    public static void main(String[] args) {
        Range range = new Range(-3, 3);
        for (int num : range) {
            System.out.println(num);
        }
    }
}
