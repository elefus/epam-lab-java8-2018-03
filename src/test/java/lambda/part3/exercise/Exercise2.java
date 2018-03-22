package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "WeakerAccess", "ConstantConditions"})
public class Exercise2 {

    private static class MapHelper<T> {

        private final List<T> source;

        private MapHelper(List<T> source) {
            this.source = source;
        }

        public static <T> MapHelper<T> from(List<T> source) {
            return new MapHelper<>(source);
        }

        public List<T> getMapped() {
            return new ArrayList<>(source);
        }

        /**
         * Создает объект для маппинга, передавая ему новый список, построенный на основе исходного.
         * Для добавления в новый список каждый элемент преобразовывается с использованием заданной функции.
         * ([T], (T -> R)) -> [R]
         * @param mapping Функция преобразования элементов.
         */
        public <R> MapHelper<R> map(Function<T, R> mapping) {
            List<R> newSource = new ArrayList<>();
            source.forEach(mapping.andThen(newSource::add)::apply);
//            source.forEach(value->newSource.add(mapping.apply(value)));
            return from(newSource);
        }

        /**
         * Создает объект для маппинга, передавая ему новый список, построенный на основе исходного.
         * Для добавления в новый список каждый элемент преобразовывается в список с использованием заданной функции.
         * ([T], (T -> [R])) -> [R]
         * @param flatMapping Функция преобразования элементов.
         */
        public <R> MapHelper<R> flatMap(Function<T, List<R>> flatMapping) {
            List<R> newSource= new ArrayList<>();
            source.forEach(flatMapping.andThen(newSource::addAll)::apply);
//            source.forEach(value->newSource.addAll(flatMapping.apply(value)));
            return from(newSource);
        }
    }

    @Test
    public void mapEmployeesToLengthOfTheirFullNamesUsingMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        Function<Employee, Person> personExtractor = Employee::getPerson;
        Function<Person, String> fullNameExtractor = Person::getFullName;
        Function<String, Integer> stringLengthFunction = String::length;

        List<Integer> lengths = MapHelper.from(employees)
                                         .map(personExtractor)
                                         .map(fullNameExtractor)
                                         .map(stringLengthFunction)
                                         .getMapped();
        assertEquals(Arrays.asList(14, 19, 14, 15, 14, 16), lengths);
    }

    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingMapHelper() {
        List<Employee> employees = Example1.getEmployees();


        List<Integer> codes = MapHelper.from(employees)
                                       .flatMap(Employee::getJobHistory)
                                       .map(JobHistoryEntry::getPosition)
                                       .flatMap(s -> {
                                           List<Character> chs = new ArrayList<>();
                                           for (int i = 0; i < s.length(); i++)
                                               chs.add(s.charAt(i));
                                           return chs;
                                       })
                                       .map(Integer::new)
                                       .getMapped();
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
