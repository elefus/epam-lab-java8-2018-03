package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class Exercise3 {

    private static class LazyMapHelper<T, R> {
        private List<T> tList;
        private Function<T, R> tToR;

        public LazyMapHelper(List<T> tList, Function<T, R> tToR) {
            this.tList = tList;
            this.tToR = tToR;
        }

        public static <T> LazyMapHelper<T, T> from(List<T> list) {
            // TODO реализация
//            throw new UnsupportedOperationException();
            return new LazyMapHelper<>(list, t -> t);
        }

        public List<R> force() {
            // TODO реализация
//            throw new UnsupportedOperationException();
            List<R> list = new ArrayList<>();
            for (T t : tList) {
                R apply = tToR.apply(t);
                list.add(apply);
            }
            return list;
        }

        public <R2> LazyMapHelper<T, R2> map(Function<R, R2> mapping) {
            // TODO реализация
//            throw new UnsupportedOperationException();
            return new LazyMapHelper<>(tList, mapping.compose(tToR));
        }
    }

    @Test
    public void mapEmployeesToLengthOfTheirFullNamesUsingLazyMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List
                lengths =
                LazyMapHelper.from(employees)
                             .map(Employee::getPerson)
                             .map(Person::getFullName)
                             .map(String::length)
                             .force();
        // TODO                 LazyMapHelper.from(employees)
        // TODO                              .map(Employee -> Person)
        // TODO                              .map(Person -> String(full name))
        // TODO                              .map(String -> Integer(length from string))
        // TODO                              .getMapped();

        assertEquals(Arrays.asList(14, 19, 14, 15, 14, 16), lengths);
    }
}
