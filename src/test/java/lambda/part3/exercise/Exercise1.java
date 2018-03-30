package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Exercise1 {

    @Test
    public void mapEmployeesToLengthOfTheirFullNames() {
        List<Employee> employees = Example1.getEmployees();
        List<Integer> lengths = new ArrayList<>();
        Function<Employee, Person> personExtractor = Employee::getPerson;
        Function<Person, String> fullNameExtractor = person -> String.format("%s %s", person.getFirstName(), person.getLastName());
        ToIntFunction<String> stringLengthExtractor = String::length;
        ToIntFunction<Employee> fullNameLengthExtractor = employee ->
                stringLengthExtractor.applyAsInt(fullNameExtractor.apply(personExtractor.apply(employee)));

        for (Employee employee : employees) lengths.add(fullNameLengthExtractor.applyAsInt(employee));

        assertEquals(Arrays.asList(14, 19, 14, 15, 14, 16), lengths);
    }
}
