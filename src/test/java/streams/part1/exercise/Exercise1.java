package streams.part1.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class Exercise1 {

    @Test
    public void findPersonsEverWorkedInEpam() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация, использовать Collectors.toList()
        List<Person>
                personsEverWorkedInEpam =
                employees.stream()
                         .filter(employee -> employee.getJobHistory()
                                                     .stream()
                                                     .map(JobHistoryEntry::getEmployer)
                                                     .anyMatch("EPAM"::equalsIgnoreCase))
                         .map(Employee::getPerson)
                         .collect(Collectors.toList());
        List<Person>
                expected =
                Arrays.asList(employees.get(0).getPerson(),
                              employees.get(1).getPerson(),
                              employees.get(4).getPerson(),
                              employees.get(5).getPerson());
        assertEquals(expected, personsEverWorkedInEpam);
    }

    @Test
    public void findPersonsBeganCareerInEpam() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация, использовать Collectors.toList()
        List<Person>
                startedFromEpam =
                employees.stream()
                         .filter(employee -> employee.getJobHistory().get(0).equals("EPAM"))
                         .map(Employee::getPerson)
                         .collect(Collectors.toList());

        List<Person>
                expected =
                Arrays.asList(employees.get(0).getPerson(), employees.get(1).getPerson(), employees.get(4).getPerson());
        assertEquals(expected, startedFromEpam);
    }

    @Test
    public void findAllCompanies() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация, использовать Collectors.toSet()
        Set<String>
                companies =
                employees.stream()
                         .flatMap(employee -> employee.getJobHistory().stream())
                         .map(JobHistoryEntry::getEmployer)
                         .collect(Collectors.toSet());

        Set<String> expected = new HashSet<>();
        expected.add("EPAM");
        expected.add("google");
        expected.add("yandex");
        expected.add("mail.ru");
        expected.add("T-Systems");
        assertEquals(expected, companies);
    }

    @Test
    public void findMinimalAgeOfEmployees() throws Exception {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Integer
                minimalAge =
                employees.stream().map(Employee::getPerson).map(Person::getAge).min(Comparator.naturalOrder()).get();
        minimalAge =
                employees.stream().map(Employee::getPerson).mapToInt(Person::getAge).min().orElseThrow(Exception::new);

        assertEquals(21, minimalAge.intValue());
    }
}