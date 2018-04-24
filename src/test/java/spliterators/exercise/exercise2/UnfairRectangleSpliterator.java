package spliterators.exercise.exercise2;

import java.util.Spliterators;
import java.util.function.IntConsumer;

public class UnfairRectangleSpliterator extends Spliterators.AbstractIntSpliterator {

    /**
     * 0 1 2 3 4
     * ---------
     * 2 3 4 5 6
     * 2 4 5 6 7
     *
     * 0 1 2 3 4
     * 2 3 / 4 5 6
     * 2 4 5 6 7
     */
    public UnfairRectangleSpliterator(int[][] data) {
        this();
    }

    private UnfairRectangleSpliterator() {
        super(0, 0);
        throw new UnsupportedOperationException();
    }

    @Override
    public OfInt trySplit() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long estimateSize() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void forEachRemaining(IntConsumer action) {
        throw new UnsupportedOperationException();
    }
}