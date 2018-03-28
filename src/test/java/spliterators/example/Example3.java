package spliterators.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class Example3 {

    @Test
    public void name() {

        // parallel reduce
        // concurrent reduce

//        ArrayList<Integer> accumulator = Stream.of(1, 2, 3, 4, 5, 6)
//                                               .parallel()
////                                               .reduce(new ArrayList<>(),
////                                                       (values, value) -> {
////                                                           System.out.println("Accumulator");
////                                                           values.add(value);
////                                                           return values;
////                                                       }, (left, right) -> {
////                                                           System.out.println("Combiner");
////                                                           left.addAll(right);
////                                                           return left;
////                                                       });
////                                               .collect(ArrayList::new,
////                                                       (values, value) -> {
////                                                   System.out.println("Accumulator");
////                                                   values.add(value);
////                                               }, (left, right) -> {
////                                                   System.out.println(left);
////                                                   System.out.println(right);
////                                                   System.out.println("Combiner");
////                                                   left.addAll(right);
////                                               });
//                                                .collect(new ToIntegerListCollector());

        // parallel reduce
        //    [1]     [2]     [3]     [4]    [5]     [6]
        // {}      {}      {}      {}      {}      {}
        // {1} []  {2} []  {3} []  {4] []  {5} []  {6} []
        //    {1, 2}       {3}        {4, 5}     {6}
        //          {1, 2, 3}           {4, 5, 6}
        //               {1, 2, 3, 4, 5, 6}
        //      finisher({1, 2, 3, 4, 5, 6})



        // concurrent reduce
        //    [1]     [2]     [3]     [4]    [5]     [6]
        // {}
        // {1} []
        // {1, 2}     []

        Queue<Integer> accumulator = Stream.of(1, 2, 3, 4, 5, 6)
                                           .parallel()
                                           .collect(new ToQueueCollector<>());


        System.out.println(accumulator);
    }


    private class ToQueueCollector<T> implements Collector<T, Queue<T>, Queue<T>> {

        @Override
        public Supplier<Queue<T>> supplier() {
            return () -> {
                System.out.println(Thread.currentThread() + " - supplier");
                return new ConcurrentLinkedQueue<>();
            };
        }

        @Override
        public BiConsumer<Queue<T>, T> accumulator() {
            return (values, value) -> {
                System.out.println(Thread.currentThread() + " - accumulate " + values + " + " + value);
                values.add(value);
            };
        }

        @Override
        public BinaryOperator<Queue<T>> combiner() {
            return (left, right) -> {
                System.out.println(Thread.currentThread() + " - combine " + left + " + " + right);
                left.addAll(right);
                return left;
            };
        }

        @Override
        public Function<Queue<T>, Queue<T>> finisher() {
            return values -> {
                throw new UnsupportedOperationException();
            };
        }

        @Override
        public Set<Characteristics> characteristics() {
            return EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.CONCURRENT);
        }
    }

    private class ToIntegerListCollector implements Collector<Integer, ArrayList<Integer>, ArrayList<Integer>> {

        @Override
        public Supplier<ArrayList<Integer>> supplier() {
            return () -> {
                System.out.println(Thread.currentThread() + " - supplier");
                return new ArrayList<>();
            };
        }

        @Override
        public BiConsumer<ArrayList<Integer>, Integer> accumulator() {
            return (values, value) -> {
                System.out.println(Thread.currentThread() + " - accumulate " + values + " + " + value);
                values.add(value);
            };
        }

        @Override
        public BinaryOperator<ArrayList<Integer>> combiner() {
            return (left, right) -> {
                System.out.println(Thread.currentThread() + " - combine " + left + " + " + right);
                left.addAll(right);
                return left;
            };
        }

        @Override
        public Function<ArrayList<Integer>, ArrayList<Integer>> finisher() {
            return values -> {
                throw new UnsupportedOperationException();
            };
        }

        @Override
        public Set<Characteristics> characteristics() {
            return EnumSet.of(Characteristics.IDENTITY_FINISH);
        }
    }
}
