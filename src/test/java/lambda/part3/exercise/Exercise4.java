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

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise4 {

    private static class LazyCollectionHelper<T, R> {

        private final List<T> source;
        private final Function<T, List<R>> mappingFunction;

        public LazyCollectionHelper(List<T> source, Function<T, List<R>> mappingFunction) {
            this.source = source;
            this.mappingFunction = mappingFunction;
        }

        public static <T> LazyCollectionHelper<T, T> from(List<T> list) {
            return new LazyCollectionHelper<>(list, Collections::singletonList);
        }

        public <U> LazyCollectionHelper<T, U> flatMap(Function<R, List<U>> flatMapping) {
            return new LazyCollectionHelper<>(source, mappingFunction.andThen(getListToListMappingFunction(flatMapping)));
        }

        public <U> LazyCollectionHelper<T, U> map(Function<R, U> mapping) {
            return new LazyCollectionHelper<>(source, mappingFunction.andThen(getListToListMappingFunction(mapping.andThen(Collections::singletonList))));
        }

        public List<R> force() {
            return getListToListMappingFunction(mappingFunction).apply(source);
        }

        private static <T1, T2> Function<List<T1>, List<T2>> getListToListMappingFunction(Function<T1, List<T2>> mapping) {
            return list -> {
                List<T2> result = new ArrayList<>();
                list.forEach(mapping.andThen(result::addAll)::apply);
                return result;
            };
        }
    }

    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> codes = LazyCollectionHelper.from(employees)
                .flatMap(Employee::getJobHistory)
                .map(JobHistoryEntry::getPosition)
                .flatMap(o -> o.chars().mapToObj(i -> (char) i).collect(Collectors.toList()))
                .map(character -> (int) character)
                .force();

        assertEquals(calcCodes("dev", "dev", "tester", "dev", "dev", "QA", "QA", "dev", "tester", "tester", "QA", "QA", "QA", "dev"), codes);
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
