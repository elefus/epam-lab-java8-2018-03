package streams.part2.example;

import lambda.data.Employee;
import lambda.data.Person;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Example2 {

    @Test
    public void toListCollector() {
        Integer[] source = {1, 2, 3, 4, 5};

        List<Integer> result = Arrays.stream(source)
                                     .limit(2)
                                     .collect(Collectors.toList());

        assertEquals(Arrays.asList(1, 2), result);
    }

    @Test
    public void toSetCollector() {
        Set<Integer> result = Stream.of(1, 1, 1, 1)
                                    .collect(Collectors.toSet());

        assertEquals(Collections.singleton(1), result);
    }

    @Test
    public void toHashMapKeyValueCollector() {
        Map<Integer, String> result = Stream.of(1, 2, 3)
                                            .collect(Collectors.toMap(Function.identity(), Object::toString));

        Map<Integer, String> expected = new HashMap<>();
        expected.put(1, "1");
        expected.put(2, "2");
        expected.put(3, "3");
        assertEquals(expected, result);
    }

    @Test(expected = IllegalStateException.class)
    public void toHashMapKeyValueCollectorFailsWhenExistsSameKeys() {
        Stream.of(1, 1, 2, 3).collect(Collectors.toMap(Function.identity(), Object::toString));
    }

    @Test
    public void toHashMapKeyValueMergeCollector() {
        Map<Integer, String> result = Stream.of(1, 1, 2, 3, 4)
                                            .collect(Collectors.toMap(Function.identity(),
                                                                      Object::toString,
                                                                      String::concat));

        Map<Integer, String> expected = new HashMap<>();
        expected.put(1, "11");
        expected.put(2, "2");
        expected.put(3, "3");
        expected.put(4, "4");
        assertEquals(expected, result);
    }

    @Test
    public void toTreeMapKeyValueMergeCollector() {
        TreeMap<Integer, String> result = Stream.of(1, 1, 2, 3, 4)
                                                .collect(Collectors.toMap(Function.identity(),
                                                                          Object::toString,
                                                                          (oldValue, newValue) -> oldValue + newValue,
                                                                          TreeMap::new));

        Map<Integer, String> expected = new HashMap<>();
        expected.put(1, "11");
        expected.put(2, "2");
        expected.put(3, "3");
        expected.put(4, "4");
        assertEquals(expected, result);
    }

    @Test
    @SuppressWarnings("SimplifyStreamApiCallChains")
    public void countingCollector() {
        long countUsingCollector = Stream.of(1, 1, 2, 3, 4).collect(Collectors.counting());
        long countUsingMethod = Stream.of(1, 1, 2, 3, 4).count();

        assertEquals(5, countUsingCollector);
        assertEquals(countUsingCollector, countUsingMethod);
    }

    @Test
    @SuppressWarnings("SimplifyStreamApiCallChains")
    // TODO summingDoubleCollector
    // TODO summingLongCollector
    public void summingIntCollector() {
        Integer sumUsingCollector = lambda.part3.example.Example1.getEmployees()
                                                                 .stream()
                                                                 .map(Employee::getPerson)
                                                                 .collect(Collectors.summingInt(Person::getAge));

        int sumUsingIntStream = lambda.part3.example.Example1.getEmployees()
                                                             .stream()
                                                             .map(Employee::getPerson)
                                                             .mapToInt(Person::getAge)
                                                             .sum();
        assertEquals(202, sumUsingIntStream);
        assertEquals(sumUsingIntStream, sumUsingCollector.intValue());
    }

    @Test
    @SuppressWarnings("SimplifyStreamApiCallChains")
    // TODO averagingDoubleCollector
    // TODO averagingLongCollector
    public void averagingIntCollector() {
        Double averageUsingCollector = lambda.part3.example.Example1.getEmployees()
                                                                    .stream()
                                                                    .map(Employee::getPerson)
                                                                    .collect(Collectors.averagingInt(Person::getAge));

        OptionalDouble averageUsingIntStream = lambda.part3.example.Example1.getEmployees()
                                                                            .stream()
                                                                            .map(Employee::getPerson)
                                                                            .mapToInt(Person::getAge)
                                                                            .average();
        assertEquals(33.6666, averageUsingCollector, 0.0001);
        assertEquals(averageUsingCollector, averageUsingIntStream.orElseThrow(IllegalStateException::new), 0.001);
    }

    @Test
    @SuppressWarnings("SimplifyStreamApiCallChains")
    public void maxByCollector() {
        Optional<Integer> maxUsingCollector = lambda.part3.example.Example1.getEmployees()
                                                                           .stream()
                                                                           .map(Employee::getPerson)
                                                                           .map(Person::getAge)
                                                                           .collect(Collectors.maxBy(Integer::compare));

        OptionalInt maxUsingIntStream = lambda.part3.example.Example1.getEmployees()
                                                                     .stream()
                                                                     .map(Employee::getPerson)
                                                                     .mapToInt(Person::getAge)
                                                                     .max();

        assertEquals(50, maxUsingCollector.orElseThrow(IllegalStateException::new).intValue());
        assertEquals(maxUsingCollector.get().intValue(), maxUsingIntStream.orElseThrow(IllegalStateException::new));
    }

    @Test
    @SuppressWarnings("SimplifyStreamApiCallChains")
    public void minByCollector() {
        Optional<Integer> maxUsingCollector = lambda.part3.example.Example1.getEmployees()
                                                                           .stream()
                                                                           .map(Employee::getPerson)
                                                                           .map(Person::getAge)
                                                                           .collect(Collectors.minBy(Integer::compare));

        OptionalInt maxUsingIntStream = lambda.part3.example.Example1.getEmployees()
                                                                     .stream()
                                                                     .map(Employee::getPerson)
                                                                     .mapToInt(Person::getAge)
                                                                     .min();

        assertEquals(21, maxUsingCollector.orElseThrow(IllegalStateException::new).intValue());
        assertEquals(maxUsingCollector.get().intValue(), maxUsingIntStream.orElseThrow(IllegalStateException::new));
    }

    @Test
    // TODO summarizingDoubleCollector
    // TODO summarizingLongCollector
    public void summarizingIntCollector() {
        IntSummaryStatistics ageStatisticsUsingCollector = lambda.part3.example.Example1.getEmployees()
                                                                                        .stream()
                                                                                        .map(Employee::getPerson)
                                                                                        .collect(Collectors.summarizingInt(Person::getAge));

        IntSummaryStatistics ageStatisticsUsingIntStream = lambda.part3.example.Example1.getEmployees()
                                                                                        .stream()
                                                                                        .map(Employee::getPerson)
                                                                                        .mapToInt(Person::getAge)
                                                                                        .summaryStatistics();

        assertEquals(6, ageStatisticsUsingCollector.getCount());
        assertEquals(50, ageStatisticsUsingCollector.getMax());
        assertEquals(21, ageStatisticsUsingCollector.getMin());
        assertEquals(202, ageStatisticsUsingCollector.getSum());
        assertEquals(33.66666, ageStatisticsUsingCollector.getAverage(), 0.0001);
        assertEquals(ageStatisticsUsingCollector.getCount(), ageStatisticsUsingIntStream.getCount());
    }

    @Test
    public void joiningCollector() {
        String result = Stream.of("a", "b", "c", "d")
                              .collect(Collectors.joining());

        assertEquals("abcd", result);
    }

    @Test
    public void joiningWithDelimiterCollector() {
        String result = Stream.of("a", "b", "c", "d")
                              .collect(Collectors.joining("~"));

        assertEquals("a~b~c~d", result);
    }

    @Test
    public void joiningWithDelimiterAndBordersCollector() {
        String result = Stream.of("a", "b", "c", "d")
                              .collect(Collectors.joining("~", "[", "]"));

        assertEquals("[a~b~c~d]", result);
    }

    @Test
    public void groupingByCollector() {
        Map<String, List<Person>> nameToPersons = lambda.part3.example.Example1.getEmployees()
                                                                               .stream()
                                                                               .map(Employee::getPerson)
                                                                               .collect(Collectors.groupingBy(Person::getFirstName));

        assertEquals(2, nameToPersons.get("Иван").size());
        assertFalse(nameToPersons.containsKey("Алексей"));

        Map<Boolean, List<Person>> moreThan40Years = lambda.part3.example.Example1.getEmployees()
                                                                                  .stream()
                                                                                  .map(Employee::getPerson)
                                                                                  .collect(Collectors.groupingBy(person -> person.getAge() > 10));

        assertEquals(6, moreThan40Years.get(true).size());
        assertEquals(0, moreThan40Years.get(false).size());
    }

    @Test
    public void partitionByCollector() {
        Map<Boolean, List<Person>> moreThan40Years = lambda.part3.example.Example1.getEmployees()
                                                                                  .stream()
                                                                                  .map(Employee::getPerson)
                                                                                  .collect(Collectors.partitioningBy(person -> person.getAge() > 10));

        assertEquals(6, moreThan40Years.get(true).size());
        assertEquals(0, moreThan40Years.get(false).size());
    }

    @Test
    public void groupingByWithDownstreamCollector() {
        Map<String, Set<Person>> nameToPersons = lambda.part3.example.Example1.getEmployees()
                                                                              .stream()
                                                                              .map(Employee::getPerson)
                                                                              .collect(Collectors.groupingBy(Person::getFirstName, Collectors.toSet()));

        assertEquals(2, nameToPersons.get("Иван").size());
        assertFalse(nameToPersons.containsKey("Алексей"));
    }

    @Test
    public void groupingByWithMapFactoryAndDownstreamCollector() {
        TreeMap<String, Set<Person>> nameToPersons = lambda.part3.example.Example1.getEmployees()
                                                                                  .stream()
                                                                                  .map(Employee::getPerson)
                                                                                  .collect(Collectors.groupingBy(Person::getFirstName,
                                                                                          () -> new TreeMap<>(Comparator.comparingInt(String::length)),
                                                                                          Collectors.toSet()));

        assertEquals(2, nameToPersons.get("Иван").size());
        assertFalse(nameToPersons.containsKey("Алексей"));
    }

    @Test
    public void collectingAndThenCollector() {
        Long negatedSize = lambda.part3.example.Example1.getEmployees()
                                                        .stream()
                                                        .map(Employee::getPerson)
                                                        .collect(Collectors.collectingAndThen(Collectors.counting(), size -> -size));

        assertEquals(-6, negatedSize.longValue());
    }

    @Test
    @SuppressWarnings("SimplifyStreamApiCallChains")
    public void mappingCollector() {
        Set<String> names = lambda.part3.example.Example1.getEmployees()
                                                         .stream()
                                                         .map(Employee::getPerson)
                                                         .collect(Collectors.mapping(Person::getFirstName, Collectors.toSet()));

        assertEquals(5, names.size());
    }
}
