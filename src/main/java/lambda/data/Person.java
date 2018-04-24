package lambda.data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Immutable.
 */
public class Person implements Comparable<Person>, Serializable {

    private final String firstName;
    private final String lastName;
    private final int age;

    /**
     * @param firstName Имя.
     * @param lastName Фамилия.
     * @param age Возраст.
     */
    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Person() {
        firstName = null;
        lastName = null;
        age = 0;
    }

    // Person -> String
    // () -> String
    public String getFirstName(Person this) {
        return firstName;
    }

    // Person -> String
    public String getLastName() {
        return this.lastName;
    }

    // String -> int
    // (Person, String) -> int
    public int convert(Person this, String val) {
        return 0;
    }

    public static String getLastNameStatic(Person person) {
        return person.getLastName();
    }

    public int getAge(Person this) {
        return age;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Person person = (Person) other;
        return age == person.age
            && Objects.equals(firstName, person.firstName)
            && Objects.equals(lastName, person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age);
    }

    @Override
    public String toString() {
        return "Person@" + hashCode() + ": {"
             + "firstName='" + firstName + "', "
             + "lastName='" + lastName + "', "
             + "age=" + age + "}";
    }

    @Override
    public int compareTo(Person other) {
        return Integer.compare(age, other.age);
    }
}
