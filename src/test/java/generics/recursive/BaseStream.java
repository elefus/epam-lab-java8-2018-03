package generics.recursive;

public interface BaseStream<T, S extends BaseStream<T, S>> {

    S parallel();
    S sequential();
}
