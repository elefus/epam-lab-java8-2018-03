package generics;

import java.lang.reflect.Method;

public class Example4 {

    @SuppressWarnings("JavaReflectionMemberAccess")
    public static void main(String[] args) throws NoSuchMethodException {
        // getMethodsUsingReflection
        for (Method method : Person.class.getDeclaredMethods()) {
            System.out.println(method);
        }

        Method compareToMethod = Person.class.getDeclaredMethod("compareTo", Object.class);
        System.out.println(compareToMethod.isSynthetic());
        System.out.println(compareToMethod.isBridge());
    }

    private static class Person implements Comparable<Person> {

        private String name;
        private String surname;
        private int age;

        @Override
        public int compareTo(Person other) {
            return Integer.compare(age, other.age);
        }
    }
}
