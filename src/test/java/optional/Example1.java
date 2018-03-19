package optional;

import org.junit.Test;

import java.util.*;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class Example1 {

    @Test
    public void ifPresent() {
        Optional<String> optional = Optional.of("string");

        List<String> result = new ArrayList<>();
        optional.ifPresent(result::add);

        if (optional.isPresent()) {
            result.add(optional.get());
        }

        assertEquals(Collections.singletonList("string"), result);
    }

    @SuppressWarnings("unused")
    public void orElse() {
        Optional<String> optional = Optional.empty();

        String result = optional.orElse("string");

        assertEquals("string", result);
    }

    @SuppressWarnings("unused")
    @Test(expected = IllegalArgumentException.class)
    public void orElseThrow() {
        Optional<String> optional = Optional.empty();

        String ignored = optional.orElseThrow(IllegalArgumentException::new);
    }

    @Test
    public void mapAndThenIfPresent() {
        Optional<String> optional = Optional.of("42");

        List<Integer> result = new ArrayList<>();
        optional.map(Integer::valueOf)
                .ifPresent(result::add);

        assertEquals(Collections.singletonList(42), result);
    }

    @Test
    public void mapFilterOrElse() {
        LinkedList<String> data = new LinkedList<>(Arrays.asList("qwe", "10", "30"));
        Optional<Deque<String>> optional = Optional.of(data);

        Predicate<String> isDigit = Pattern.compile("\\d+").asPredicate();
        Optional<Integer> result = optional.map(Deque::getFirst)
                                           .filter(isDigit)
                                           .map(Integer::valueOf);

        assertFalse(result.isPresent());
    }

    @Test
    public void flatMapOrElseGet() {
        Optional<String> optional = Optional.of("value");

        Integer result = optional.flatMap(Example1::convertStringToInteger)
                                 .orElseGet(Example1::hugeOperation);

        assertEquals(42, result.intValue());
    }

    // String -> Optional<Integer>
    // TODO replace with filter
    private static Optional<Integer> convertStringToInteger(String stringValue) {
        return Optional.ofNullable(stringValue)
                       .filter(Pattern.compile("\\d+").asPredicate())
                       .map(Integer::valueOf);
    }

    private static Integer hugeOperation() {
        try {
            TimeUnit.MILLISECONDS.sleep(300);
            return 42;
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void primitiveOptionals() {
        OptionalInt optional = OptionalInt.of(42);

        int result = optional.orElse(0);

        assertEquals(42, result);
    }
}
