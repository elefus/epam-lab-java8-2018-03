package lambda.part3.example;

import lambda.data.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("WeakerAccess")
public class Example2 {

    public static class FilterUtil<T> {

        private final List<T> source;

        private FilterUtil(List<T> source) {
            this.source = source;
        }

        /**
         * Статический фабричный метод.
         * @param source Исходный список.
         * @param <T> Тип элементов исходного списка.
         * @return Созданный объект.
         */
        public static <T> FilterUtil<T> from(List<T> source) {
            // TODO check null
            return new FilterUtil<>(source);
        }

        /**
         * Создает объект для фильтрации, передавая ему новый список, построенный на основе исходного.
         * Для добавления в новый список каждый элемент проверяется на соответствие заданному условию.
         * ([T], (T -> boolean)) -> [T]
         * @param condition условие по которому производится отбор.
         */
        public FilterUtil<T> filter(Predicate<T> condition) {
            ArrayList<T> result = new ArrayList<>();
            source.forEach(value -> {
                if (condition.test(value)) {
                    result.add(value);
                }
            });
            return from(result);
        }

        public List<T> getResult() {
            return new ArrayList<>(source);
        }
    }

    @Test
    public void findIvanWithDeveloperExperienceAndWorkedInEpamMoreThenYearAtOnePositionUsingFilterUtil() {
        List<Employee> employees = Example1.getEmployees();

        List<Employee> result = FilterUtil.from(employees)
                                          .filter(employee -> "Иван".equals(employee.getPerson().getFirstName()))
                                          .filter(Example2::hasDeveloperExperience)
                                          .filter(Example2::workedInEpamMoreThenYearAtOnePosition)
                                          .getResult();

        assertEquals(Arrays.asList(employees.get(0), employees.get(5)), result);
    }

    private static boolean hasDeveloperExperience(Employee employee) {
        return !FilterUtil.from(employee.getJobHistory())
                          .filter(entry -> "dev".equals(entry.getPosition()))
                          .getResult()
                          .isEmpty();
    }

    private static boolean workedInEpamMoreThenYearAtOnePosition(Employee employee) {
        return !FilterUtil.from(employee.getJobHistory())
                          .filter(entry -> "EPAM".equals(entry.getEmployer()))
                          .filter(entry -> entry.getDuration() > 1)
                          .getResult()
                          .isEmpty();
    }
}
