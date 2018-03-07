package lambda.part1.example;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import lambda.data.Person;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@SuppressWarnings({"Guava", "Convert2Lambda", "Anonymous2MethodRef"})
public class Example1 {

    private int field = 42;

    @Test
    public void sortPersonsByNameUsingArraysSortUsingLocalComparator() {
        Person[] persons = getPersons();

        class LastNameComparator implements Comparator<Person> {
            @Override
            public int compare(Person left, Person right) {
                // FIXME потенциальная ошибка
                return left.getLastName().compareTo(right.getLastName());
            }
        }
        Arrays.sort(persons, new LastNameComparator());

        assertArrayEquals(new Person[]{
            new Person("Алексей", "Доренко", 40),
            new Person("Николай", "Зимов", 30),
            new Person("Иван", "Мельников", 20)
        }, persons);
    }


    @Test
    public void sortPersonsByNameUsingArraysSortUsingAnonymousComparator() {
        Person[] persons = getPersons();

        Arrays.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person left, Person right) {
                // FIXME потенциальная ошибка
                return left.getLastName().compareTo(right.getLastName());
            }
        });

        assertArrayEquals(new Person[]{
            new Person("Алексей", "Доренко", 40),
            new Person("Николай", "Зимов", 30),
            new Person("Иван", "Мельников", 20)
        }, persons);
    }

    @Test
    public void findFirstAlexUsingForeach() {
        List<Person> persons = Arrays.asList(getPersons());

        Person person = null;
        for (Person current : persons) {
            // FIXME потенциальная ошибка
            if ("Алексей".equals(current.getFirstName())) {
                person = current;
                break;
            }
        }

        if (person != null) {
            System.out.println(person);
        }

        assertEquals(new Person("Алексей", "Доренко", 40), person);
    }

    /**
     * Google Guava – библиотека для Java, предоставляющая дополнительные возможности при работе с коллекциями, IO, кэшами.
     * @see <a href="https://github.com/google/guava">Официальный сайт проекта Guava</a>
     */
    @Test
    public void findFirstAlexUsingGuavaPredicate() {
        List<Person> persons = Arrays.asList(getPersons());

        Predicate<Person> isFirstNameAlexChecker = new Predicate<Person>() {
            @Override
            public boolean apply(Person person) {
                return "Алексей".equals(person.getFirstName());
            }
        };
        Optional<Person> personOptional = FluentIterable.from(persons)
                                                        .firstMatch(isFirstNameAlexChecker);

        if (personOptional.isPresent()) {
            System.out.println(personOptional.get());
            assertEquals(new Person("Алексей", "Доренко", 40), personOptional.get());
        }
    }

    @Test
    public void findFirstAlexUsingGuavaAnonymousPredicate() {
        List<Person> persons = Arrays.asList(getPersons());

        Optional<Person> personOptional = FluentIterable.from(persons)
                                                        .firstMatch(new Predicate<Person>() {
                                                            @Override
                                                            public boolean apply(Person p) {
                                                                return "Алексей".equals(p.getFirstName());
                                                            }
                                                        });

        if (personOptional.isPresent()) {
            System.out.println(personOptional.get());
            assertEquals(new Person("Алексей", "Доренко", 40), personOptional.get());
        }
    }

    @Test
    public void createMapFromListUsingForeach() {
        List<Person> persons = Arrays.asList(getPersons());

        Map<String, Person> personByLastName = new HashMap<>(persons.size());
        for (Person person : persons) {
            personByLastName.put(person.getLastName(), person);
        }

        Map<String, Person> expected = new HashMap<>(persons.size());
        expected.put("Мельников", new Person("Иван", "Мельников", 20));
        expected.put("Доренко", new Person("Алексей", "Доренко", 40));
        expected.put("Зимов", new Person("Николай", "Зимов", 30));
        assertEquals(expected, personByLastName);
    }

    @Test
    public void createMapFromListUsingGuavaFunction() {
        List<Person> persons = Arrays.asList(getPersons());

        Function<Person, String> getLastNameFromPerson = new Function<Person, String>() {
            @Override
            public String apply(Person person) {
                return person.getLastName();
            }
        };
        // Упадет, если одному ключу будет соответствовать два элемента.
        Map<String, Person> personByLastName = FluentIterable.from(persons)
                                                             .uniqueIndex(getLastNameFromPerson);

        Map<String, Person> expected = new HashMap<>(persons.size());
        expected.put("Мельников", new Person("Иван", "Мельников", 20));
        expected.put("Доренко", new Person("Алексей", "Доренко", 40));
        expected.put("Зимов", new Person("Николай", "Зимов", 30));
        assertEquals(expected, personByLastName);
    }

    @Test
    public void createMapFromListUsingGuavaAnonymousFunction() {
        List<Person> persons = Arrays.asList(getPersons());

        Map<String, Person> personByLastName = FluentIterable.from(persons)
                                                             .uniqueIndex(new Function<Person, String>() {
                                                                 @Override
                                                                 public String apply(Person person) {
                                                                     return person.getLastName();
                                                                 }
                                                             });

        Map<String, Person> expected = new HashMap<>(persons.size());
        expected.put("Мельников", new Person("Иван", "Мельников", 20));
        expected.put("Доренко", new Person("Алексей", "Доренко", 40));
        expected.put("Зимов", new Person("Николай", "Зимов", 30));
        assertEquals(expected, personByLastName);
    }

    private Person[] getPersons() {
        return new Person[]{
            new Person("Иван", "Мельников", 20),
            new Person("Алексей", "Доренко", 40),
            new Person("Николай", "Зимов", 30)
        };
    }
}
