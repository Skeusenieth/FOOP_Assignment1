package lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

record GraphDataResultSet(
        List<Integer> xValues,
        Map<String, List<Double>> results
) {}

public class EvalIntListSpeed {
    static long time_n_appends(IntList list, int n) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            list.append(i);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static void main(String[] args) {
        IntList[] lists = {
                new IntArrayList(),
                new IntLinkedList(),
        };

        List<Supplier<IntList>> listMakers = new ArrayList<>();
        listMakers.add(IntArrayList::new);
        listMakers.add(IntLinkedList::new);

        int initial_n = 10000;
        int n_step = 10000;
        int n_max = 100000;
        List<Integer> xValues = new ArrayList<>();
        for (int n = initial_n; n <= n_max; n += n_step) {
            xValues.add(n);
        }

        Map<String, List<Double>> results = new HashMap<>();
        for (Supplier<IntList> listMaker : listMakers) {
            String seriesName = listMaker.get().getClass().getName();
            List<Double> series = new ArrayList<>();
            System.out.println("List class: " + seriesName);
            for (int n = initial_n; n <= n_max; n += n_step) {
                System.out.println(n);
                long t = time_n_appends(listMaker.get(), n);
                series.add((double) t);
            }
            results.put(seriesName, series);
        }
        System.out.println(new GraphDataResultSet(xValues, results));
    }
}
