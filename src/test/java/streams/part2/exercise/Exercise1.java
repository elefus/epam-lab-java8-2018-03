package streams.part2.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class Exercise1 {

    @Test
    public void calcTotalYearsSpentInEpam() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        // начнём с конца - результатом д.б. Лонг тогда терминальной операцией у меня может быть :
        // .mapToLong(), или sum()... но пока не точно
        // чтобы получить её в результирующем стриме у меня д.б. Лист Long, то есть Durationов,
        // duration получается из
        // JobHistoryEntry::getDuration
        // .map()
        // - это список то есть перед тем как получить лист Durationов я должен
        // преобразовать лист JobHistoryEntry в JobHistoryEntry в которых Position.equals("EPAM")
        // .filter(entry -> "EPAM".equals(entry.getPosition())
        // чтобы получить список всех entry для фильтра их нужно вытащить из JobHistory - для каждого employee
        // то есть получить простой список одних элементов из вложенного списка списков другого типа
        // .flatMap(JobJistory::getJobHistoryEntry)
        // чтобы получить список JobHistory нам нужен список employee из элементов которого получается вложенный
        // .map(Employee::getJobHistory)
        // ну и для того чтобы использовать сущность как стрим надо сделать её стримом
        // .stream()
        Long
                hours =
                employees.stream()
                         .map(Employee::getJobHistory)
                         .flatMap(jobHistoryEntries -> jobHistoryEntries.stream()
                                                                        .filter(jobHistoryEntry -> "EPAM".equals(
                                                                                jobHistoryEntry.getPosition())))
                         .mapToLong(JobHistoryEntry::getDuration)
                         .sum();
        assertEquals(19, hours.longValue());
    }

    @Test
    public void findPersonsWithQaExperience() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Set<Person> workedAsQa = null;

        assertEquals(new HashSet<>(Arrays.asList(
            employees.get(2).getPerson(),
            employees.get(4).getPerson(),
            employees.get(5).getPerson()
        )), workedAsQa);
    }

    @Test
    public void composeFullNamesOfEmployeesUsingLineSeparatorAsDelimiter() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        String result = null;

        assertEquals("Иван Мельников\n"
                   + "Александр Дементьев\n"
                   + "Дмитрий Осинов\n"
                   + "Анна Светличная\n"
                   + "Игорь Толмачёв\n"
                   + "Иван Александров", result);
    }

    @Test
    public void groupPersonsByFirstPositionUsingToMap() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Map<String, Set<Person>> result = null;

        Map<String, Set<Person>> expected = new HashMap<>();
        expected.put("QA", new HashSet<>(Arrays.asList(employees.get(2).getPerson(), employees.get(5).getPerson())));
        expected.put("dev", Collections.singleton(employees.get(0).getPerson()));
        expected.put("tester", new HashSet<>(Arrays.asList(
            employees.get(1).getPerson(),
            employees.get(3).getPerson(),
            employees.get(4).getPerson()))
        );
        assertEquals(expected, result);
    }

    @Test
    public void groupPersonsByFirstPositionUsingGroupingByCollector() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Map<String, Set<Person>> result = null;

        Map<String, Set<Person>> expected = new HashMap<>();
        expected.put("QA", new HashSet<>(Arrays.asList(employees.get(2).getPerson(), employees.get(5).getPerson())));
        expected.put("dev", Collections.singleton(employees.get(0).getPerson()));
        expected.put("tester", new HashSet<>(Arrays.asList(
            employees.get(1).getPerson(),
            employees.get(3).getPerson(),
            employees.get(4).getPerson()))
        );
        assertEquals(expected, result);
    }
}
