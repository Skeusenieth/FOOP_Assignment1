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
        private int end;

        public OddRangeIterator(int start, int end) {
            // todo: implement this properly
        }

        @Override
        public boolean hasNext() {
            // implement this properly
            return false;
        }

        @Override
        public Integer next() {
            // implement this properly
            return 0;
        }
    }

    public static void main(String[] args) {
        OddRange range = new OddRange(0, 10);
        for (int num : range) {
            System.out.println(num);
        }
    }
}
