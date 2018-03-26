package streams.part2.example;

import lambda.data.Employee;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;
import streams.part2.example.data.PersonPositionPair;

import java.util.*;
import java.util.stream.Stream;

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

        Map<String, Set<Person>> result = null;

        assertEquals(prepareExpected(employees), result);
    }

    private static Stream<PersonPositionPair> getPersonPositionPairStream(Employee employee) {
        return null;
    }

    private static HashMap<String, Set<Person>> addToMap(Map<String, Set<Person>> map, PersonPositionPair pair) {
        return null;
    }

    private static HashMap<String, Set<Person>> mergeMaps(HashMap<String, Set<Person>> left, HashMap<String, Set<Person>> right) {
        return null;
    }

    @Test
    public void splitPersonsByPositionExperienceUsingCollect() {
        List<Employee> employees = Example1.getEmployees();

        Map<String, Set<Person>> result = null;

        assertEquals(prepareExpected(employees), result);
    }

    private static void mutableAddToMap(Map<String, Set<Person>> map, PersonPositionPair pair) {
    }

    private static void mutableMergeMaps(HashMap<String, Set<Person>> left, HashMap<String, Set<Person>> right) {
    }

    @Test
    public void splitPersonsByPositionExperienceUsingGroupingByCollector() {
        List<Employee> employees = Example1.getEmployees();

        Map<String, Set<Person>> result = null;

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
