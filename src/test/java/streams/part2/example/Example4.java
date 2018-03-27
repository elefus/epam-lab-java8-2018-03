package streams.part2.example;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;
import streams.part2.example.data.PersonDurationPair;
import streams.part2.example.data.PersonPositionDuration;
import streams.part2.example.data.PersonPositionPair;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;
import static org.junit.Assert.assertEquals;

@SuppressWarnings("ConstantConditions")
public class Example4 {

    /*
      * Вход:
      * [
      *     {
      *         {Иван Мельников 30},
      *         [
      *             {2, dev, "EPAM"},
      *             {1, dev, "google"}
      *         ]
      *     },
      *     {
      *         {Александр Дементьев 28},
      *         [
      *             {2, tester, "EPAM"},
      *             {1, dev, "EPAM"},
      *             {1, dev, "google"}
      *         ]
      *     },
      *     {
      *         {Дмитрий Осинов 40},
      *         [
      *             {3, QA, "yandex"},
      *             {1, QA, "EPAM"},
      *             {1, dev, "mail.ru"}
      *         ]
      *     },
      *     {
      *         {Анна Светличная 21},
      *         [
      *             {1, tester, "T-Systems"}
      *         ]
      *     }
      * ]
      *
      * Выход:
      * [
      *     "dev" -> {Иван Мельников 30}
      *     "QA" -> {Дмитрий Осинов 40}
      *     "tester" -> {Александр Дементьев 28}
      * ]
      */
    @Test
    public void getTheCoolestByPositionUsingToMap() {
        List<Employee> employees = Example1.getEmployees();

        Map<String, Person> coolest = employees.stream()
                                               .flatMap(employee -> employee.getJobHistory()
                                                                            .stream()
                                                                            .collect(toMap(JobHistoryEntry::getPosition,
                                                                                    entry -> new PersonDurationPair(employee
                                                                                            .getPerson(), entry.getDuration()),
                                                                                    (pair1, pair2) -> new PersonDurationPair(employee
                                                                                            .getPerson(), pair1
                                                                                            .getDuration() + pair2.getDuration())))
                                                                            .entrySet()
                                                                            .stream())
                                               .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (entry1, entry2) -> entry1
                                                       .getDuration() > entry2.getDuration() ? entry1 : entry2))
                                               .entrySet()
                                               .stream()
                                               .collect(toMap(Map.Entry::getKey, entry -> entry.getValue()
                                                                                               .getPerson()));

        assertEquals(prepareExpected(employees), coolest);
    }

    @Test
    public void getTheCoolestByPositionUsingGroupingBy() {
        List<Employee> employees = Example1.getEmployees();

        Map<String, Person> coolest = employees.stream()
                                               .flatMap(employee -> employee.getJobHistory()
                                                                            .stream()
                                                                            .collect(groupingBy(JobHistoryEntry::getPosition, summingInt(JobHistoryEntry::getDuration)))
                                                                            .entrySet()
                                                                            .stream()
                                                                            .map(entry -> new PersonPositionDuration(employee.getPerson(), entry.getKey(), entry.getValue())))
                                               .collect(groupingBy(PersonPositionDuration::getPosition,
                                                        collectingAndThen(maxBy(comparingInt(PersonPositionDuration::getDuration)),
                                                                          entry -> entry.map(PersonPositionDuration::getPerson)
                                                                                        .orElseThrow(IllegalStateException::new))));


        assertEquals(prepareExpected(employees), coolest);
    }

    private static Map<String, Person> prepareExpected(List<Employee> employees) {
        Map<String, Person> expected = new HashMap<>();
        expected.put("dev", employees.get(0).getPerson());
        expected.put("tester", employees.get(4).getPerson());
        expected.put("QA", employees.get(4).getPerson());
        return expected;
    }
}
