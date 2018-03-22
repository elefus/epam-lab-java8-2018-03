package lambda.part1.exercise;

import com.google.common.collect.FluentIterable;
import lambda.data.Person;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Exercise1 {

    @Test
    public void sortPersonsByAgeUsingArraysSortComparator() {
        Person[] persons = getPersons();

        class PersonComparator implements Comparator<Person> {

            @Override
            public int compare(Person o1, Person o2) {
                return Integer.compare(o1.getAge(), o2.getAge());
            }
        }

        // TODO использовать Arrays.sort
        Arrays.sort(persons, new PersonComparator());

        assertArrayEquals(new Person[]{
            new Person("Иван", "Мельников", 20),
            new Person("Николай", "Зимов", 30),
            new Person("Алексей", "Доренко", 40),
            new Person("Артем", "Зимов", 45)
        }, persons);
    }

    @Test
    public void sortPersonsByAgeUsingArraysSortAnonymousComparator() {
        Person[] persons = getPersons();

        // TODO использовать Arrays.sort
        Arrays.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return Integer.compare(o1.getAge(), o2.getAge());
            }
        });

        assertArrayEquals(new Person[]{
            new Person("Иван", "Мельников", 20),
            new Person("Николай", "Зимов", 30),
            new Person("Алексей", "Доренко", 40),
            new Person("Артем", "Зимов", 45)
        }, persons);
    }

    @Test
    public void sortPersonsByLastNameThenFirstNameUsingArraysSortAnonymousComparator() {
        Person[] persons = getPersons();

        // TODO использовать Arrays.sort
        Arrays.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getLastName().compareTo(o2.getLastName());
            }
        }.thenComparing(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getFirstName().compareTo(o2.getFirstName());
            }
        }));

        assertArrayEquals(new Person[]{
            new Person("Алексей", "Доренко", 40),
            new Person("Артем", "Зимов", 45),
            new Person("Николай", "Зимов", 30),
            new Person("Иван", "Мельников", 20)
        }, persons);
    }

    @Test
    public void findFirstWithAge30UsingGuavaPredicate() {
        List<Person> persons = Arrays.asList(getPersons());

        // TODO использовать FluentIterable
        Person person = null;
        Predicate<Person> predicate = new Predicate<Person>(){
            @Override
            public boolean apply(Person person) {
                return person.getAge()==30;
            }
        };
        Optional<Person> optionalPerson = FluentIterable.from(persons).firstMatch(predicate);

        person = optionalPerson.get();

        assertEquals(new Person("Николай", "Зимов", 30), person);
    }

    @Test
    public void findFirstWithAge30UsingGuavaAnonymousPredicate() {
        List<Person> persons = Arrays.asList(getPersons());

        // TODO использовать FluentIterable
        Person person = null;

        Optional<Person> optional = FluentIterable.from(persons).firstMatch(new Predicate<Person>() {
            @Override
            public boolean apply(Person person) {
                return person.getAge() == 30;
            }
        });

        person = optional.get();

        assertEquals(new Person("Николай", "Зимов", 30), person);
    }

    private Person[] getPersons() {
        return new Person[]{
            new Person("Иван", "Мельников", 20),
            new Person("Алексей", "Доренко", 40),
            new Person("Николай", "Зимов", 30),
            new Person("Артем", "Зимов", 45)
        };
    }
}
