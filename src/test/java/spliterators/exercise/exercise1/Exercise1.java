package spliterators.exercise.exercise1;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     * На вход подается текст, состоящий из слов 1_000 < N < 1_000_000, разделенных пробелами. Слова могут содержать
     * буквы английского алфавита в различном регистре. Необходимо осуществить подсчет количества вхождений каждого
     * слова без учета регистра и выбрать среди них 5 < R < 15 наиболее часто встречающихся, приведенных к нижнему
     * регистру. Если встретятся слова с совпадающей частотой - в результирующем списке они должны быть отсортированы
     * в лексикографическом порядке.
     * При реализации подумайте над возможностью распараллеливания решения а также над способами измерения
     * эффективности предложенного решения на разных наборах входных данных.
     */
    private List<java.lang.String> getMostPopularWords(int count, java.lang.String... words) {

        class MyStringMapComparator implements Comparator<Object> {

            @Override
            public int compare(Object entrySet1, Object entrySet2) {
                java.util.Map.Entry entry1 = (java.util.Map.Entry) entrySet1;
                java.util.Map.Entry entry2 = (java.util.Map.Entry) entrySet2;
                Long valu1 = (Long) entry1.getValue();
                Long value2 = (Long) entry2.getValue();
                java.lang.String key1 = (java.lang.String) entry1.getKey();
                java.lang.String key2 = (java.lang.String) entry2.getKey();

                return Long.compare(valu1, value2) == 0 ? key1.toLowerCase().compareTo(key2.toLowerCase())
                                                        : (valu1 > value2 ? -1 : 1);
            }
        }

        return Arrays.stream(Arrays.toString(words).replaceAll(",", "").split(" "))
                     .collect(Collectors.groupingBy(String::toString, Collectors.counting()))
                     .entrySet()
                     .stream()
                     .sorted(new MyStringMapComparator())
                     .limit(count)
                     .map(Map.Entry::getKey)
                     .collect(Collectors.toList());
    }
}
