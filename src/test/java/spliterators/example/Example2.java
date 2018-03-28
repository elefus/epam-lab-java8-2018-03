package spliterators.example;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Example2 {

    @Test
    public void test() {
        // 0 1 2 3 4 ... 9_999

        // parallel reduce
        // concurrent reduce

        int sum = IntStream.range(0, 10_000)
                           .parallel()
                           .sum();
    }

    @Test
    public void test2() {
        // 0 1 2 3
        String collect = Arrays.asList(0, 1, 2, 3)
                               .stream()
                               .parallel()
                               .map(String::valueOf)
                               .collect(Collectors.joining(" "));
    }

    @Test
    public void test3() {
        // 0 1 2 3
        String collect = IntStream.generate(() -> 1)
                                  .parallel()
                                  .mapToObj(String::valueOf)
                                  .collect(Collectors.joining(" "));
    }
}
