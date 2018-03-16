package lambda.part2.exercise;

import lambda.data.Person;
import org.junit.Test;

import java.util.function.Predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings({"ConstantConditions", "unused"})
public class Exercise2 {

    private static final Predicate<Person> PERSON_HAS_EMPTY_FIRST_NAME = person -> "".equals(person.getFirstName());
    private static final Predicate<Person> PERSON_HAS_EMPTY_LAST_NAME = person -> "".equals(person.getLastName());

    @Test
    public void personHasNotEmptyLastNameAndFirstName() {
        // предикат Person -> boolean, проверяющий что имя и фамилия человека не пусты
        Predicate<Person> validate = Exercise2::checkExistenceNamePerson;

        assertTrue(validate.test(new Person("Алексей", "Доренко", 40)));
        assertFalse(validate.test(new Person("Николай", "", 30)));
        assertFalse(validate.test(new Person("", "Мельников", 20)));
        assertFalse(validate.test(new Person("", "", 20)));
    }
    private static boolean checkExistenceNamePerson(Person person){
        return !"".equals(person.getLastName()) && !"".equals(person.getFirstName());
    }

    // метод (Person -> boolean) -> (Person -> boolean)
    //  возвращает новый предикат, являющийся отрицанием исходного
    //  при реализации использовать логический оператор !
    private Predicate<Person> negateUsingLogicalOperator(Predicate<Person> predicate) {
        return  person->!predicate.test(person);
    }

    //  метод (Person -> boolean, Person -> boolean) -> (Person -> boolean)
    //  возвращает новый предикат, объединяющий исходные с помощью операции "AND"
    //  при реализации использовать логический оператор &&
    private Predicate<Person> andUsingLogicalOperator(Predicate<Person> left, Predicate<Person> right) {
        return person -> left.test(person) && right.test(person);
    }

    @Test
    public void personHasNotEmptyLastNameAndFirstNameUsingLogicalOperators() {
        Predicate<Person> personHasEmptyFirstName = PERSON_HAS_EMPTY_FIRST_NAME;
        Predicate<Person> personHasEmptyLastName = PERSON_HAS_EMPTY_LAST_NAME;

        Predicate<Person> personHasNotEmptyFirstName = negateUsingLogicalOperator(personHasEmptyFirstName);
        Predicate<Person> personHasNotEmptyLastName = negateUsingLogicalOperator(personHasEmptyLastName);

        Predicate<Person> personHasNotEmptyLastNameAndFirstName = andUsingLogicalOperator(personHasNotEmptyFirstName,
                personHasNotEmptyLastName);

        assertTrue(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)));
    }

    //  метод (T -> boolean) -> (T -> boolean)
    // возвращает новый предикат, являющийся отрицанием исходного
    //  при реализации использовать логический оператор !
    private <T> Predicate<T> negate(Predicate<T> predicate) {
        return typeT->!predicate.test(typeT);
    }

    //  метод (T -> boolean, T -> boolean) -> (T -> boolean)
    //  возвращает новый предикат, объединяющий исходные с помощью операции "AND"
    //  при реализации использовать логический оператор &&
    private <T> Predicate<T> and(Predicate<T> left, Predicate<T> right) {
        return typeT->left.test(typeT) && right.test(typeT);
    }

    @Test
    public void personHasNotEmptyLastNameAndFirstNameUsingGenericPredicates() {
        Predicate<Person> personHasEmptyFirstName = PERSON_HAS_EMPTY_FIRST_NAME;
        Predicate<Person> personHasEmptyLastName = PERSON_HAS_EMPTY_LAST_NAME;

        Predicate<Person> personHasNotEmptyFirstName = negate(personHasEmptyFirstName);
        Predicate<Person> personHasNotEmptyLastName = negate(personHasEmptyLastName);

        Predicate<Person> personHasNotEmptyLastNameAndFirstName = and(personHasNotEmptyFirstName,personHasNotEmptyLastName);

        assertTrue(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)));
    }

    @Test
    public void personHasNotEmptyLastNameAndFirstNameUsingStandardMethods() {
        Predicate<Person> personHasEmptyFirstName = PERSON_HAS_EMPTY_FIRST_NAME;
        Predicate<Person> personHasEmptyLastName = PERSON_HAS_EMPTY_LAST_NAME;

        //  использовать Predicate.negate
        Predicate<Person> personHasNotEmptyFirstName = personHasEmptyFirstName.negate();
        Predicate<Person> personHasNotEmptyLastName =personHasEmptyLastName.negate();

        //  использовать Predicate.and
        Predicate<Person> personHasNotEmptyLastNameAndFirstName = personHasNotEmptyFirstName.and(personHasNotEmptyLastName);

        assertTrue(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)));
        assertFalse(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)));
    }

}
