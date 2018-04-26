package spliterators.exercise.exercise1;

import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class Exercise1 {

    @Test
    public void test() {
        List<String> result = getMostPopularWords(10, "Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit",
                "Sed", "sodales", "consectetur", "purus", "at", "faucibus", "Donec", "mi", "quam", "tempor", "vel", "ipsum",
                "non", "faucibus", "suscipit", "massa", "Morbi", "lacinia", "velit", "blandit", "tincidunt", "efficitur",
                "Vestibulum", "eget", "metus", "imperdiet", "sapien", "laoreet", "faucibus", "Nunc", "eget", "vehicula",
                "mauris", "ac", "auctor", "lorem", "Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing",
                "elit", "Integer", "vel", "odio", "nec", "mi", "tempor", "dignissim");

        assertEquals(Arrays.asList("consectetur", "faucibus", "ipsum", "lorem", "adipiscing", "amet", "dolor", "eget", "elit", "mi"), result);
    }

    /**
     * Выбирает из текста наиболее часто встречающиеся слова.
     * Подсчет слов выполняется без учета их регистра, т.е. "Привет", "привет", "пРиВеТ" - одно и то же слово.
     * Если некоторые слова имеют одинаковую частоту, то в выходном списке они упорядочиваются в лексикографическом порядке.
     *
     * @param count Количество наиболее часто встречающихся слов, которые необходимо отобрать.
     * @param words Анализируемые слова.
     * @return Список отобранных слов (в нижнем регистре).
     */
    private List<String> getMostPopularWords(int count, String... words) {
        return Stream.of(words)
                     .parallel()
                     .map(String::toLowerCase)
                     .collect(new ToMapCollector())
                     .entrySet()
                     .stream()
                     .sorted(Map.Entry.<String, Integer>comparingByValue()
                             .reversed()
                             .thenComparing(Map.Entry.comparingByKey()))
                     .limit(count)
                     .map(Map.Entry::getKey)
                     .collect(Collectors.toList());

    }


}


class ToMapCollector implements Collector<String, HashMap<String, Integer>, HashMap<String, Integer>> {

    @Override
    public Supplier<HashMap<String, Integer>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<HashMap<String, Integer>, String> accumulator() {
        return (values, value) -> {
            if (values.get(value) == null) {
                values.put(value, 1);
            } else {
                int v = values.get(value);
                values.put(value, ++v);
            }
        };
    }

    @Override
    public BinaryOperator<HashMap<String, Integer>> combiner() {
        return (left, right) -> {
            for (String s : left.keySet()) {
                if (right.get(s) != null) {
                    int total = right.get(s) + left.get(s);
                    left.replace(s, total);
                    right.remove(s);
                }
            }
            left.putAll(right);
            return left;
        };
    }

    @Override
    public Function<HashMap<String, Integer>, HashMap<String, Integer>> finisher() {
        return values -> {
            throw new UnsupportedOperationException();
        };

    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.UNORDERED);
    }
}
