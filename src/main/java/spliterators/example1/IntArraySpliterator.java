package spliterators.example1;

import java.util.Spliterators;
import java.util.function.IntConsumer;

public class IntArraySpliterator extends Spliterators.AbstractIntSpliterator {

    public static final int THRESHOLD = 10_000;
    private final int[] data;
    private int startInclusive;
    private int endExclusive;

    public IntArraySpliterator(int[] data) {
        this(data, 0, data.length);
    }

    private IntArraySpliterator(int[] data, int startInclusive, int endExclusive) {
        super(data.length, SIZED | IMMUTABLE | NONNULL | ORDERED);
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
        this.data = data;
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
//        System.out.println("Try advance");
//        System.out.println(Thread.currentThread());
        if (startInclusive == endExclusive) {
            return false;
        }
        action.accept(data[startInclusive++]);
        return true;
    }

    @Override
    public long estimateSize() {
        return getExactSizeIfKnown();
    }

    @Override
    public long getExactSizeIfKnown() {
        return endExclusive - startInclusive;
    }

    @Override
    public OfInt trySplit() {
        if (estimateSize() < THRESHOLD) {
            return null;
        }
//        System.out.println("trySplit");
        int mid = startInclusive + (int)(estimateSize() / 2);
        return new IntArraySpliterator(data, startInclusive, startInclusive = mid);
    }

    @Override
    public void forEachRemaining(IntConsumer action) {
        while (startInclusive != endExclusive) {
            action.accept(data[startInclusive++]);
        }
    }
}
