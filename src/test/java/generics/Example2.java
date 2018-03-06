package generics;

import java.io.Closeable;
import java.io.File;
import java.io.Serializable;
import java.util.Collection;

public class Example2 {

    @SuppressWarnings("unchecked, UnusedAssignment")
    public static void main(String[] args) {
        Holder<Integer> intHolder = new Holder<>(42);

        Holder objectHolder = new Holder<>(0.45);

        objectHolder = intHolder;
        objectHolder.setValue(100.5);

        System.out.println(objectHolder.getValue());
        System.out.println(intHolder.getValue());

        Holder<Double> doubleHolder = objectHolder;
        Double value = doubleHolder.getValue();
        System.out.println(value);


        Object maxValue = max(null);

    }

    public static Object max(Collection collection) {
        return null;
    }
}

class Holder<T extends Number & Serializable> {

    private T value;

    public Holder(T value) {
        this.value = value;
    }

    public T getValue() {
//        System.out.println(value.intValue());
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}