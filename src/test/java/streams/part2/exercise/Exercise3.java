package streams.part2.exercise;

import org.junit.Test;

import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;

public class Exercise3 {

    @Test
    public void createLimitedStringWithOddNumbersSeparatedBySpaces() {
        int countNumbers = 10;

        String result = IntStream
                .iterate(1, value -> value + 2)
                .limit(countNumbers)
                .mapToObj(String::valueOf)
                .collect(joining(" "));

        assertEquals("1 3 5 7 9 11 13 15 17 19", result);
    }

    @Test
    public void extractEvenNumberedCharactersToNewString() {
        String source = "abcdefghijklm";

        String result = IntStream
                .range(0, source.length())
                .filter(value -> value % 2 == 0)
                .map(source::codePointAt)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        assertEquals("acegikm", result);
    }
}
