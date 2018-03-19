package optional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Optional<T> {

    private static final Optional<?> EMPTY = new Optional<>();
    private final T value;

    private Optional() {
        value = null;
    }

    private Optional(T value) {
        this.value = Objects.requireNonNull(value);
    }

    public static <T> Optional<T> of(T value) {
        return new Optional<>(value);
    }

    public static <T> Optional<T> ofNullable(T value) {
        return value == null ? empty() : new Optional<>(value);
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<T> empty() {
        return (Optional<T>) EMPTY;
    }

    public boolean isPresent() {
        return value != null;
    }

    public T get() {
        if (!isPresent()) {
            throw new NoSuchElementException();
        }
        return value;
    }

    public void ifPresent(Consumer<T> action) {
        if (isPresent()) {
            action.accept(value);
        }
    }

    public T orElse(T defaultValue) {
        return isPresent() ? value : defaultValue;
    }

    public T orElseGet(Supplier<T> defaultValue) {
        return isPresent() ? value : defaultValue.get();
    }

    public <X extends Throwable> T orElseThrow(Supplier<X> throwableSupplier) throws X {
        if (!isPresent()) {
            throw throwableSupplier.get();
        }
        return value;
    }

    public Optional<T> filter(Predicate<T> predicate) {
        if (isPresent()) {
            return predicate.test(value) ? this : empty();
        }
        return this;
    }

    public <R> Optional<R> map(Function<T, R> remapping) {
        if (isPresent()) {
            return ofNullable(remapping.apply(value));
        }
        return empty();
    }

    public <R> Optional<R> flatMap(Function<T, Optional<R>> mapper) {
        return isPresent() ? mapper.apply(value) : empty();
    }
}

class Runner {

    public static void main(String[] args) {
        Optional<String> optional = Optional.of("123");


        if (optional.isPresent()) {
            System.out.println(optional.get());
        }

        optional.ifPresent(System.out::println);

        optional.orElse("abc");
        optional.orElseGet(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });

        Optional<String> filter = optional.filter(s -> s.length() > 2);
        filter.orElseThrow(IllegalStateException::new);

    }

    private static Optional<String> getValue() {
        return ThreadLocalRandom.current().nextBoolean() ? Optional.of("123") : Optional.empty();
    }
}
