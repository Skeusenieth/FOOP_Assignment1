package lists;

public class GenIntListWrapper implements IntList {
    @Override
    public boolean contains(int value) {
        return list.contains(value);
    }

    @Override
    public void append(int value) {
        list.append(value);
    }

    @Override
    public int length() {
        return list.length();
    }

    // This is wraps a generic list as an IntList
    private final GenericList<Integer> list;

    public GenIntListWrapper(GenericList<Integer> list) {
        this.list = list;
    }
}
