package stats;

import java.util.Comparator;

public class StringSelectors {

    public Selector<String> longestString() {
        return new Selector<>(Comparator.comparingInt(String::length));
    }

    public Selector<String> shortestString() {
        return new Selector<>(Comparator.comparingInt(String::length).reversed());
    }
}