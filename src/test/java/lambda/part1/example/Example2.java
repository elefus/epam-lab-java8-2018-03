package lambda.part1.example;

import interfaces.Summator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"Convert2Lambda", "FieldCanBeLocal", "CodeBlock2Expr", "unused", "Convert2MethodRef"})
public class Example2 {

    @Test
    public void anonymousClassImplementation() {
        Summator<Integer> summator = new Summator<Integer>() {
            @Override
            public Integer sum(Integer left, Integer right) {
                return left + right;
            }
        };

        assertEquals(3, summator.sum(1, 2).intValue());
        assertEquals(1, summator.sum(-1, 2).intValue());
        assertEquals(4, summator.twice(2).intValue());
        assertEquals(0, summator.twice(0).intValue());
    }

    @Test
    public void statementLambdaImplementation() {
        // FIXME вывод типов
        Summator<Integer> summator = (Integer left, Integer right) -> {
            System.out.println("");
            new Thread().start();
            return left + right;
        };

        assertEquals(3, summator.sum(1, 2).intValue());
        assertEquals(1, summator.sum(-1, 2).intValue());
        assertEquals(4, summator.twice(2).intValue());
        assertEquals(0, summator.twice(0).intValue());
    }

    @Test
    public void expressionLambdaImplementation() {
        Summator<Integer> summator = (a, b) -> a + b;

        assertEquals(3, summator.sum(1, 2).intValue());
        assertEquals(1, summator.sum(-1, 2).intValue());
        assertEquals(4, summator.twice(2).intValue());
        assertEquals(0, summator.twice(0).intValue());
    }

    private static String stringSum(String left, String right) {
        return left + right;
    }

    @Test
    public void classMethodReferenceLambdaImplementation() {
        Summator<String> summator = Example2::stringSum;

        assertEquals("ab", summator.sum("a", "b"));
        assertEquals("aa", summator.twice("a"));
    }

    private final String delimiter = "-";

    private String stringSumWithDelimiter(String left, String right) {
        return left + delimiter + right;
    }

    @Test
    public void objectMethodReferenceLambdaImplementation() {
        Summator<String> summator = this::stringSumWithDelimiter;

        assertEquals("a-b", summator.sum("a", "b"));
        assertEquals("a-a", summator.twice("a"));
    }

    @Test
    public void typeInferenceInLambda() {
        Summator<Integer> summator = Example2::sum;

        process(summator);

        // FIXME вывод типов
//        process((left, right) -> Example2.sum(left, right));
//        process((Integer left, Integer right) -> Example2.sum(left, right));
//        process((left, right) -> Example2.sum((Integer)left, (Integer)right));
//        Example2.<Integer>process((left, right) -> Example2.sum(left, right));
    }

    private static <T extends Number> void process(Summator<T> summator) {

    }

    private static Integer sum(Integer left, Integer right) {
        return left + right;
    }
}
