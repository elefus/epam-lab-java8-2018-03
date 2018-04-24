package spliterators.example2;

public class IndexedValue<T> extends Pair<Long, T> {

    public IndexedValue(Long index, T value) {
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
