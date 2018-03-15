package lambda.part2.example;

import lambda.data.Person;
import org.junit.Test;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("UnnecessaryLocalVariable")
public class Example2 {

    // (Person, Person -> String) -> (String -> boolean)
    private static Predicate<String> stringPropertyChecker(Person person, Function<Person, String> getProperty) {
        return propertyValue -> Objects.equals(getProperty.apply(person), propertyValue);
    }

    @Test
    public void checkConcretePersonStringProperty() {
        Person person = new Person("Иван", "Мельников", 33);

        // Person -> String                    // Person -> String
        Function<Person, String> getProperty = Person::getFirstName;
        Predicate<String> checkFirstName = stringPropertyChecker(person, getProperty);

        assertTrue(checkFirstName.test("Иван"));
        assertFalse(checkFirstName.test("Игорь"));
    }

    // (Person -> String) -> (Person -> (String -> boolean))
    private static Function<Person, Predicate<String>> stringPropertyChecker(Function<Person, String> propertyExtractor) {
        return person -> checkingValue -> Objects.equals(propertyExtractor.apply(person), checkingValue);
    }

    @Test
    @SuppressWarnings("Convert2MethodRef")
    public void checkAnyPersonStringProperty() {
        Person person = new Person("Иван", "Мельников", 33);

        Function<Person, Predicate<String>> lastNameChecker = stringPropertyChecker(Person::getLastName);

        assertTrue(lastNameChecker.apply(person).test("Мельников"));
        assertFalse(lastNameChecker.apply(person).test("Гущин"));
    }

    // (V -> P) -> (V -> (P -> boolean))
    private static <V, P, T> Function<V, Predicate<P>> propertyChecker(Function<V, T> propertyExtractor) {
        return object -> checkedProperty -> Objects.equals(propertyExtractor.apply(object), checkedProperty);
    }

    @Test
    public void checkAnyProperty() {
        Person person = new Person("Иван", "Мельников", 33);

        Function<Person, Predicate<Integer>> ageChecker = propertyChecker(Person::getAge);

        assertTrue(ageChecker.apply(person).test(33));
        assertFalse(ageChecker.apply(person).test(10));
    }
}
