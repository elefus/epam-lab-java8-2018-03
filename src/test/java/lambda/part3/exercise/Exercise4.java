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

        private final List<T> source;
        private final Function<T, List<R>> function;

        public LazyCollectionHelper(List<T> source, Function<T, List<R>> function) {
            this.source = source;
            this.function = function;
        }

        public static <T> LazyCollectionHelper<T, T> from(List<T> list) {
            return new LazyCollectionHelper<>(list, Collections::singletonList);
        }

        public <U> LazyCollectionHelper<T, U> flatMap(Function<R, List<U>> flatMapping) {
            return new LazyCollectionHelper<>(source, function.andThen(getListListFunction(flatMapping)));
        }

        private <U> Function<List<R>, List<U>> getListListFunction(Function<R, List<U>> flatMapping) {
            return rs -> {
                List<U> result = new ArrayList<>();
                for (R r : rs) {
                    result.addAll(flatMapping.apply(r));
                }
                return result;
            };
        }

        public <U> LazyCollectionHelper<T, U> map(Function<R, U> mapping) {
            return new LazyCollectionHelper<>(source,
                    function.andThen(getListListFunction(mapping.andThen(Collections::singletonList))));
        }

        public List<R> force() {
            List<R> result = new ArrayList<>();
            for (T t : source) {
                result.addAll(function.apply(t));
            }
            return result;
        }
    }

    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        Function<String, List<Character>> strToCharList = s -> {
            List<Character> chars = new ArrayList<>();
            for (char c : s.toCharArray()) {
                chars.add(c);
            }
            return chars;
        };

        List<Integer> codes = LazyCollectionHelper.from(employees)
                                                  .flatMap(Employee::getJobHistory)
                                                  .map(JobHistoryEntry::getPosition)
                                                  .flatMap(s -> {
                                                      List<Character> chars = new ArrayList<>();
                                                      for (char c : s.toCharArray()) {
                                                          chars.add(c);
                                                      }
                                                      return chars;
                                                  })
                                                  .map(ch -> (int) ch)
                                                  .force();
        assertEquals(calcCodes("dev", "dev", "tester", "dev", "dev", "QA", "QA", "dev", "tester", "tester", "QA", "QA", "QA", "dev"), codes);
    }

    private static List<Integer> calcCodes(String... strings) {
        List<Integer> codes = new ArrayList<>();
        for (String string : strings) {
            for (char letter : string.toCharArray()) {
                codes.add((int) letter);
            }
        }
        return codes;
    }
}
