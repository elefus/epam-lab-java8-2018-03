package streams.part2.exercise;

import javafx.util.Pair;
import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("ConstantConditions")
public class Exercise2 {

    private static <T> Set<T> mergeSets(Set<T> set1, Set<T> set2){
        set1.addAll(set2);
        return set1;
    }
    /**
     * Преобразовать список сотрудников в отображение [компания -> множество людей, когда-либо работавших в этой компании].
     *
     * Входные данные:
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
     * Выходные данные:
     * [
     *    "EPAM" -> [
     *       {Иван Мельников 30},
     *       {Александр Дементьев 28},
     *       {Дмитрий Осинов 40}
     *    ],
     *    "google" -> [
     *       {Иван Мельников 30},
     *       {Александр Дементьев 28}
     *    ],
     *    "yandex" -> [ {Дмитрий Осинов 40} ]
     *    "mail.ru" -> [ {Дмитрий Осинов 40} ]
     *    "T-Systems" -> [ {Анна Светличная 21} ]
     * ]
     */
    @Test
    public void employersStuffList() {
        List<Employee> employees = Example1.getEmployees();

        Map<String, Set<Person>> result =
                employees.stream()
                        .map(employee -> employee.getJobHistory().stream()
                                .collect(Collectors.groupingBy(JobHistoryEntry::getEmployer,
                                        Collectors.mapping(
                                                (val)-> employee.getPerson(),
                                                Collectors.toSet()))))
                        .map(Map::entrySet)
                        .flatMap(Set::stream)
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                Map.Entry::getValue,
                                Exercise2::mergeSets));

        Map<String, Set<Person>> expected = new HashMap<>();
        expected.put("yandex", new HashSet<>(Collections.singletonList(employees.get(2).getPerson())));
        expected.put("mail.ru", new HashSet<>(Collections.singletonList(employees.get(2).getPerson())));
        expected.put("EPAM", new HashSet<>(Arrays.asList(
            employees.get(0).getPerson(),
            employees.get(1).getPerson(),
            employees.get(4).getPerson(),
            employees.get(5).getPerson()
        )));
        expected.put("google", new HashSet<>(Arrays.asList(
            employees.get(0).getPerson(),
            employees.get(1).getPerson()
        )));
        expected.put("T-Systems", new HashSet<>(Arrays.asList(
            employees.get(3).getPerson(),
            employees.get(5).getPerson()
        )));
        assertEquals(expected, result);
    }

    /**
     * Преобразовать список сотрудников в отображение [компания -> множество людей, начавших свою карьеру в этой компании].
     *
     * Пример.
     *
     * Входные данные:
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
     * Выходные данные:
     * [
     *    "EPAM" -> [
     *       {Иван Мельников 30},
     *       {Александр Дементьев 28}
     *    ],
     *    "yandex" -> [ {Дмитрий Осинов 40} ]
     *    "T-Systems" -> [ {Анна Светличная 21} ]
     * ]
     */
    @Test
    public void indexByFirstEmployer() {
        List<Employee> employees = Example1.getEmployees();

        Map<String, Set<Person>> result = employees.stream()
                        .collect(Collectors.toMap(employee-> employee.getJobHistory().get(0).getEmployer(),
                                employee-> new HashSet<>(Collections.singletonList(employee.getPerson())),
                                Exercise2::mergeSets));

        Map<String, Set<Person>> expected = new HashMap<>();
        expected.put("yandex", new HashSet<>(Collections.singletonList(employees.get(2).getPerson())));
        expected.put("EPAM", new HashSet<>(Arrays.asList(
            employees.get(0).getPerson(),
            employees.get(1).getPerson(),
            employees.get(4).getPerson()
        )));
        expected.put("T-Systems", new HashSet<>(Arrays.asList(
            employees.get(3).getPerson(),
            employees.get(5).getPerson()
        )));
        assertEquals(expected, result);
    }

    /**
     * Преобразовать список сотрудников в отображение [компания -> сотрудник, суммарно проработавший в ней наибольшее время].
     * Гарантируется, что такой сотрудник будет один.
     */
    @Test
    public void greatestExperiencePerEmployer() {
        List<Employee> employees = Example1.getEmployees();

        Map<String, Person> collect = employees.stream()
                .map(employee -> employee.getJobHistory().stream()
                        .collect(Collectors.toMap(JobHistoryEntry::getEmployer,
                                jobHistoryEntry -> new Pair<>(employee.getPerson(),
                                        jobHistoryEntry.getDuration()),
                                (personIntegerPair, personIntegerPair2) -> new Pair<>(personIntegerPair.getKey(),
                                        personIntegerPair.getValue()+ personIntegerPair2.getValue()))))
                .map(Map::entrySet)
                .flatMap(Set::stream)
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (personIntegerPair, personIntegerPair2) -> personIntegerPair.getValue()>personIntegerPair2.getValue()
                                ? personIntegerPair
                                : personIntegerPair2))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        stringPairEntry -> stringPairEntry.getValue().getKey()));

        Map<String, Person> expected = new HashMap<>();
        expected.put("EPAM", employees.get(4).getPerson());
        expected.put("google", employees.get(1).getPerson());
        expected.put("yandex", employees.get(2).getPerson());
        expected.put("mail.ru", employees.get(2).getPerson());
        expected.put("T-Systems", employees.get(5).getPerson());
        assertEquals(expected, collect);
    }
}