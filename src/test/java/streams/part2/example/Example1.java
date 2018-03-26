package streams.part2.example;

import lambda.data.Employee;
import lambda.data.Person;
import org.junit.Test;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class Example1 {

    @Test
    public void combineFullNamesOfFirstThreePersonsToStringUsingJava7() {
        List<Employee> source = lambda.part3.example.Example1.getEmployees().subList(0, 3);
        StringBuilder builder = new StringBuilder(source.size() * 15);

        for (Employee employee : source) {
            builder.append(employee.getPerson().getFullName())
                   .append(", ");
        }
        builder.setLength(builder.length() - 2);

        assertEquals("Иван Мельников, Александр Дементьев, Дмитрий Осинов", builder.toString());
    }

    @Test
    public void combineFullNamesOfFirstThreePersonsToStringUsingStringJoin() {
        List<Employee> source = lambda.part3.example.Example1.getEmployees().subList(0, 3);

        List<String> fullNames = new ArrayList<>(source.size());
        for (Employee employee : source) {
            fullNames.add(employee.getPerson().getFullName());
        }
        String result = String.join(", ", fullNames);

        assertEquals("Иван Мельников, Александр Дементьев, Дмитрий Осинов", result);
    }

    // identity: 0
    // accumulator: Integer::sum
    // [1 2 3 4 5 6 7 8 9]
    // [1 2] [3 4] [5 6] [7 8] [9]
    // 3     7     11    15    9
    // 45
    @Test
    public void reducePersonsToStringUsingStringConcatenation() {
        Stream<Employee> source = lambda.part3.example.Example1.getEmployees().parallelStream();

        String result = source.limit(3)
                              .map(Employee::getPerson)
                              .map(Person::getFullName)
                              // Optional<T> reduce(BinaryOperator<T> accumulator)
                              //          T  reduce(T identity, BinaryOperator<T> accumulator)
                              //      <U> U  reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner)
                              //
                              // javadoc: not constrained to execute sequentially
                              // identity: neutral element
                              // accumulator: associative operation
                              .reduce((part, element) -> part + ", " + element).get();

        assertEquals("Иван Мельников, Александр Дементьев, Дмитрий Осинов", result);
    }

    // supplier: () -> 0
    // accumulator: Integer::sum
    // [1 2 3 4 5 6 7 8 9]
    // [1 2] [3 4] [5 6] [7 8] [9]
    // 3     7     11    15    9
    // 45
    @Test
    public void collectPersonsToStringUsingStringBuilder() {
        Stream<Employee> source = lambda.part3.example.Example1.getEmployees().parallelStream();

        StringBuilder result = source.limit(3)
                              .map(Employee::getPerson)
                              .map(Person::getFullName)
                              .collect(StringBuilder::new,
                                       (builder, name) -> builder.append(name).append(", "),
                                       StringBuilder::append);
//                                       (left, right) -> new StringBuilder(left).append(right));

        assertEquals("Иван Мельников, Александр Дементьев, Дмитрий Осинов", result.toString());
    }

    @Test
    public void simpleStringJoinerWithDelimiter() {
        StringJoiner joiner = new StringJoiner("-");

        joiner.add("1").add("2").add("3");

        assertEquals("1-2-3", joiner.toString());
    }

    @Test
    public void stringJoinerWithPrefixDelimiterAndPostfix() {
        StringJoiner joiner = new StringJoiner("-", "[", "]");

        joiner.add("1").add("2").add("3");

        assertEquals("[1-2-3]", joiner.toString());
    }

    @Test
    public void mergeStringJoiners() {
        StringJoiner left = new StringJoiner("-", "[", "]");
        StringJoiner right = new StringJoiner("-", "[", "]");

        left.add("1").add("2");
        right.add("3").add("4");

        left.merge(right);

        StringJoiner last = new StringJoiner("-", "[", "]");
        left.merge(last);

        assertEquals("[1-2-3-4]", left.toString());
    }


    @Test
    public void collectPersonToStringUsingLambdasStringJoiner() {
        String result = IntStream.range(0, 5)
                                 .parallel()
                                 .mapToObj(String::valueOf)
                                 .collect(() -> new StringJoiner(", "), StringJoiner::add, StringJoiner::merge)
                                 .toString();

        assertEquals("0, 1, 2, 3, 4", result);
    }

    private static class IntCommaStringJoiner implements Collector<Integer, StringJoiner, String> {

        @Override
        public Supplier<StringJoiner> supplier() {
            return () -> new StringJoiner(", ");
        }

        @Override
        public BiConsumer<StringJoiner, Integer> accumulator() {
            return (stringJoiner, integer) -> stringJoiner.add(String.valueOf(integer));
        }

        @Override
        public BinaryOperator<StringJoiner> combiner() {
            return StringJoiner::merge;
        }

        @Override
        public Function<StringJoiner, String> finisher() {
            return StringJoiner::toString;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return EnumSet.noneOf(Characteristics.class);
        }
    }

    @Test
    public void collectPersonToStringUsingCustomStringJoiner() {
        String result = IntStream.range(0, 5)
                                 .boxed()
                                 .collect(new IntCommaStringJoiner());

        assertEquals("0, 1, 2, 3, 4", result);
    }

    @Test
    public void collectPersonToStringUsingJoiningCollector() {
        String result = IntStream.range(0, 5)
                                 .boxed()
                                 .map(String::valueOf)
                                 .collect(Collectors.joining(", "));

        assertEquals("0, 1, 2, 3, 4", result);
    }

    @Test
    public void collectPersonToStringUsingJoiningCollectorWithPrefixAndPostfix() {
        Stream<Employee> source = lambda.part3.example.Example1.getEmployees().stream();

        String result = source.limit(3)
                              .map(Employee::getPerson)
                              .map(Person::getFullName)
                              .collect(Collectors.joining(", ", "[", "]"));

        assertEquals("[Иван Мельников, Александр Дементьев, Дмитрий Осинов]", result);
    }
}
