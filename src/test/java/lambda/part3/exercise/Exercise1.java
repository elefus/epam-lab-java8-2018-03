package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Exercise1 {

    @Test
    public void mapEmployeesToLengthOfTheirFullNames() {
        List<Employee> employees = Example1.getEmployees();
        List<Integer> lengths;

        Function<Employee, Person> personExtractor = e -> e.getPerson();
        Function<Person, String> fullNameExtractor = p -> p.getFirstName() + " " + p.getLastName();
        Function<String, Integer> stringLengthExtractor = s -> s.length();
        Function<Person, Integer> fullNameLengthExtractor = fullNameExtractor.andThen(stringLengthExtractor);
        lengths = employees
                .stream()
                .map(e -> fullNameLengthExtractor.apply(e.getPerson()))
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(14, 19, 14, 15, 14, 16), lengths);
    }
}
