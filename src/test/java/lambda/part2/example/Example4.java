package lambda.part2.example;

import org.junit.Test;

import java.util.function.Function;
import java.util.function.IntFunction;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"UnnecessaryLocalVariable", "CodeBlock2Expr"})
public class Example4 {

    // (int, int, int) -> int
    private static int sum(int x, int y, int z) {
        return x + y + z;
    }

    // () -> (Integer -> (Integer -> (Integer -> Integer)))
    private static Function<Integer, Function<Integer, Function<Integer, Integer>>> curriedSum3UsingFunction() {
        return (Integer x) -> y -> z -> sum(x, y, z);
    }

    // () -> (int -> (int -> (int -> Integer)))
    private static IntFunction<IntFunction<IntFunction<Integer>>> curriedSum3UsingIntFunction() {
        return x -> y -> z -> sum(x, y, z);
    }

    @Test
    public void testCurriedSum3UsingFunction() {
        int result = sum(1, 2, 3);
        assertEquals(6, result);


        Function<Integer, Function<Integer, Function<Integer, Integer>>> curried3sum = curriedSum3UsingFunction();
        assertEquals(6, curried3sum.apply(1).apply(2).apply(3).intValue());
    }

    @Test
    public void testCurriedSum3UsingIntFunction() {
        IntFunction<IntFunction<IntFunction<Integer>>> curried3sum = curriedSum3UsingIntFunction();

        assertEquals(6, curried3sum.apply(1).apply(2).apply(3).intValue());
    }

    // (String, String) -> String
    private static String sum(String left, String right) {
        return left + right;
    }

    // () -> (String -> (String -> String))
    private static Function<String, Function<String, String>> curriedRevertedSumString() {
        return right -> left -> sum(left, right);
    }

    @Test
    public void testCurriedRevertedSumString() {
        Function<String, Function<String, String>> curriedRevertedSum = curriedRevertedSumString();

        assertEquals("ab", curriedRevertedSum.apply("b").apply("a"));
    }
}
