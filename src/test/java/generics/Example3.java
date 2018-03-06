package generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Example3 {

    @SuppressWarnings({"UnnecessaryLocalVariable", "unchecked"})
    public static void main(String[] args) {
        // Ковариантность
        String[] arr = {"a", "b", "c"};
        Object[] objectArr = arr;
//        objectArr[0] = 1;



        // Инвариантность
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
//        List<Object> objectList = list;
        List rawList = list;

        rawList.set(0, 10.5);
        System.out.println(rawList.get(0));

        // checkedCollections
        List<String> checkedList = Collections.checkedList(list, String.class);
        rawList = checkedList;
        rawList.set(1, 11.5);
    }
}
