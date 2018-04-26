package spliterators.example3;

import spliterators.example2.IndexedValue;
import spliterators.example2.Pair;

import java.util.function.Predicate;
import java.util.stream.Stream;

// Stream<Character> a = {A, B, C, D, E...}
// Stream<Character> b = {Q, W, E, R, T...}
public interface AdvancedStream<T> extends Stream<T> {

    // zipped = a.zip(b)
    // zipped = {(A, Q), (B, W), (C, E), (D, R)...}
    <U> AdvancedStream<Pair<T, U>> zip(Stream<U> other);

    // zipped = a.zipWithIndex();
    // zipped = {(0, A), (1, B), (2, C)...}
    AdvancedStream<IndexedValue<T>> zipWithIndex();

    // lessD = a.takeWhile(elem -> elem < 'D');
    // lessD = {A, B, C}
    AdvancedStream<T> takeWhile(Predicate<? super T> predicate);

    // greaterC = a.dropWhile(elem -> elem > 'C');
    // greaterC = {D, E...}
    AdvancedStream<T> dropWhile(Predicate<? super T> predicate);

    @Override
    AdvancedStream<T> filter(Predicate<? super T> predicate);
}
