package interfaces;

/**
 * Single Abstract Method (SAM)-interface, предназначенный для суммирования элементов.
 * @param <T> Тип суммируемых элементов.
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface Summator<T> {

    T sum(T left, T right);

    /*
     * Default-методы позволяют добавлять поведение ранее существовавшим интерфейсам.
     */
    default T twice(T value) {
        return sum(value, value);
    }

    static <V extends Number> V squareNaturalValue(V value, Summator<V> summator) {
        V result = value;
        for (long i = 1; i < value.longValue(); ++i) {
            result = summator.sum(result, value);
        }
        return result;
    }
}
