package spliterators.example1;

import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Example1 {

    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
        Spliterator<Integer> spliterator = integers.spliterator();

        Stream<Integer> stream = StreamSupport.stream(spliterator, false);
    }

}
