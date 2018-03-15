package lambda.part2.example;

import lambda.data.Person;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"UnnecessaryLocalVariable", "CodeBlock2Expr", "ConstantConditions"})
public class Example3 {

    @Test
    public void oneFunction() {
        Person ivan = new Person("Иван", "Мельников", 33);

        // Person -> Integer                       // Person -> int
        Function<Person, Integer> lastNameLength = person -> person.getLastName().length();

        assertEquals(9, lastNameLength.apply(ivan).intValue());
    }

    // (Person -> String, String -> Integer) -> (Person -> Integer)
    private Function<Person, Integer> personStringPropertyToInt(
            Function<Person, String> personToString,
            Function<String, Integer> stringToInteger) {
        return person -> {
            return stringToInteger.apply(personToString.apply(person));
        };
    }

    @Test
    public void personToInt1() {
        Person person = new Person("Иван", "Мельников", 33);

        Function<Person, String> personLastNameExtractor = Person::getLastName;
        Function<String, Integer> stringLengthExtractor = String::length;
        Function<Person, Integer> personLastNameLengthExtractor = personStringPropertyToInt(personLastNameExtractor, stringLengthExtractor);

        assertEquals(9, personLastNameLengthExtractor.apply(person).intValue());
    }

    // (A -> B, B -> C) -> (A -> C)
    private <A, B, C> Function<A, C> andThen(Function<A, B> first, Function<B, C> second) {
        return value -> second.apply(first.apply(value));
    }

    @Test
    public void customAndThenFunction() {
        Person person = new Person("Иван", "Мельников", 33);

        Function<Person, String> personLastNameExtractor = Person::getLastName;
        Function<String, Integer> stringLengthExtractor = String::length;
        Function<Person, Integer> lastNameLength = andThen(personLastNameExtractor, stringLengthExtractor);

        assertEquals(9, lastNameLength.apply(person).intValue());
    }

    @Test
    public void standardAndThenFunction() {
        Person person = new Person("Иван", "Мельников", 33);

        Function<Person, String> personLastNameExtractor = Person::getLastName;
        Function<String, Integer> stringLengthExtractor = String::length;
        Function<Person, Integer> lastNameLength = personLastNameExtractor.andThen(stringLengthExtractor);

        assertEquals(9, lastNameLength.apply(person).intValue());
    }

    @Test
    public void standardComposeFunction() {
        Person person = new Person("Иван", "Мельников", 33);

        Function<Person, String> personLastNameExtractor = Person::getLastName;
        Function<String, Integer> stringLengthExtractor = String::length;
        Function<Person, Integer> lastNameLength = stringLengthExtractor.compose(personLastNameExtractor);

        assertEquals(9, lastNameLength.apply(person).intValue());
    }
}
