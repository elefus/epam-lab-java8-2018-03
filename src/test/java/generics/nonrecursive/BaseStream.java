package generics.nonrecursive;

public interface BaseStream<E> {

    BaseStream<E> parallel();
    BaseStream<E> sequential();
}
