package streams.part1.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"ConstantConditions", "unused"})
public class Exercise1 {

    @Test
    public void findPersonsEverWorkedInEpam() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация, использовать Collectors.toList()
        List<Person> personsEverWorkedInEpam = employees.stream()
                .filter(e->e.getJobHistory().stream()
                        .map(JobHistoryEntry::getEmployer)
                        .anyMatch("EPAM"::equals))
                .map(Employee::getPerson)
                .collect(Collectors.toList());

        List<Person> expected = Arrays.asList(
                employees.get(0).getPerson(),
                employees.get(1).getPerson(),
                employees.get(4).getPerson(),
                employees.get(5).getPerson());
        assertEquals(expected, personsEverWorkedInEpam);
    }

    @Test
    public void findPersonsBeganCareerInEpam() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация, использовать Collectors.toList()
        List<Person> startedFromEpam = employees.stream()
                .filter(e->"EPAM".equals(e.getJobHistory().get(0).getEmployer()))
                .map(Employee::getPerson)
                .collect(Collectors.toList());

        List<Person> expected = Arrays.asList(
                employees.get(0).getPerson(),
                employees.get(1).getPerson(),
                employees.get(4).getPerson());
        assertEquals(expected, startedFromEpam);
    }

    @Test
    public void findAllCompanies() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация, использовать Collectors.toSet()
        Set<String> companies = employees.stream()
                .flatMap(employee->employee.getJobHistory().stream())
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
    public void findMinimalAgeOfEmployees() {
        List<Employee> employees = Example1.getEmployees();

        // TODO реализация
        Integer minimalAge = employees.stream()
                .map(Employee::getPerson)
                .mapToInt(Person::getAge).min().orElseThrow(IllegalStateException::new);

        assertEquals(21, minimalAge.intValue());
    }
}