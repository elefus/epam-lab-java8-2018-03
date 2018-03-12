package lambda.part1.example;

import lambda.data.Person;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings({"unused", "ComparatorCombinators"})
public class Example9 {

    private static class FullNameComparator implements Comparator<Person> {

        @Override
        public int compare(Person o1, Person o2) {
            return o1.getFullName().compareTo(o2.getFullName());
        }
    }

    @Test
    public void serializeTree() {
//        Set<Person> treeSet = new TreeSet<>(
//                (Comparator<? super Person> & Serializable)(left, right) -> left.getLastName().compareTo(right.getLastName()));
        Set<Person> treeSet = new TreeSet<>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return 0;
            }
        });
        treeSet.add(new Person("Иван", "Мельников", 33));
        treeSet.add(new Person("Алексей", "Игнатенко", 1));
        treeSet.add(new Person("Сергей", "Лопатин", 3));

        System.out.println(treeSet);

        // TODO сериализовать дерево в массив байт
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream)) {
            objectOutputStream.writeObject(treeSet);
            System.out.println(Arrays.toString(stream.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
