package api.example;

import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"Java8ListSort", "ForLoopReplaceableByForEach"})
public class Example3 {

    @Test
    public void removeIfUsingJava7() {
        List<Integer> values = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));

        for (Iterator<Integer> iterator = values.iterator(); iterator.hasNext(); ) {
            Integer value = iterator.next();
            if (value < 3) {
                iterator.remove();
            }
        }

        assertEquals(Arrays.asList(3, 4, 5, 6), values);
    }

    @Test
    public void removeIfUsingJava8() {
        Collection<Integer> values = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6));

        values.removeIf(value -> value < 3);

        assertEquals(Arrays.asList(3, 4, 5, 6), values);
    }

    @Test
    public void replaceAllUsingJava7() {
        List<Integer> values = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6));

        ListIterator<Integer> iterator = values.listIterator();
        while (iterator.hasNext()) {
            iterator.set(iterator.next() + 1);
        }

        assertEquals(Arrays.asList(2, 3, 4, 5, 6, 7), values);
    }

    @Test
    public void replaceAllUsingJava8() {
        List<Integer> values = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6));

        values.replaceAll(value -> value + 1);

        assertEquals(Arrays.asList(2, 3, 4, 5, 6, 7), values);
    }

    @Test
    public void sortUsingJava7() {
        List<String> values = new ArrayList<>(Arrays.asList("1", "333", "22", "4444"));

        Collections.sort(values, Comparator.comparingInt(String::length));

        assertEquals(Arrays.asList("1", "22", "333", "4444"), values);
    }

    @Test
    public void sortUsingJava8() {
        List<String> values = new ArrayList<>(Arrays.asList("1", "333", "22", "4444"));

        values.sort(Comparator.comparingInt(String::length));

        assertEquals(Arrays.asList("1", "22", "333", "4444"), values);
    }
}
