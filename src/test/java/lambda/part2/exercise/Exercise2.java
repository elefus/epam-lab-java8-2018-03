package lambda.part2.exercise;

import lambda.data.Person;
import org.junit.Test;

import java.util.function.Predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings({"ConstantConditions", "unused"})
public class Exercise2 {

    @Test
    public void personHasNotEmptyLastNameAndFirstName() {
        Predicate<Person> validate = p -> !(p.getFirstName().isEmpty() || p.getLastName().isEmpty());

        assertTrue(validate.test(new Person("Алексей", "Доренко", 40)));
        assertFalse(validate.test(new Person("Николай", "", 30)));
        assertFalse(validate.test(new Person("", "Мельников", 20)));
    }

    private Predicate<Person> negateUsingLogicalOperator(Predicate<Person> predicate) {
        return p -> !predicate.test(p);
    }

    private Predicate<Person> andUsingLogicalOperator(Predicate<Person> left, Predicate<Person> right) {
        return p -> left.test(p) && right.test(p);
    }

    @Test
    public void personHasNotEmptyLastNameAndFirstNameUsingLogicalOperators() {
        Predicate<Person> personHasEmptyFirstName = p -> p.getFirstName().isEmpty();
        Predicate<Person> personHasEmptyLastName = p -> p.getLastName().isEmpty();

        Predicate<Person> personHasNotEmptyFirstName = negateUsingLogicalOperator(personHasEmptyFirstName);
        Predicate<Person> personHasNotEmptyLastName = negateUsingLogicalOperator(personHasEmptyLastName);

        Predicate<Person> personHasNotEmptyLastNameAndFirstName = and(personHasNotEmptyFirstName, personHasNotEmptyLastName);
        assertTrue(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)));
    }

    private <T> Predicate<T> negate(Predicate<T> predicate) {
        return arg -> !predicate.test(arg);
    }

    private <T> Predicate<T> and(Predicate<T> left, Predicate<T> right) {
        return arg -> left.test(arg) && right.test(arg);
    }

    @Test
    public void personHasNotEmptyLastNameAndFirstNameUsingGenericPredicates() {
        Predicate<Person> personHasEmptyFirstName = p -> p.getFirstName().isEmpty();
        Predicate<Person> personHasEmptyLastName = p -> p.getLastName().isEmpty();

        Predicate<Person> personHasNotEmptyFirstName = negate(personHasEmptyFirstName);
        Predicate<Person> personHasNotEmptyLastName = negate(personHasEmptyLastName);

        Predicate<Person> personHasNotEmptyLastNameAndFirstName =
                and(personHasNotEmptyFirstName, personHasNotEmptyLastName);

        assertTrue(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)));
    }

    @Test
    public void personHasNotEmptyLastNameAndFirstNameUsingStandardMethods() {
        Predicate<Person> personHasEmptyFirstName = p -> p.getFirstName().isEmpty();
        Predicate<Person> personHasEmptyLastName = p -> p.getLastName().isEmpty();

        Predicate<Person> personHasNotEmptyFirstName = personHasEmptyFirstName.negate();
        Predicate<Person> personHasNotEmptyLastName = personHasEmptyLastName.negate();

        Predicate<Person> personHasNotEmptyLastNameAndFirstName =
                personHasNotEmptyFirstName.and(personHasNotEmptyLastName);

        assertTrue(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)));
    }
}
