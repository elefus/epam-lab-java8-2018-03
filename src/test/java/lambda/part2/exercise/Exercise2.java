package lambda.part2.exercise;

import lambda.data.Person;
import org.junit.Test;

import java.util.function.Predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Exercise2 {

    @Test
    public void personHasNotEmptyLastNameAndFirstName() {
        // TODO предикат Person -> boolean, проверяющий что имя и фамилия человека не пусты
        Predicate<Person>
                validate =
                person -> (person.getLastName().length() != 0) &&
                          ((person.getFirstName().length() == 0) ? false : true);

        assertTrue(validate.test(new Person("Алексей", "Доренко", 40)));
        assertFalse(validate.test(new Person("Николай", "", 30)));
        assertFalse(validate.test(new Person("", "Мельников", 20)));
    }

    // TODO метод (Person -> boolean) -> (Person -> boolean)
    // TODO - возвращает новый предикат, являющийся отрицанием исходного
    // TODO - при реализации использовать логический оператор !
    private Predicate<Person> negateUsingLogicalOperator(Predicate<Person> predicate) {
//        throw new UnsupportedOperationException();
        return person -> !predicate.test(person);
    }

    // TODO метод (Person -> boolean, Person -> boolean) -> (Person -> boolean)
    // TODO - возвращает новый предикат, объединяющий исходные с помощью операции "AND"
    // TODO - при реализации использовать логический оператор &&
    private Predicate<Person> andUsingLogicalOperator(Predicate<Person> left, Predicate<Person> right) {
//        throw new UnsupportedOperationException();
        return person -> (left.test(person) && right.test(person));
    }


    @Test
    public void personHasNotEmptyLastNameAndFirstNameUsingLogicalOperators() {
        Predicate<Person> personHasEmptyFirstName = person -> person.getFirstName().length() == 0;
        Predicate<Person> personHasEmptyLastName = person -> person.getLastName().length() == 0;

        Predicate<Person> personHasNotEmptyFirstName = person -> person.getFirstName().length() != 0;
        Predicate<Person> personHasNotEmptyLastName = person -> person.getLastName().length() != 0;

        Predicate<Person>
                personHasNotEmptyLastNameAndFirstName =
                person -> personHasNotEmptyLastName.test(person) && personHasNotEmptyFirstName.test(person);

        assertTrue(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)));
    }

    // TODO метод (T -> boolean) -> (T -> boolean)
    // TODO - возвращает новый предикат, являющийся отрицанием исходного
    // TODO - при реализации использовать логический оператор !
    private <T> Predicate<T> negate(Predicate<T> predicate) {
//        throw new UnsupportedOperationException();
        return (T t) -> !predicate.test(t);
    }

    // TODO метод (T -> boolean, T -> boolean) -> (T -> boolean)
    // TODO - возвращает новый предикат, объединяющий исходные с помощью операции "AND"
    // TODO - при реализации использовать логический оператор &&
    private <T> Predicate<T> and(Predicate<T> left, Predicate<T> right) {
//        throw new UnsupportedOperationException();
        return (T t) -> left.test(t) && right.test(t);
    }

    @Test
    public void personHasNotEmptyLastNameAndFirstNameUsingGenericPredicates() {
        Predicate<Person> personHasEmptyFirstName = person -> person.getFirstName().length() == 0;
        Predicate<Person> personHasEmptyLastName = person -> person.getLastName().length() == 0;

        Predicate<Person> personHasNotEmptyFirstName = person -> person.getFirstName().length() != 0;
        Predicate<Person> personHasNotEmptyLastName = person -> person.getLastName().length() != 0;

        Predicate<Person>
                personHasNotEmptyLastNameAndFirstName =
                person -> personHasNotEmptyFirstName.test(person) && personHasNotEmptyLastName.test(person);

        assertTrue(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)));
    }

    @Test
    public void personHasNotEmptyLastNameAndFirstNameUsingStandardMethods() {
        Predicate<Person> personHasEmptyFirstName = person -> {
            return person.getFirstName().length() == 0;
        };
        Predicate<Person> personHasEmptyLastName = person -> {
            return person.getLastName().length() == 0;
        };

        // TODO использовать Predicate.negate
        Predicate<Person> personHasNotEmptyFirstName = negate(personHasEmptyFirstName);
        Predicate<Person> personHasNotEmptyLastName = negate(personHasEmptyLastName);

        // TODO использовать Predicate.and
        Predicate<Person>
                personHasNotEmptyLastNameAndFirstName =
                personHasNotEmptyLastName.and(personHasNotEmptyFirstName);

        assertTrue(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)));
    }

}
