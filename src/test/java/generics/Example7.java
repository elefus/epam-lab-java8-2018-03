package generics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Example7 {

    public static void main(String[] args) {
        List<? extends Number> list1 = getList1();
//        list1.add(10);
//        list1.add(10L);
//        list1.add(10.5);
//        list1.add("123");
//        Number num = 1;
//        list1.add(num);
        list1.add(null);
        Number number = list1.get(0);

        List<? super MyNumber> list2 = new ArrayList<Number>();
        Object object = list2.get(0);


        // Producer
        // Extends
        // Consumer
        // Super

        Comparator<String> stringComparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        };

        Comparator<Object> objectComparator = new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return o1.hashCode() - o2.hashCode();
            }
        };

        method(stringComparator);
        method(objectComparator);
    }

    private static void method(Comparator<? super String> comparator) {
        String a = "a";
        String b = "b";
        int compare = comparator.compare(a, b);
    }

    private static List<? extends Number> getList1() {
        return new ArrayList<Number>();
    }
}

class MyNumber extends Number {

    @Override
    public int intValue() {
        return 0;
    }

    @Override
    public long longValue() {
        return 0;
    }

    @Override
    public float floatValue() {
        return 0;
    }

    @Override
    public double doubleValue() {
        return 0;
    }
}

class MyChildNumber extends MyNumber {

}