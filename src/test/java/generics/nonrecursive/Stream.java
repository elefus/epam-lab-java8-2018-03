package generics.nonrecursive;

public interface Stream<E> extends BaseStream<E> {

    Stream<E> limit(int n);
    Stream<E> skip(int n);
    Stream<E> map();


    long count();
}
