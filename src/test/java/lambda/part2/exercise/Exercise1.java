package lambda.part2.exercise;

import lambda.data.Person;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.ToIntBiFunction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise1 {

    @Test
    public void ageExtractorFromPersonUsingMethodReference() {
        Person person = new Person("Иван", "Мельников", 33);

        Function<Person, Integer> ageExtractor = Person::getAge;

        assertEquals(33, ageExtractor.apply(person).intValue());

    }

    @Test
    public void sameAgesCheckerUsingBiPredicate() {
        Person person1 = new Person("Иван", "Мельников", 33);
        Person person2 = new Person("Дмитрий", "Гущин", 33);
        Person person3 = new Person("Илья", "Жирков", 22);

        BiPredicate<Person, Person> sameAgesChecker = (p1,p2) -> p1.getAge()==p2.getAge();
         assertTrue(sameAgesChecker.test(person1, person2));
         assertFalse(sameAgesChecker.test(person1, person3));
         assertFalse(sameAgesChecker.test(person2, person3));

    }

    //  метод getFullName: Person -> String, извлекающий из объекта Person строку в формате "имя фамилия".
     private static String getFullName(Person person) {
        return String.format("%s %s", person.getFirstName(), person.getLastName());
     }
    //  метод createExtractorAgeOfPersonWithTheLongestFullName: (Person -> String) -> ((Person, Person) -> int),
    //  принимающий способ извлечения полного имени из объекта Person
    //  возвращающий BiFunction, сравнивающий два объекта Person и возвращающий возраст того, чье полное имя длиннее.
    private static BiFunction<Person, Person, Integer> createExtractorAgeOfPersonWithTheLongestFullName(Function<Person, String> function) {
        return (p1, p2) -> {
            int lengthOfP1Name = function.apply(p1).length();
            int lengthOfP2Name = function.apply(p2).length();
            return lengthOfP1Name>=lengthOfP2Name ?p1.getAge():p2.getAge();
        };
    }

    @Test
    public void getAgeOfPersonWithTheLongestFullName() {
        Person person1 = new Person("Иван", "Мельников", 33);
        Person person2 = new Person("Илья", "Жирков", 22);

        //  воспользоваться ссылкой на метод getFullName
        Function<Person, String> getFullName = Exercise1::getFullName;

        // (Person, Person) -> Integer
        // воспользоваться методом createExtractorAgeOfPersonWithTheLongestFullName
        BiFunction<Person, Person, Integer> extractorAgeOfPersonWithTheLongestFullName =
                createExtractorAgeOfPersonWithTheLongestFullName(getFullName);

        assertEquals(33, extractorAgeOfPersonWithTheLongestFullName.apply(person1, person2).intValue());
    }
}
