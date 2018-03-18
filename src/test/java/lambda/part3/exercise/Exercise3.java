package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise3 {

    private static class LazyMapHelper<T1, T2> {

        private final List<T1> source;
        private final Function<T1, T2> mappingFunction;

        public LazyMapHelper(List<T1> source, Function<T1, T2> mappingFunction) {
            this.source = source;
            this.mappingFunction = mappingFunction;
        }

        public static <T> LazyMapHelper<T, T> from(List<T> list) {
            return new LazyMapHelper<>(list, o -> o);
        }

        public List<T2> force() {
            return source.stream().map(mappingFunction).collect(Collectors.toList());
        }

        public <T3> LazyMapHelper<T1, T3> map(Function<T2, T3> mapping) {
            return new LazyMapHelper<>(source, mappingFunction.andThen(mapping));
        }
    }

    @Test
    public void mapEmployeesToLengthOfTheirFullNamesUsingLazyMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> lengths = LazyMapHelper.from(employees)
                .map(Employee::getPerson)
                .map(Person::getFullName)
                .map(String::length)
                .force();

        assertEquals(Arrays.asList(14, 19, 14, 15, 14, 16), lengths);
    }
}
