package streams.part2.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;
import streams.part2.example.data.PersonPositionPair;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.junit.Assert.assertEquals;

@SuppressWarnings({"ConstantConditions", "unused"})
public class Exercise1 {

    @Test
    public void calcTotalYearsSpentInEpam() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Long hours = employees.stream()
                              .map(Employee::getJobHistory)
                              .flatMapToLong(list -> list.stream()
                                                         .filter(l -> "EPAM".equals(l.getEmployer()))
                                                         .mapToLong(JobHistoryEntry::getDuration))
                              .sum();

        assertEquals(19, hours.longValue());
    }

    @Test
    public void findPersonsWithQaExperience() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Set<Person> workedAsQa = employees.stream().filter(e->e.getJobHistory()
                                                               .stream()
                                                               .anyMatch(l->"QA".equals(l.getPosition())))
                 .map(Employee::getPerson)
                 .collect(toSet());

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
        String result = employees.stream()
                                 .map(Employee::getPerson)
                                 .map(Person::getFullName)
                                 .collect(joining("\n"));

        assertEquals("Иван Мельников\n"
                   + "Александр Дементьев\n"
                   + "Дмитрий Осинов\n"
                   + "Анна Светличная\n"
                   + "Игорь Толмачёв\n"
                   + "Иван Александров", result);
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void groupPersonsByFirstPositionUsingToMap() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Map<String, Set<Person>> result = employees.stream()
                                                   .map(e -> new PersonPositionPair(e.getPerson(), e.getJobHistory()
                                                                                                    .get(0)
                                                                                                    .getPosition()))
                                                   .collect(toMap(PersonPositionPair::getPosition,
                                                           p -> {
                                                               Set<Person> treeSet = new TreeSet<>();
                                                               treeSet.add(p.getPerson());
                                                               return treeSet;
                                                           }, (s1, s2) -> {
                                                               s1.addAll(s2);
                                                               return s1;
                                                           }));

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
    @SuppressWarnings("Duplicates")
    public void groupPersonsByFirstPositionUsingGroupingByCollector() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Map<String, Set<Person>> result = employees.stream()
                                                   .map(e -> new PersonPositionPair(e.getPerson(), e.getJobHistory()
                                                                                                    .get(0)
                                                                                                    .getPosition()))
                                                   .collect(groupingBy(PersonPositionPair::getPosition,
                                                           mapping(PersonPositionPair::getPerson,
                                                                   toSet())));
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
