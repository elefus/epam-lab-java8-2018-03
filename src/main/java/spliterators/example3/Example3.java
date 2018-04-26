package spliterators.example3;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class Example3 {

    public static void main(String[] args) {
        Stream<Integer> ints = Stream.of(1, 2, 3, 4, 5);


        AdvancedStream<Integer> advanced = AdvancedStreamImpl.of(ints);
        advanced.zipWithIndex()
                .dropWhile(integerIndexedValue -> true)
                .filter(integerIndexedValue -> ThreadLocalRandom.current().nextBoolean())
                .count();


    }
}
