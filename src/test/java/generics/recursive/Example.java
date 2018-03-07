package generics.recursive;

public class Example {

    public static void main(String[] args) {
        Stream<String> stream1 = null;

        stream1.limit(10).map().sequential().map();

    }
}
