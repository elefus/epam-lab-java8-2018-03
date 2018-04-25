package spliterators.exercise.exercise1;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
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
     * @param count Количество наиболее часто встречающихся слов, которые необходимо отобрать.
     * @param words Анализируемые слова.
     * @return Список отобранных слов (в нижнем регистре).
     */
    private List<String> getMostPopularWords(int count, String... words) {
        return Arrays.stream(words)
                .map(String::toLowerCase)
                .collect(groupingBy(Function.identity(), counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue()
                        .reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(count)
                .map(Map.Entry::getKey)
                .collect(toList());
    }
}
