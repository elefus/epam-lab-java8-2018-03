package spliterators.example2;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

// first : [A1 B1 C1]
// second: [A2 B2 C2]

// zip(first, second): [{A1, A2}, {B1, B2}, {C1, C2}]

// zipWithIndex(first): [{0, A1}, {1, A2}, {2, A3}]



public class IndexedArraySpliterator<T> extends Spliterators.AbstractSpliterator<IndexedValue<T>> {

    private final T[] data;
    private long index;

    public IndexedArraySpliterator(T[] data) {
        super(data.length, SIZED | IMMUTABLE | ORDERED);
        this.data = data;
    }

    @Override
    public boolean tryAdvance(Consumer<? super IndexedValue<T>> action) {
        if (index == data.length) {
            return false;
        }
        action.accept(new IndexedValue<>(index, data[(int)index++]));
        return true;
    }

    @Override
    public void forEachRemaining(Consumer<? super IndexedValue<T>> action) {
        while (index != data.length) {
            action.accept(new IndexedValue<>(index, data[(int)index++]));
        }
    }

    @Override
    public Spliterator<IndexedValue<T>> trySplit() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long estimateSize() {
        return data.length - index;
    }
}
