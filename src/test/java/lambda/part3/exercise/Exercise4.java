package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.*;
import static java.util.stream.Collectors.*;
import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise4 {

    private static class LazyCollectionHelper<T, R> {

        private final List<T> source;
        private final Function<T,List<R>> function;

        public LazyCollectionHelper(List<T> source, Function<T, List<R>> function) {
            this.source = source;
            this.function = function;
        }

        public static <T> LazyCollectionHelper<T, T> from(List<T> list) {
            return new LazyCollectionHelper<>(list, Collections::singletonList);
        }

        public <U> LazyCollectionHelper<T, U> flatMap(Function<R, List<U>> flatMapping) {
            return  new LazyCollectionHelper<>(source, function.andThen(transform(flatMapping)));
        }

        public <U> LazyCollectionHelper<T, U> map(Function<R, U> mapping) {
            return new LazyCollectionHelper<>(source, function.andThen(transform(mapping.andThen(Collections::singletonList))));
        }

        public List<R> force() {
            return transform(function).apply(source);
        }
    }

    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {
        List<Employee> employees = Example1.getEmployees();
        Function<String, List<Character>> strToCharList = str -> {
            List<Character> characters = new ArrayList<>();
            for (char s: str.toCharArray()) {
                characters.add(s);
            }
            return characters;
        };

        List<Integer> codes = LazyCollectionHelper.from(employees)
                                          .flatMap(Employee::getJobHistory)
                                          .map(JobHistoryEntry::getPosition)
                                          .flatMap(strToCharList)
                                          .map(c -> (int) c)
                                          .force();
        assertEquals(calcCodes("dev", "dev", "tester", "dev", "dev", "QA", "QA", "dev", "tester", "tester", "QA", "QA", "QA", "dev"), codes);
    }

    private static <FROM, TO> Function<List<FROM>, List<TO>> transform(Function<FROM, List<TO>> mapper) {
        return  source -> {
            List<TO> list = new ArrayList<>();
            source.forEach(mapper.andThen(list::addAll)::apply);
            return list;
        };
    }


        private static List<Integer> calcCodes(String...strings) {
        List<Integer> codes = new ArrayList<>();
        for (String string : strings) {
            for (char letter : string.toCharArray()) {
                codes.add((int) letter);
            }
        }
        return codes;
    }
}
