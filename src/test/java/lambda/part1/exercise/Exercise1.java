package lambda.part1.exercise;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import lambda.data.Person;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Exercise1 {
	
	@Test
	public void sortPersonsByAgeUsingArraysSortComparator() {
		Person[] persons = getPersons();
		
		class PersonAgeComarator implements Comparator<Person> {
			
			@Override
			public int compare(Person o1, Person o2) {
				return Integer.compare(o1.getAge(), o2.getAge());
			}
		}
		// TODO использовать Arrays.sort
		
		
		assertArrayEquals(new Person[]{
				new Person("Иван", "Мельников", 20),
				new Person("Николай", "Зимов", 30),
				new Person("Алексей", "Доренко", 40),
				new Person("Артем", "Зимов", 45)}, persons);
		Arrays.parallelSort(persons);
	}
	
	@Test
	public void sortPersonsByAgeUsingArraysSortAnonymousComparator() {
		Person[] persons = getPersons();
		
		Arrays.sort(persons, Comparator.comparingInt(Person::getAge));
		// TODO использовать Arrays.sort
		
		assertArrayEquals(new Person[]{
				new Person("Иван", "Мельников", 20),
				new Person("Николай", "Зимов", 30),
				new Person("Алексей", "Доренко", 40),
				new Person("Артем", "Зимов", 45)}, persons);
	}
	
	@Test
	public void sortPersonsByLastNameThenFirstNameUsingArraysSortAnonymousComparator() {
		Person[] persons = getPersons();
		
		Arrays.sort(persons, new Comparator<Person>() {
			@Override
			public int compare(Person o1, Person o2) {
				return o1.getLastName().compareTo(o2.getLastName()) == 0 ? 0 :
					   o1.getFirstName().compareTo(o2.getFirstName());
			}
		});
		// TODO использовать Arrays.sort
		
		assertArrayEquals(new Person[]{
				new Person("Алексей", "Доренко", 40),
				new Person("Артем", "Зимов", 45),
				new Person("Иван", "Мельников", 20),
				new Person("Николай", "Зимов", 30)}, persons);
	}
	
	@Test
	public void findFirstWithAge30UsingGuavaPredicate() {
		List<Person> persons = Arrays.asList(getPersons());
		Predicate personPredicate = new Predicate<Person>() {
			@Override
			public boolean apply(Person inputPerson) {
				return (inputPerson.getAge() == 30);
			}
		};
		Optional<Person> personOptional = FluentIterable.from(persons).firstMatch(personPredicate);
		Person person = personOptional.get();
		System.out.println(personOptional.get());
		assertEquals(new Person("Николай", "Зимов", 30), person);
		// TODO использовать FluentIterable
		
	}
	
	@Test
	public void findFirstWithAge30UsingGuavaAnonymousPredicate() {
		List<Person> persons = Arrays.asList(getPersons());
		
		Optional<Person> personOptional =
				FluentIterable.from(persons).firstMatch((new Predicate<Person>() {
					@Override
					public boolean apply(Person inputPerson) {
						return (inputPerson.getAge() == 30);
					}
				}));
		if (personOptional.isPresent()) {
			System.out.println(personOptional.get());
			assertEquals(new Person("Николай", "Зимов", 30), personOptional.get());
		}
		// TODO использовать FluentIterable
		Person person = personOptional.get();
		
		assertEquals(new Person("Николай", "Зимов", 30), person);
	}
	
	private Person[] getPersons() {
		
		
		return new Person[]{
				new Person("Иван", "Мельников", 20),
				new Person("Николай", "Зимов", 30),
				new Person("Алексей", "Доренко", 40),
				new Person("Артем", "Зимов", 45)};
	}
}
