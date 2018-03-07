package generics.recursive;

public interface Stream<T> extends BaseStream<T, Stream<T>> {

    Stream<T> limit(int n);
    Stream<T> skip(int n);
    Stream<T> map();


    long count();
}
