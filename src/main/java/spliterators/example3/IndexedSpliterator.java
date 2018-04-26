package spliterators.example3;

import spliterators.example2.IndexedValue;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class IndexedSpliterator<T> extends Spliterators.AbstractSpliterator<IndexedValue<T>> {

    private final Spliterator<T> source;
    private long index;
    private long bound;

    public IndexedSpliterator(Spliterator<T> source) {
        this(source, 0, source.estimateSize());
    }

    private IndexedSpliterator(Spliterator<T> source, long from, long bound) {
        super(bound - from, checkCharacteristics(source));
        this.source = source;
        this.bound = bound;
        this.index = from;
    }

    private static <T> int checkCharacteristics(Spliterator<T> source) {
        if (!source.hasCharacteristics(SUBSIZED)) {
            throw new IllegalArgumentException("Non-subsized spliterator");
        }
        return source.characteristics();
    }

    @Override
    public boolean tryAdvance(Consumer<? super IndexedValue<T>> action) {
        if (index == bound) {
            return false;
        }
        source.tryAdvance(value -> action.accept(new IndexedValue<>(index++, value)));
        return true;
    }

    @Override
    public Spliterator<IndexedValue<T>> trySplit() {
        if (estimateSize() == 0) {
            return null;
        }
        Spliterator<T> leftChunk = source.trySplit();
        return leftChunk == null ? null : new IndexedSpliterator<>(leftChunk, index, index += leftChunk.getExactSizeIfKnown());
    }

    @Override
    public void forEachRemaining(Consumer<? super IndexedValue<T>> action) {
        source.forEachRemaining(value -> action.accept(new IndexedValue<>(index++, value)));
    }

    @Override
    public long getExactSizeIfKnown() {
        return source.getExactSizeIfKnown();
    }

    @Override
    public boolean hasCharacteristics(int characteristics) {
        return source.hasCharacteristics(characteristics);
    }

    @Override
    public Comparator<? super IndexedValue<T>> getComparator() {
        return null;
    }
}
