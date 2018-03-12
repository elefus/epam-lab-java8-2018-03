package lambda.part2.example;

import lambda.data.Person;
import org.junit.Test;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.*;

public class Example1 {

    // String -> int
    private static int strLength(String string) {
        return string.length();
    }

    @Test
    public void stringToInt() {
        Person person = new Person();

        // String -> Integer
        Function<String, Integer> strLength = Example1::strLength;
        Function<String, Integer> strLength1 = person::convert;

        assertEquals(5, strLength.apply("12345").intValue());
    }

    @Test
    public void personToString() {
        Person person = new Person("Иван", "Мельников", 33);

        // Person -> String
        Function<Person, String> lastName = Person::getLastName;
        lastName.apply(person);

        Function<Person, String> lastName1 = person1 -> person1.getLastName();
        lastName1.apply(person);


        Supplier<String> supplierLastName = person::getLastName;

        assertEquals("Мельников", lastName.apply(person));
    }

    @Test
    public void personToInt() {
        String lastName = "Мельников";
        Person ivan = new Person("Иван", lastName, 33);

        // Person -> Integer
        Function<Person, Integer> lastNameLength = person -> strLength(person.getLastName());

        assertEquals(lastName.length(), lastNameLength.apply(ivan).intValue());
    }

    // (Person, String) -> boolean
    private static boolean isSameLastName(Person person, String lastName) {
        return Objects.equals(person.getLastName(), lastName);
    }

    @Test
    public void checkLastName() {
        Person person = new Person("Иван", "Мельников", 33);

        // (Person, String) -> Boolean
        BiFunction<Person, String, Boolean> sameLastName = Example1::isSameLastName;

        // (Person, String) -> boolean
        BiPredicate<Person, String> sameLastNamePredicate = Example1::isSameLastName;


        assertTrue(sameLastName.apply(person, "Мельников"));
        assertFalse(sameLastNamePredicate.test(person, "Плотников"));
    }
}
