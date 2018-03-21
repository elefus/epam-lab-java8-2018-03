package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise4 {

    private static class LazyCollectionHelper<T, R> {
        private List<T> source;
        private Function<T, List<R>> mapPipeLine;

        private LazyCollectionHelper(List<T> source, Function<T, List<R>> mapPipeLine){
            this.source = source;
            this.mapPipeLine = mapPipeLine;
        }

        public static <T> LazyCollectionHelper<T, T> from(List<T> list) {
            return new LazyCollectionHelper<>(list, Collections::singletonList);
        }

        public <U> LazyCollectionHelper<T, U> flatMap(Function<R, List<U>> flatMapping) {
            return new LazyCollectionHelper<>(source, (t)-> {
                List<U> res = new ArrayList<>();
                for (R r : mapPipeLine.apply(t)) res.addAll(flatMapping.apply(r));
                return res;
                });
        }

        public <U> LazyCollectionHelper<T, U> map(Function<R, U> mapping) {
            return new LazyCollectionHelper<>(source, (T t) ->{
                List<U> res = new ArrayList<>();
                for(R r : mapPipeLine.apply(t)) res.add(mapping.apply(r));
                return res;
            });
        }

        public List<R> force() {
            List<R> result = new ArrayList<>();
            for (T t : source) result.addAll(mapPipeLine.apply(t));
            return result;
        }
    }

    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> codes = LazyCollectionHelper.from(employees)
                .flatMap(Employee::getJobHistory)
                .map(JobHistoryEntry::getPosition)
                .flatMap(s -> {
                    List<Character> result = new ArrayList<>();
                    for (char a: s.toCharArray()) result.add(a);
                    return result; })
                .map(Integer::valueOf)
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
