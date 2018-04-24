package spliterators.example2;

public class IndexedPair<T> extends Pair<Long, T> {

    public IndexedPair(Long index, T value) {
        super(index, value);
    }

    public Long getIndex() {
        return getValue1();
    }

    @Override
    public String toString() {
        return "[" + getValue1() + "] = " + getValue2();
    }
}
