package streams.part2.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;
import streams.part2.example.Example3;
import streams.part2.example.data.PersonPositionPair;

import javax.swing.event.PopupMenuEvent;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"ConstantConditions", "unused"})
public class Exercise1 {

    @Test
    public void calcTotalYearsSpentInEpam() {
        List<Employee> employees = Example1.getEmployees();
        // TODO реализация
        Long hours = employees.stream()
                                    .map(Employee::getJobHistory)
                                    .flatMap(List::stream)
                                    .filter(j -> j.getEmployer().equals("EPAM"))
                                    .mapToLong(JobHistoryEntry::getDuration)
                                    .sum();

        assertEquals(19, hours.longValue());
    }

    @Test
    public void findPersonsWithQaExperience() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Set<Person> workedAsQa = employees.stream()
                .filter(employee -> employee.getJobHistory().stream().anyMatch(jobHistoryEntry -> jobHistoryEntry.getPosition().equals("QA")))
                .collect(Collectors.mapping(Employee::getPerson, Collectors.toSet()));

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
                                .collect(Collectors.joining("\n"));

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
                .flatMap(Exercise1::firstPositionAndPerson)
                .reduce(new HashMap<>(), Exercise1::addToMap, Exercise1::mergeMaps);

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

    private static HashMap<String, Set<Person>> addToMap(Map<String, Set<Person>> map, PersonPositionPair pair) {
        HashMap<String, Set<Person>> result = new HashMap<>(map);
        result.compute(pair.getPosition(), (position, persons) -> {
            persons = persons == null ? new HashSet<>() : persons;
            persons.add(pair.getPerson());
            return persons;
        });
        return result;
    }

    private static HashMap<String, Set<Person>> mergeMaps(HashMap<String, Set<Person>> left, HashMap<String, Set<Person>> right) {
        HashMap<String, Set<Person>> result = new HashMap<>(left);
        right.forEach((position, persons) -> result.merge(position, persons, (leftPersons, rightPersons) -> {
            leftPersons.addAll(rightPersons);
            return leftPersons;
        }));
        return result;
    }

    public static Stream<PersonPositionPair> firstPositionAndPerson(Employee employee) {
        Person person = employee.getPerson();
        return employee.getJobHistory()
                .stream()
                .limit(1)
                .map(jHE -> new PersonPositionPair(person, jHE.getPosition()));
    }


    @Test
    @SuppressWarnings("Duplicates")
    public void groupPersonsByFirstPositionUsingGroupingByCollector() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Map<String, Set<Person>> result = employees.stream()
                                                   .flatMap(Exercise1::firstPositionAndPerson)
                                                   .collect(Collectors.groupingBy(PersonPositionPair::getPosition,
                                                            Collectors.mapping(PersonPositionPair::getPerson,
                                                                    Collectors.toSet())));


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
