package streams.part1.example;

import org.junit.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;

public class Example5 {

    // filter(Predicate<T>)
    // map(Function<T, R>)
    // flatMap(Function<T, Stream<R>>)
    // peek(Consumer<T>)
    // distinct()
    // sorted()
    // sorted(Comparator<T>)
    // parallel()
    // sequential()
    // unordered()
    // skip(long)
    // limit(long)
    @Test
    public void test() {
        // "123", "abc", "def"
        // flatMap -> String -> Stream<Character>

        Stream<String> stream = IntStream.of(2, 1, 3, 5, 7, 1, 2, 3)
                                         .filter(value -> {
                                             System.out.println("Filtering: " + value);
                                             return value < 5;
                                         })
                                         .peek(value -> System.out.println("Peek: " + value))
                                         .distinct()
                                         .sorted()
                                         .map(value -> {
                                             System.out.println("Mapping: " + value);
                                             return value + 1;
                                         })
                                         .mapToObj(value -> {
                                             System.out.println("Mapping to object: " + value);
                                             return String.valueOf(value);
                                         });

        String result = stream.collect(joining(" "));

        assertEquals("2 3 4", result);
    }
}
