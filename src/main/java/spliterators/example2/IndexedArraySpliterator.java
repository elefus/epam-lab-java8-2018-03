package spliterators.example2;

import java.util.Spliterators;

public abstract class IndexedArraySpliterator<T> extends Spliterators.AbstractSpliterator<IndexedPair<T>> {

    protected IndexedArraySpliterator(long est, int additionalCharacteristics) {
        super(est, additionalCharacteristics);
    }
}
