package lambda.part2.example;

import lambda.data.Person;
import org.junit.Test;

import java.util.function.*;

import static org.junit.Assert.*;

/**
 * Общее количество стандартных интерфейсов - 43.
 * @see <a href=https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html">java.util.function</a>
 */
@SuppressWarnings({"Convert2MethodRef", "RedundantStringConstructorCall", "unused"})
public class Example6 {

    // Arity
    // 0: () -> R
    // 1: (T1) -> R
    // 2: (T1, T2) -> R
    // 3: (T1, T2, T3) -> R
    // 4: (T1, T2, T3, T4) -> R

    // IO int
    // IO long
    // IO double
    //  O boolean
    //  O void

    /**
     * Arity 0
     *
     *  1. Supplier: () -> R
     *  2. IntSupplier: () -> int
     *  3. LongSupplier: () -> long
     *  4. DoubleSupplier: () -> double
     *  5. BooleanSupplier: () -> boolean
     *  -. Runnable: () -> void
     */
    @Test
    public void suppliers() {
        Supplier<String> strSupplier = () -> new String("some string");

        assertEquals("some string", strSupplier.get());
        assertNotSame(strSupplier.get(), strSupplier.get());

        IntSupplier intSupplier = () -> 42;
        assertEquals(intSupplier.getAsInt(), intSupplier.getAsInt());
        assertNotSame(intSupplier.getAsInt(), intSupplier.getAsInt());

        LongSupplier longSupplier = () -> 1_000_000_000_000L;
        assertTrue(longSupplier.getAsLong() > Integer.MAX_VALUE);

        DoubleSupplier doubleSupplier = () -> 0.1;
        assertEquals(0.1, doubleSupplier.getAsDouble(), 0.0001);

        BooleanSupplier booleanSupplier = () -> true;
        assertTrue(booleanSupplier.getAsBoolean());

        Runnable runnable = () -> System.out.println("runnable");
    }

    /**
     * Arity 1
     *
     *  1. Consumer: (T) -> void
     *  2. IntConsumer: (int) -> void
     *  3. LongConsumer: (long) -> void
     *  4. DoubleConsumer: (double) -> void
     */
    @Test
    public void consumers() {
        Consumer<String> stringConsumer = System.out::println;
        stringConsumer.accept("string");

        IntConsumer intConsumer = System.out::println;
        intConsumer.accept(42);

        LongConsumer longConsumer = System.out::println;
        longConsumer.accept(1_000_000_000_000L);

        DoubleConsumer doubleConsumer = System.out::println;
        doubleConsumer.accept(42.5);
    }

    /**
     * Arity 1
     *
     *  5. Predicate: (T) -> boolean
     *  6. IntPredicate: (int) -> boolean
     *  7. LongPredicate: (long) -> boolean
     *  8. DoublePredicate: (double) -> boolean
     */
    @Test
    public void predicates() {
        Predicate<String> emptyChecker = String::isEmpty;
        assertTrue(emptyChecker.test(""));

        IntPredicate positiveChecker = value -> value > 0;
        assertFalse(positiveChecker.test(-1));

        LongPredicate greaterThan1000Checker = value -> value > 1000;
        assertFalse(greaterThan1000Checker.test(0));

        DoublePredicate closeToInteger = value -> Math.abs(value - Math.round(value)) < 0.0001;
        assertTrue(closeToInteger.test(1.0));
        assertFalse(closeToInteger.test(1.1));
    }

    /**
     * Arity 1
     *
     *  9. UnaryOperator: (T) -> T
     * 10. IntUnaryOperator: (int) -> int
     * 11. LongUnaryOperator: (long) -> long
     * 12. DoubleUnaryOperator: (double) -> double
     */
    @Test
    public void unaryOperators() {
        UnaryOperator<String> reverse = string -> new StringBuilder(string).reverse().toString();
        assertEquals("gnirts", reverse.apply("string"));

        IntUnaryOperator negate = i -> -i;
        assertEquals(-1, negate.applyAsInt(1));

        LongUnaryOperator increment = value -> ++value;
        assertEquals(1_000_000_000_001L, increment.applyAsLong(1_000_000_000_000L));

        DoubleUnaryOperator decrement = value -> --value;
        assertEquals(2.0, decrement.applyAsDouble(3.0), 0.0001);
    }

    /**
     * Arity 1
     *
     * 13. Function: (T) -> R
     * 14. ToIntFunction: (T) -> int
     * 15. ToLongFunction: (T) -> long
     * 16. ToDoubleFunction: (T) -> double
     * 17. IntFunction: (int) -> R
     * 18. LongFunction: (long) -> R
     * 19. DoubleFunction: (double) -> R
     * 20. IntToLongFunction: (int) -> long
     * 21. IntToDoubleFunction: (int) -> double
     * 22. LongToIntFunction: (long) -> int
     * 23. LongToDoubleFunction: (long) -> double
     * 24. DoubleToIntFunction: (double) -> int
     * 25. DoubleToLongFunction: (double) -> long
     */
    @Test
    public void functions() {
        Person person = new Person("Иван", "Мельников", 33);

        Function<Person, String> firstNameExtractor = Person::getFirstName;
        assertEquals("Иван", firstNameExtractor.apply(person));

        ToIntFunction<Person> ageExtractor = Person::getAge;
        assertEquals(33, ageExtractor.applyAsInt(person));

        ToLongFunction<Long> longUnboxer = Long::longValue;
        assertEquals(100L, longUnboxer.applyAsLong(100L));

        ToDoubleFunction<Double> doubleUnboxer = Double::doubleValue;
        assertEquals(10.5, doubleUnboxer.applyAsDouble(10.5), 0.0001);

        IntFunction<String> intToString = Integer::toString;
        assertEquals("123", intToString.apply(123));

        IntToDoubleFunction intToLong = value -> value;
        IntToDoubleFunction intToDouble = value -> value;

        LongToIntFunction longToInt = value -> (int)value;
        LongToDoubleFunction longToDouble = value -> value;

        DoubleToIntFunction doubleToInt = value -> (int)value;
        DoubleToLongFunction doubleToLong = Math::round;
    }

    /**
     * Arity 2
     *
     *  1. BiConsumer: (T, U) -> void
     *  2. ObjIntConsumer: (T, int) -> void
     *  3. ObjLongConsumer: (T, long) -> void
     *  4. ObjDoubleConsumer: (T, double) -> void
     */
    @Test
    public void biConsumers() {
        Person ivan = new Person("Иван", "Мельников", 33);

        BiConsumer<Person, String> printGreeting = (person, appeal) -> System.out.println(appeal + " " + person.getFirstName());
        printGreeting.accept(ivan, "Mr.");

        ObjIntConsumer<Person> printIncrementedAge = (person, value) -> System.out.println(person.getAge() + value);
        printIncrementedAge.accept(ivan, 10);

        ObjLongConsumer<String> printStringIfLengthLessThen = (string, value) -> System.out.println(string.length() < value ? string : "");
        printStringIfLengthLessThen.accept("12345", 10);

        ObjDoubleConsumer<String> printIncrementedValue = (stringRepresentation, value) -> System.out.println(Double.parseDouble(stringRepresentation) + value);
        printIncrementedValue.accept("10.5", 0.5);
    }

    /**
     * Arity 2
     *
     *  5. BiPredicate: (T, U) -> boolean
     */
    @Test
    public void biPredicate() {
        BiPredicate<String, Person> firstNameChecker = (string, person) -> string.equals(person.getFirstName());
        assertTrue(firstNameChecker.test("Иван", new Person("Иван", "Мельников", 33)));
    }

    /**
     * Arity 2
     *  6. BinaryOperator: (T, T) -> T
     *  7. IntBinaryOperator: (int, int) -> int
     *  8. LongBinaryOperator: (long, long) -> long
     *  9. DoubleBinaryOperator: (double, double) -> double
     */
    @Test
    public void binaryOperators() {
        BinaryOperator<String> concat = String::concat;
        assertEquals("ab", concat.apply("a", "b"));

        LongBinaryOperator longMin = Long::min;
        assertEquals(10, longMin.applyAsLong(10, 100));

        IntBinaryOperator intSum = Integer::sum;
        assertEquals(3, intSum.applyAsInt(1, 2));

        DoubleBinaryOperator doubleMax = Double::max;
        assertEquals(5.9, doubleMax.applyAsDouble(5.9, 0.8), 0.0001);
    }

    /**
     * Arity 2
     *
     * 10. BiFunction: (T, U) -> R
     * 11. ToIntBiFunction: (T, U) -> int
     * 12. ToLongBiFunction: (T, U) -> long
     * 13. ToDoubleBiFunction: (T, U) -> double
     */
    @Test
    public void biFunction() {
        Person ivan = new Person("Иван", "Мельников", 33);

        BiFunction<Person, String, Person> changeFirstName = (person, firstName) -> new Person(firstName, person.getLastName(), person.getAge());
        assertEquals(new Person("Семён", "Мельников", 33), changeFirstName.apply(ivan, "Семён"));

        ToIntBiFunction<Person, String> calcLengthAppeal = (person, string) -> String.format("%s %s %s", string, person.getFirstName(), person.getLastName()).length();
        assertEquals(18, calcLengthAppeal.applyAsInt(ivan, "Mr."));

        ToLongBiFunction<Person, String> toLongBiFunction = (person, string) -> 42;
        ToDoubleBiFunction<Person, String> toDoubleBiFunction = (person, string) -> 42;
    }
}
