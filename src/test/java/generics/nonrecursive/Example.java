package generics.nonrecursive;

public class Example {

    public static void main(String[] args) {
        BaseStream<Integer> stream = null;
        BaseStream<Integer> sequential = stream.sequential()
                                               .parallel()
                                               .sequential()
                                               .sequential();

        Stream<String> stream1 = null;
        BaseStream<String> sequential1 = stream1.map().limit(10).skip(10).sequential().parallel().sequential();
    }
}
