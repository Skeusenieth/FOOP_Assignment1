package javapy;

import lists.*; // Assuming all custom list classes are in the lists package
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PerformanceEvaluator {

    // Configuration - adjust here for different list sizes and enable/disable tests
    private static final int[] TEST_SIZES = {10, 100, 1000, 10000, 100000};
    private static final int NUM_TRIALS = 10; // Number of trials for median calculation
    private static final boolean TEST_IntArrayList = true;
    private static final boolean TEST_IntLinkedList = true;
    private static final boolean TEST_EfficientIntArrayList = true;
    private static final boolean TEST_EfficientIntLinkedList = true;
    private static final boolean TEST_GenericLinkedList = true;
    private static final boolean TEST_GenericArrayList = true;
    private static final boolean TEST_GenericLinkedListRecord = true;
    private static final String CSV_FILE = "performance_data.csv";

    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter(CSV_FILE)) {
            writer.append("ListType,Size,AppendTime,RetrievalTime\n");

            // Run tests for each list type and size combination
            for (int n : TEST_SIZES) {
                runTestsForSize(writer, n);
            }

            System.out.println("Performance data saved to " + CSV_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runTestsForSize(FileWriter writer, int n) throws IOException {
        // Run tests for each list type at the current size `n`
        if (TEST_IntArrayList) {
            testIntListPerformance(writer, IntArrayList::new, n, "IntArrayList");
        }
        if (TEST_IntLinkedList) {
            testIntListPerformance(writer, IntLinkedList::new, n, "IntLinkedList");
        }
        if (TEST_EfficientIntArrayList) {
            testIntListPerformance(writer, EfficientIntArrayList::new, n, "EfficientIntArrayList");
        }
        if (TEST_EfficientIntLinkedList) {
            testIntListPerformance(writer, EfficientIntLinkedList::new, n, "EfficientIntLinkedList");
        }
        if (TEST_GenericLinkedList) {
            testGenericListPerformance(writer, GenericLinkedList::new, n, "GenericLinkedList");
        }
        if (TEST_GenericArrayList) {
            testGenericListPerformance(writer, GenericArrayList::new, n, "GenericArrayList");
        }
        if (TEST_GenericLinkedListRecord) {
            testGenericListPerformance(writer, GenericLinkedListRecord::new, n, "GenericLinkedListRecord");
        }
    }

    private static void testIntListPerformance(FileWriter writer, ListSupplier<IntList> supplier, int n, String listName) throws IOException {
        long appendTime = measureMedianAppendTimeIntList(supplier, n);
        long retrievalTime = listName.equals("IntArrayList") ? measureMedianRetrievalTime((IntArrayList) supplier.get(), n) : 0;
        writer.append(String.format("%s,%d,%d,%d\n", listName, n, appendTime, retrievalTime));
    }

    private static void testGenericListPerformance(FileWriter writer, ListSupplier<GenericList<Integer>> supplier, int n, String listName) throws IOException {
        long appendTime = measureMedianAppendTimeGenericList(supplier, n);
        writer.append(String.format("%s,%d,%d,%d\n", listName, n, appendTime, 0));
    }

    private static long measureMedianAppendTimeIntList(ListSupplier<IntList> supplier, int n) {
        List<Long> times = new ArrayList<>();
        for (int t = 0; t < NUM_TRIALS; t++) {
            IntList list = supplier.get(); // Create a new list instance for each trial
            long startTime = System.nanoTime();
            for (int i = 0; i < n; i++) {
                list.append(i);
            }
            long timeTaken = System.nanoTime() - startTime;
            times.add(timeTaken);
        }
        Collections.sort(times);
        return times.get(times.size() / 2); // Median time
    }

    private static long measureMedianAppendTimeGenericList(ListSupplier<GenericList<Integer>> supplier, int n) {
        List<Long> times = new ArrayList<>();
        for (int t = 0; t < NUM_TRIALS; t++) {
            GenericList<Integer> list = supplier.get(); // Create a new list instance for each trial
            long startTime = System.nanoTime();
            for (int i = 0; i < n; i++) {
                list.append(i);
            }
            long timeTaken = System.nanoTime() - startTime;
            times.add(timeTaken);
        }
        Collections.sort(times);
        return times.get(times.size() / 2); // Median time
    }

    private static long measureMedianRetrievalTime(IntArrayList list, int n) {
        List<Long> times = new ArrayList<>();
        for (int t = 0; t < NUM_TRIALS; t++) {
            long startTime = System.nanoTime();
            for (int i = 0; i < list.getLength(); i++) {
                int value = list.getValues()[i];
            }
            long timeTaken = System.nanoTime() - startTime;
            times.add(timeTaken);
        }
        Collections.sort(times);
        return times.get(times.size() / 2); // Median time
    }

    // Functional interface for supplier, similar to Java's Supplier<T> but for custom lists
    @FunctionalInterface
    interface ListSupplier<T> {
        T get();
    }
}