package api.exercise;

import lambda.data.Person;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Exercise1 {

    enum Status {
        UNKNOWN,
        PENDING,
        COMMENTED,
        ACCEPTED,
        DECLINED
    }

    @Test
    public void acceptGreaterThan21OthersDecline() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Person ivan = new Person("Иван", "Стрельцов", 24);
        Person helen = new Person("Елена", "Рощина", 22);
        Map<Person, Status> candidates = new HashMap<>();
        candidates.put(alex, Status.PENDING);
        candidates.put(ivan, Status.PENDING);
        candidates.put(helen, Status.PENDING);

        // TODO реализация
        for (Map.Entry entry : candidates.entrySet()) {
            Person p = (Person) entry.getKey();
            int age = p.getAge();
            if (age <= 21) {
                candidates.replace(p, Status.DECLINED);
            } else {
                candidates.replace(p, Status.ACCEPTED);
            }
        }

        assertEquals(Status.ACCEPTED, candidates.get(ivan));
        assertEquals(Status.ACCEPTED, candidates.get(helen));
        assertEquals(Status.DECLINED, candidates.get(alex));
    }

    @Test
    public void acceptGreaterThan21OthersRemove() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Person ivan = new Person("Иван", "Стрельцов", 24);
        Person helen = new Person("Елена", "Рощина", 22);
        Map<Person, Status> candidates = new HashMap<>();
        candidates.put(alex, Status.PENDING);
        candidates.put(ivan, Status.PENDING);
        candidates.put(helen, Status.PENDING);

        candidates.put(new Person("a", "a", 19), Status.PENDING);
        candidates.put(new Person("b", "c", 16), Status.PENDING);
        candidates.put(new Person("b", "c", 5), Status.PENDING);

        // TODO реализация
        Map<Person, Status> expected = new HashMap<>();
        for (Map.Entry entry : candidates.entrySet()) {
            Person p = (Person) entry.getKey();
            int age = p.getAge();
            if (age > 21) {
                expected.put(p, Status.ACCEPTED);
                candidates.replace(p, Status.ACCEPTED);
            }

        }

        for (Iterator<Map.Entry<Person, Status>> it = candidates.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Person, Status> entry = it.next();
            if (entry != null) {
                if (!entry.getValue().equals(Status.ACCEPTED)) {
                    it.remove();
                }
            }
        }

        expected.put(ivan, Status.ACCEPTED);
        expected.put(helen, Status.ACCEPTED);
        assertEquals(expected, candidates);
    }

    @Test
    public void getStatus() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Person ivan = new Person("Иван", "Стрельцов", 24);
        Person helen = new Person("Елена", "Рощина", 22);
        Map<Person, Status> candidates = new HashMap<>();
        candidates.put(alex, Status.PENDING);
        candidates.put(ivan, Status.PENDING);

        // TODO реализация
        Status alexStatus = null;
        Status ivanStatus = null;
        Status helenStatus = null;

        assertEquals(Status.PENDING, alexStatus);
        assertEquals(Status.PENDING, ivanStatus);
        assertEquals(Status.UNKNOWN, helenStatus);
    }

    @Test
    public void putToNewValuesIfNotExists() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Person ivan = new Person("Иван", "Стрельцов", 24);
        Person helen = new Person("Елена", "Рощина", 22);
        Person dmitry = new Person("Дмитрий", "Егоров", 30);
        Map<Person, Status> oldValues = new HashMap<>();
        oldValues.put(alex, Status.PENDING);
        oldValues.put(dmitry, Status.DECLINED);
        oldValues.put(ivan, Status.ACCEPTED);

        Map<Person, Status> newValues = new HashMap<>();
        newValues.put(alex, Status.DECLINED);
        newValues.put(helen, Status.PENDING);

        // TODO реализация

        assertEquals(Status.DECLINED, newValues.get(alex));
        assertEquals(Status.ACCEPTED, newValues.get(ivan));
        assertEquals(Status.PENDING, newValues.get(helen));
    }
}
