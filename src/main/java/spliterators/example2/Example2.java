package spliterators.example2;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Example2 {

    public static void main(String[] args) {
        String[] data = {"a", "b", "c"};

        getIndexedStream(data).filter(pair -> pair.getIndex() > 1)
                              .map(IndexedValue::getValue2)
                              .forEachOrdered(System.out::println);
    }

    @NotNull
    private static Stream<IndexedValue<String>> getIndexedStream(String[] data) {
        return StreamSupport.stream(new IndexedArraySpliterator<>(data), false);
    }

}
