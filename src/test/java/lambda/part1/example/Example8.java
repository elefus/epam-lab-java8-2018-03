package lambda.part1.example;

import lambda.data.Person;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "Convert2MethodRef"})
public class Example8 {

    @FunctionalInterface
    private interface PersonFactory {

        Person create(String name, String lastName, int age);
    }

    @Test
    public void factoryAsExpressionLambda() {
        PersonFactory factory = (name, lastName, age) -> new Person(name, lastName, age);

        Person actual = factory.create("Иван", "Мельников", 33);

        assertEquals(new Person("Иван", "Мельников", 33), actual);
    }

    @Test
    public void factoryAsReferenceToConstructor() {
        PersonFactory factory = Person::new;

        Person actual = factory.create("Иван", "Мельников", 33);

        assertEquals(new Person("Иван", "Мельников", 33), actual);
    }
}
