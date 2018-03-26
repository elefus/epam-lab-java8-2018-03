package streams.part2.example;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;
import streams.part2.example.data.PersonPositionPair;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;

@SuppressWarnings("ConstantConditions")
public class Example3 {

    /*
     * Вход:
     * [
     *     {
     *         {Иван Мельников 30},
     *         [dev, dev]
     *     },
     *     {
     *         {Александр Дементьев 28},
     *         [tester, dev, dev]
     *     },
     *     {
     *         {Дмитрий Осинов 40},
     *         [QA, QA, dev]
     *     },
     *     {
     *         {Анна Светличная 21},
     *         [tester]
     *     }
     * ]
     *
     * Выход:
     * [
     *     "dev" -> [ {Иван Мельников 30}, {Александр Дементьев 28}, {Дмитрий Осинов 40} ],
     *     "QA" -> [ {Дмитрий Осинов 40} ],
     *     "tester" -> [ {Александр Дементьев 28}, {Анна Светличная 21} ]
     * ]
     */
    @Test
    public void splitPersonsByPositionExperienceUsingReduce() {
        List<Employee> employees = Example1.getEmployees();

        Map<String, Set<Person>> result = employees.stream()
                                                   .flatMap(Example3::toPersonPositionPairStream)
                                                   .reduce(new HashMap<>(), Example3::addToMap, Example3::mergeMaps);


        assertEquals(prepareExpected(employees), result);
    }

    private static Stream<PersonPositionPair> toPersonPositionPairStream(Employee employee) {
        Person person = employee.getPerson();
        return employee.getJobHistory()
                       .stream()
                       .map(JobHistoryEntry::getPosition)
                       .map(position -> new PersonPositionPair(person, position));
    }

    private static HashMap<String, Set<Person>> addToMap(Map<String, Set<Person>> map, PersonPositionPair pair) {
        HashMap<String, Set<Person>> result = new HashMap<>(map);
        result.compute(pair.getPosition(), (position, persons) -> {
//            if (persons == null) {
//                persons = new HashSet<>();
//            }
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

    @Test
    public void splitPersonsByPositionExperienceUsingCollect() {
        List<Employee> employees = Example1.getEmployees();

        Map<String, Set<Person>> result = employees.stream()
                                                   .flatMap(Example3::toPersonPositionPairStream)
                                                   .collect(HashMap::new, Example3::mutableAddToMap, Example3::mutableMergeMaps);

        assertEquals(prepareExpected(employees), result);
    }

    private static void mutableAddToMap(Map<String, Set<Person>> map, PersonPositionPair pair) {
        map.compute(pair.getPosition(), (position, persons) -> {
            persons = Optional.ofNullable(persons).orElse(new HashSet<>());
            persons.add(pair.getPerson());
            return persons;
        });
    }

    private static void mutableMergeMaps(HashMap<String, Set<Person>> left, HashMap<String, Set<Person>> right) {
        right.forEach((position, persons) -> left.merge(position, persons, (leftPersons, rightPersons) -> {
            leftPersons.addAll(rightPersons);
            return leftPersons;
        }));
    }

    @Test
    public void splitPersonsByPositionExperienceUsingGroupingByCollector() {
        List<Employee> employees = Example1.getEmployees();

        Map<String, Set<Person>> result = employees.stream()
                                                   .flatMap(Example3::toPersonPositionPairStream)
                                                   .collect(groupingBy(PersonPositionPair::getPosition,
                                                                       mapping(PersonPositionPair::getPerson, toSet())));

        assertEquals(prepareExpected(employees), result);
    }

    private static Map<String, Set<Person>> prepareExpected(List<Employee> employees) {
        Map<String, Set<Person>> expected = new HashMap<>();
        expected.put("dev", new HashSet<>(Arrays.asList(
                employees.get(0).getPerson(),
                employees.get(1).getPerson(),
                employees.get(2).getPerson(),
                employees.get(5).getPerson()))
        );
        expected.put("tester", new HashSet<>(Arrays.asList(
                employees.get(1).getPerson(),
                employees.get(3).getPerson(),
                employees.get(4).getPerson()
        )));
        expected.put("QA", new HashSet<>(Arrays.asList(
                employees.get(2).getPerson(),
                employees.get(4).getPerson(),
                employees.get(5).getPerson()
        )));
        return expected;
    }
}
