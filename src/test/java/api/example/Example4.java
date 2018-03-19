package api.example;

import org.junit.Test;

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

import static org.junit.Assert.assertArrayEquals;

public class Example4 {

    @Test
    public void arraysParallelSortUsingJava8() {
        int[] values = {1, 6, 9, 4, -1, -4, 0, 2, 3};

        Arrays.parallelSort(values);

        assertArrayEquals(new int[]{-4, -1, 0, 1, 2, 3, 4, 6, 9}, values);
    }

    @Test
    public void arraysParallelPrefixUsingJava8() {
        int[] values = {1, 6, 9, 4, -1, -4, 0, 2, 3};

        Arrays.parallelPrefix(values, Integer::sum);

        assertArrayEquals(new int[]{1, 7, 16, 20, 19, 15, 15, 17, 20}, values);
    }

    @Test
    public void arraysParallelSetAllUsingJava8() {
        int[] values = new int[5];

        for (int i = 0; i < values.length; ++i) {
            values[i] = i;
        }

        Arrays.parallelSetAll(values, operand -> operand + 1);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, values);

        // TODO для всех четных 0, для нечетных 1
        Arrays.parallelSetAll(values, i -> i & 1);
        assertArrayEquals(new int[]{0, 1, 0, 1, 0}, values);
    }
}
