package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise3 {

    private static class LazyMapHelper<T, R> {

        public static <T> LazyMapHelper<T, T> from(List<T> list) {
            // TODO реализация
            throw new UnsupportedOperationException();
        }

        public List<R> force() {
            // TODO реализация
            throw new UnsupportedOperationException();
        }

        public <R2> LazyMapHelper<T, R2> map(Function<R, R2> mapping) {
            // TODO реализация
            throw new UnsupportedOperationException();
        }
    }

    @Test
    public void mapEmployeesToLengthOfTheirFullNamesUsingLazyMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> lengths = null;
        // TODO                 LazyMapHelper.from(employees)
        // TODO                              .map(Employee -> Person)
        // TODO                              .map(Person -> String(full name))
        // TODO                              .map(String -> Integer(length from string))
        // TODO                              .getMapped();
        assertEquals(Arrays.asList(14, 19, 14, 15, 14, 16), lengths);
    }
}
