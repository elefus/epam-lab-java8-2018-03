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

        // TODO функция извлечения информации о человеке из объекта сотрудника personExtractor: Employee -> Person
        Function<Employee, Person> personExtractor = Employee::getPerson;
        // TODO функция извлечения полного имени из информации о человеке fullNameExtractor: Person -> String
        Function<Person, String> fullNameExtractor = Person::getFullName;
        // TODO функция извлечения длины из строки stringLengthExtractor: String -> Integer
        ToIntFunction<String> stringLengthExtractor = String::length;
        // TODO функция извлечения длины полного имени из сотрудника fullNameLengthExtractor: Employee -> Integer
        ToIntFunction<Employee> fullNameLengthExtractor = employee -> stringLengthExtractor.applyAsInt(fullNameExtractor.apply(personExtractor.apply(employee)));
        // TODO преобразование списка employees в lengths используя fullNameLengthExtractor
        for (Employee employee: employees) {
            lengths.add(fullNameLengthExtractor.applyAsInt(employee));
        }

        assertEquals(Arrays.asList(14, 19, 14, 15, 14, 16), lengths);
    }


}
