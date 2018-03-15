package lambda.part2.example;

import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"UnnecessaryLocalVariable", "CodeBlock2Expr"})
public class Example5 {

    // (String, String, String) -> String
    private static String sumWithDelimiter(String left, String delimiter, String right) {
        return left + delimiter + right;
    }

    // () -> String -> String -> String -> String
    private static Function<String, Function<String, Function<String, String>>> curriedSumWithDelimiter() {
        return left -> delimiter -> right -> sumWithDelimiter(left, delimiter, right);
    }

    @Test
    public void testCurriedSumWithDelimiter() {
        Function<String, Function<String, Function<String, String>>> curriedSumWithDelimiter = curriedSumWithDelimiter();

        assertEquals("a-b", curriedSumWithDelimiter.apply("a").apply("-").apply("b"));
    }

    // (String -> String -> String -> String, String) -> String -> String -> String
    private static Function<String, Function<String, String>> partiallyAppliedSumWithDelimiter(
            Function<String, Function<String, Function<String, String>>> summator,
            String delimiter) {
        return left -> right -> summator.apply(left).apply(delimiter).apply(right);
    }

    @Test
    public void testPartiallyAppliedSumWithDelimiter() {
        Function<String, Function<String, Function<String, String>>> summator = curriedSumWithDelimiter();
        Function<String, Function<String, String>> summatorWithDash = partiallyAppliedSumWithDelimiter(summator, "-");

        assertEquals("a-b", summatorWithDash.apply("a").apply("b"));
    }
}
