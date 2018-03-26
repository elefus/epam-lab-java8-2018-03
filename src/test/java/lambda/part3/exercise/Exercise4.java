package lambda.part3.exercise;

import com.sun.org.apache.bcel.internal.generic.VariableLengthInstruction;
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

        private LazyCollectionHelper(List<T> source, Function<T, List<R>> function) {
            this.source = source;
            this.function = function;
        }


        public static <T> LazyCollectionHelper<T, T> from(List<T> list) {

            return new LazyCollectionHelper<>(list, Collections::singletonList);
        }

        private <TO, FROM> Function<List<FROM>, List<TO>> tranformWith(Function<FROM, List<TO>> transform){
            return value -> {
                ArrayList<TO> result = new ArrayList<>();
                value.forEach(transform.andThen(result::addAll)::apply);
                return result;
           };
        }

        public <U> LazyCollectionHelper<T, U> flatMap(Function<R, List<U>> flatMapping) {
//            return new LazyCollectionHelper<>(source, function.andThen(value ->{
//                ArrayList<U> result = new ArrayList<>();
//                value.forEach(flatMapping.andThen(result::addAll)::apply);
//                return result;
//            }));
            return new LazyCollectionHelper<>(source, function.andThen(tranformWith(flatMapping)));
        }

        public <U> LazyCollectionHelper<T, U> map(Function<R, U> mapping) {
//            return new LazyCollectionHelper<>(source, function.andThen(value ->{
//                ArrayList<U> result = new ArrayList<>();
//                value.forEach(mapping.andThen(result::add)::apply);
//                return result;
//            }));
            return new LazyCollectionHelper<>(source, function.andThen(tranformWith(mapping.andThen(Collections::singletonList))));

        }

        public List<R> force() {
            ArrayList<R> result = new ArrayList<>();
            source.forEach(function.andThen(result::addAll)::apply);
            return result;
        }
    }

    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> codes = LazyCollectionHelper.from(employees)
                                          .flatMap(Employee::getJobHistory)
                                          .map(JobHistoryEntry::getPosition)
                                          .flatMap(Exercise4::calcCodes)
//                                          .map(Character -> Integer(code letter)
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
