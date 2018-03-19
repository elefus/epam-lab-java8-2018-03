package lambda.part2.exercise;

import lambda.data.Person;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

import static org.junit.Assert.*;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise1 {

    @Test
    public void ageExtractorFromPersonUsingMethodReference() {
        Person person = new Person("Иван", "Мельников", 33);

        // TODO создать переменную ageExtractor: Person -> Integer, используя Function и ссылку на метод
        Function<Person, Integer> ageExtractor = Person::getAge;

        assertEquals(33, ageExtractor.apply(person).intValue());
    }

    @Test
    public void sameAgesCheckerUsingBiPredicate() {
        Person person1 = new Person("Иван", "Мельников", 33);
        Person person2 = new Person("Дмитрий", "Гущин", 33);
        Person person3 = new Person("Илья", "Жирков", 22);

        // TODO создать переменную sameAgesChecker: (Person, Person) -> boolean, используя BiPredicate
        BiPredicate<Person, Person> sameAgesChecker = (o1, o2) -> o1.getAge() == o2.getAge();

        assertTrue(sameAgesChecker.test(person1, person2));
        assertFalse(sameAgesChecker.test(person1, person3));
        assertFalse(sameAgesChecker.test(person2, person3));
    }

    // TODO метод getFullName: Person -> String, извлекающий из объекта Person строку в формате "имя фамилия".
    // private static ... getFullName(...) {

    // TODO метод createExtractorAgeOfPersonWithTheLongestFullName: (Person -> String) -> ((Person, Person) -> int),
    // TODO - принимающий способ извлечения полного имени из объекта Person
    // TODO - возвращающий BiFunction, сравнивающий два объекта Person и возвращающий возраст того, чье полное имя длиннее.
    // private static ... createExtractorAgeOfPersonWithTheLongestFullName(...) {

    private String getFullName(Person person) {
        return person.getFirstName() + " " + person.getLastName();
    }

    private BiFunction<Person, Person, Integer> createExtractorAgeOfPersonWithTheLongestFullName(Function<Person, String> fullNameExtractor) {
        return (o1, o2) -> fullNameExtractor.apply(o1).length() > fullNameExtractor.apply(o2).length() ? o1.getAge() : o2.getAge();
    }

    @Test
    public void getAgeOfPersonWithTheLongestFullName() {
        Person person1 = new Person("Иван", "Мельников", 33);
        Person person2 = new Person("Илья", "Жирков", 22);

        // TODO воспользоваться ссылкой на метод getFullName
        Function<Person, String> getFullName = this::getFullName;

        // (Person, Person) -> Integer
        // TODO воспользоваться методом createExtractorAgeOfPersonWithTheLongestFullName
        BiFunction<Person, Person, Integer> extractorAgeOfPersonWithTheLongestFullName = createExtractorAgeOfPersonWithTheLongestFullName(getFullName);

        assertEquals(33, extractorAgeOfPersonWithTheLongestFullName.apply(person1, person2).intValue());
    }
}
