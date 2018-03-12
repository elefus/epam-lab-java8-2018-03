package lambda.part1.example;

import lambda.data.Person;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings({"unused", "ComparatorCombinators"})
public class Example9 {

    private static class ComparatorPersonsByLastName implements Comparator<Person>, Serializable {

        @Override
        public int compare(Person left, Person right) {
            return left.getLastName().compareTo(right.getLastName());
        }
    }

    @Test
    public void serializeTree() {
        Set<Person> treeSet = new TreeSet<>(
                (Comparator<? super Person> & Serializable)(left, right) -> left.getLastName().compareTo(right.getLastName()));
        treeSet.add(new Person("Иван", "Мельников", 33));
        treeSet.add(new Person("Алексей", "Игнатенко", 1));
        treeSet.add(new Person("Сергей", "Лопатин", 3));

//        System.out.println(treeSet);

        // TODO сериализовать дерево в массив байт
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(treeSet);
            System.out.println(new String(out.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
