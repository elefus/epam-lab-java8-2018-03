package streams.part1.example;

import org.junit.Test;

import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;

public class Example5 {

    @Test
    public void test() {
        String result = IntStream.of(2, 1, 3, 5, 7, 1, 2, 3)
                                 .filter(value -> {
                                     System.out.println("filtering: " + value);
                                     return value < 5;
                                 })
                                 .distinct()
                                 .sorted()
                                 .map(value -> {
                                     System.out.println("mapping: " + value);
                                     return value + 1;
                                 })
                                 .mapToObj(value -> {
                                     System.out.println("mapping to object: " + value);
                                     return String.valueOf(value);
                                 })
                                 .collect(joining(" "));

        assertEquals("2 3 4", result);
    }
}
