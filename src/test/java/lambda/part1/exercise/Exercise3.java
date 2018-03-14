package lambda.part1.exercise;

import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import lambda.data.Person;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Exercise3 {
	
	@Test
	public void sortPersonsByAgeUsingArraysSortExpressionLambda() {
		Person[] persons = getPersons();
		Arrays.sort(persons, (left, rigth) -> left.getAge() > rigth.getAge() ? 1 :
											  left.getAge() < rigth.getAge() ? -1 : 0);
		// TODO использовать Arrays.sort + expression-lambda
		
		assertArrayEquals(new Person[]{
				new Person("Иван", "Мельников", 20),
				new Person("Николай", "Зимов", 30),
				new Person("Алексей", "Доренко", 40),
				new Person("Артем", "Зимов", 45)}, persons);
	}
	
	@Test
	public void sortPersonsByLastNameThenFirstNameUsingArraysSortExpressionLambda() {
		Person[] persons = getPersons();
		Arrays.sort(persons,
					(left, right) -> left.getLastName().compareTo(right.getLastName()) == 0 ?
									 left.getFirstName().compareTo(right.getFirstName()) :
									 left.getLastName().compareTo(right.getLastName()));
		// TODO использовать Arrays.sort + statement-lambda
		
		assertArrayEquals(new Person[]{
				new Person("Алексей", "Доренко", 40),
				new Person("Артем", "Зимов", 45),
				new Person("Николай", "Зимов", 30),
				new Person("Иван", "Мельников", 20)}, persons);
	}
	
	@Test
	public void findFirstWithAge30UsingGuavaPredicateLambda() {
		List<Person> persons = Arrays.asList(getPersons());
		
		Optional<Person> optionalPerson =
				FluentIterable.from(persons).firstMatch(input -> input.getAge() == 30);
		// TODO использовать FluentIterable
		Person person = optionalPerson.get();
		
		assertEquals(new Person("Николай", "Зимов", 30), person);
	}
	
	private Person[] getPersons() {
		return new Person[]{
				new Person("Иван", "Мельников", 20),
				new Person("Алексей", "Доренко", 40),
				new Person("Николай", "Зимов", 30),
				new Person("Артем", "Зимов", 45)};
	}
}
