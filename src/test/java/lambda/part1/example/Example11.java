package lambda.part1.example;

import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static junit.framework.TestCase.assertNotSame;
import static junit.framework.TestCase.assertSame;

@SuppressWarnings({"unused", "ComparatorCombinators"})
public class Example11 {

    private static void method(String str) {
        throw new RuntimeException();
    }

    @SuppressWarnings({"Convert2Lambda", "Anonymous2MethodRef"})
    private static Consumer<String> anonymousConsumer() {
        return new Consumer<String>() {
            @Override
            public void accept(String string) {
                method(string);
            }
        };
    }

    @SuppressWarnings("Convert2MethodRef")
    private static Consumer<String> lambdaConsumer() {
        return string -> method(string);
    }

    private static Consumer<String> referenceConsumer() {
        return Example11::method;
    }

    @SuppressWarnings("Convert2MethodRef")
    private static Supplier<Integer> lengthExtractor(String str) {
        return () -> str.length();
    }


    private final String str = "123";

    private Supplier<String> getString() {
        return () -> this.str;
    }

    @Test
    public void test() {
        Consumer<String> consumer1 = anonymousConsumer();
        Consumer<String> consumer2 = anonymousConsumer();
        assertNotSame(consumer1, consumer2);

        Consumer<String> consumer3 = lambdaConsumer();
        Consumer<String> consumer4 = lambdaConsumer();
        assertSame(consumer3, consumer4);

        Consumer<String> consumer5 = referenceConsumer();
        Consumer<String> consumer6 = referenceConsumer();
        consumer5.accept("123");
        assertSame(consumer5, consumer6);

        Supplier<Integer> supplier1 = lengthExtractor("qwe");
        Supplier<Integer> supplier2 = lengthExtractor("abc");
        assertNotSame(supplier1, supplier2);

        Supplier<String> string1 = getString();
        Supplier<String> string2 = getString();
        assertNotSame(string1, string2);
    }
}
