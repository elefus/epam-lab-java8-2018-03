package spliterators.exercise.exercise2;

import java.util.Spliterators;
import java.util.function.IntConsumer;

/**
 * Сплитератор, оборачивающий прямоугольную матрицу int[][]
 * Обходит элементы слева-направо, сверху-вниз
 * Деление "нечестное" - по строкам
 */
public class UnfairRectangleSpliterator extends Spliterators.AbstractIntSpliterator {

    /**
     *  0  1  2  3  4
     * -------------
     *  5  6  7  8  9
     * 10 11 12 13 14
     */
    public UnfairRectangleSpliterator(int[][] data) {
        this(data, 0, data.length);
    }

    private UnfairRectangleSpliterator(int[][] data, int from, int to) {
        super(data.length, SIZED | IMMUTABLE | NONNULL | ORDERED);

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