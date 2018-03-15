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
        // TODO предикат Person -> boolean, проверяющий что имя и фамилия человека не пусты
        Predicate<Person> validate = p -> !p.getFirstName().isEmpty() && !p.getLastName().isEmpty();

        assertTrue(validate.test(new Person("Алексей", "Доренко", 40)));
        assertFalse(validate.test(new Person("Николай", "", 30)));
        assertFalse(validate.test(new Person("", "Мельников", 20)));
    }

    // TODO метод (Person -> boolean) -> (Person -> boolean)
    // TODO - возвращает новый предикат, являющийся отрицанием исходного
    // TODO - при реализации использовать логический оператор !
    private Predicate<Person> negateUsingLogicalOperator(Predicate<Person> predicate) {
        return p -> !predicate.test(p);
    }

    // TODO метод (Person -> boolean, Person -> boolean) -> (Person -> boolean)
    // TODO - возвращает новый предикат, объединяющий исходные с помощью операции "AND"
    // TODO - при реализации использовать логический оператор &&
    private Predicate<Person> andUsingLogicalOperator(Predicate<Person> left, Predicate<Person> right) {
        return p -> left.test(p) && right.test(p);
    }

    @Test
    public void personHasNotEmptyLastNameAndFirstNameUsingLogicalOperators() {
        Predicate<Person> personHasEmptyFirstName = negateUsingLogicalOperator(p -> !p.getFirstName().isEmpty());
        Predicate<Person> personHasEmptyLastName = negateUsingLogicalOperator(p -> !p.getLastName().isEmpty());

        Predicate<Person> personHasNotEmptyFirstName = negateUsingLogicalOperator(p -> p.getFirstName().isEmpty());
        Predicate<Person> personHasNotEmptyLastName = negateUsingLogicalOperator(p -> p.getLastName().isEmpty());

        Predicate<Person> personHasNotEmptyLastNameAndFirstName = p -> !p.getLastName().isEmpty() && !p.getFirstName().isEmpty();

        assertTrue(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)));

        assertTrue(personHasNotEmptyLastName.test(new Person("", "Мельников", 20)));
        assertFalse(personHasNotEmptyFirstName.test(new Person("", "Мельников", 20)));
        assertTrue(personHasEmptyFirstName.test(new Person("", "Мельников", 20)));
        assertTrue(personHasEmptyLastName.test(new Person("", "", 20)));
    }

    // TODO метод (T -> boolean) -> (T -> boolean)
    // TODO - возвращает новый предикат, являющийся отрицанием исходного
    // TODO - при реализации использовать логический оператор !
    private <T> Predicate<T> negate(Predicate<T> predicate) {
        return predicate.negate();
    }

    // TODO метод (T -> boolean, T -> boolean) -> (T -> boolean)
    // TODO - возвращает новый предикат, объединяющий исходные с помощью операции "AND"
    // TODO - при реализации использовать логический оператор &&
    private <T> Predicate<T> and(Predicate<T> left, Predicate<T> right) {
        return left.and(right);
    }

    @Test
    public void personHasNotEmptyLastNameAndFirstNameUsingGenericPredicates() {
        Predicate<Person> personHasEmptyFirstName = negate(p -> !p.getFirstName().isEmpty());
        Predicate<Person> personHasEmptyLastName = negate(p -> !p.getLastName().isEmpty());

        Predicate<Person> personHasNotEmptyFirstName = negate(p -> p.getFirstName().isEmpty());
        Predicate<Person> personHasNotEmptyLastName = negate(p -> p.getLastName().isEmpty());

        Predicate<Person> personHasNotEmptyLastNameAndFirstName =
                and(p -> !p.getLastName().isEmpty(), p -> !p.getFirstName().isEmpty());

        assertTrue(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)));

        assertTrue(personHasNotEmptyLastName.test(new Person("", "Мельников", 20)));
        assertFalse(personHasNotEmptyFirstName.test(new Person("", "Мельников", 20)));
        assertTrue(personHasEmptyFirstName.test(new Person("", "Мельников", 20)));
        assertTrue(personHasEmptyLastName.test(new Person("Алексей", "", 20)));
    }

    @Test
    public void personHasNotEmptyLastNameAndFirstNameUsingStandardMethods() {
        Predicate<Person> personHasEmptyFirstName = negate(p -> !p.getFirstName().isEmpty());
        Predicate<Person> personHasEmptyLastName = negate(p -> !p.getLastName().isEmpty());

        // TODO использовать Predicate.negate
        Predicate<Person> personHasNotEmptyFirstName = ((Predicate<Person>) person -> person.getFirstName().isEmpty()).negate();
        Predicate<Person> personHasNotEmptyLastName = ((Predicate<Person>) person -> person.getLastName().isEmpty()).negate();

        // TODO использовать Predicate.and
        Predicate<Person> personHasNotEmptyLastNameAndFirstName = ((Predicate<Person>) person -> !person.getLastName().isEmpty()).and(person -> !person.getFirstName().isEmpty());

        assertTrue(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)));

        assertTrue(personHasEmptyFirstName.test(new Person("", "Мельников", 20)));
        assertTrue(personHasEmptyLastName.test(new Person("Алексей", "", 20)));
        assertTrue(personHasNotEmptyLastName.test(new Person("", "Мельников", 20)));
        assertFalse(personHasNotEmptyFirstName.test(new Person("", "Мельников", 20)));
    }

}
