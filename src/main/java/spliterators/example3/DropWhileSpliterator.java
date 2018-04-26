package spliterators.example3;

import spliterators.example2.IndexedValue;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class DropWhileSpliterator<T> extends Spliterators.AbstractSpliterator<T> {

    private final Spliterator<T> source;
    private final Predicate<? super T> predicate;
    private boolean dropped = false;

    //  001010111011

    //  000000100010
    // ~000000100010
    //  111111011101
    // &original
    //
    public DropWhileSpliterator(Spliterator<T> source, Predicate<? super T> predicate) {
        super(source.estimateSize(), source.characteristics() & ~(SIZED | SUBSIZED));
        this.source = source;
        this.predicate = predicate;
    }


    // [0, 1, 2, 3, 0, 1, 2]
    // dropWhile(element < 3)

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        if (dropped) {
            return source.tryAdvance(action);
        }
        do {} while (!dropped && source.tryAdvance(value -> {
            if (!predicate.test(value)) {
                dropped = true;
                action.accept(value);
            }
        }));
        return dropped;
    }

    @Override
    public Spliterator<T> trySplit() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {
    }
}