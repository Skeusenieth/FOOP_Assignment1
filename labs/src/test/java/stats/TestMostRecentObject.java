package stats;

public class TestMostRecentObject {
    public static void main(String[] args) {
        MostRecentObject<String> recentString = new MostRecentObject<>();
        recentString.store("Hello");
        System.out.println(recentString.get());

        MostRecentObject<Integer> recentInt = new MostRecentObject<>();
        recentInt.store(42);
        System.out.println(recentInt.get());
    }
}
