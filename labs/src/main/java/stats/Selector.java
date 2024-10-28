package stats;

/*
    * Generic class that selects the best fit from a list of objects.
 */

import java.util.Comparator;

public class Selector<T> {
    private T bestFit = null;
    private final Comparator<T> comparator;

    public Selector(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void add(T object) {
        if (bestFit == null || comparator.compare(object, bestFit) > 0) {
            bestFit = object;
        }
        System.out.println("Added: " + object);
    }

    public T getSelection() {
        return bestFit;
    }

    public static void main(String[] args) {
        Selector<Integer> selector = new Selector<>((a, b) -> b - a);
        selector.add(1);
        selector.add(2);
        selector.add(3);
        selector.add(0);
        selector.add(-1);
        System.out.println(selector.getSelection());
    }
}

