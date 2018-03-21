package streams.part1.example;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Example3 {

    @Test
    public void streamOfValues() {
        Stream<Integer> streamFromValues = Stream.of(1, 2, 3);

        Integer[] valuesInStream = streamFromValues.toArray(Integer[]::new);

        assertArrayEquals(new Integer[]{1, 2, 3}, valuesInStream);
    }

    @Test
    public void streamFromArrayUsingArraysStream() {
        Integer[] source = {1, 2, 3};

        Stream<Integer> stream = Arrays.stream(source);
        Integer[] valuesInStream = stream.toArray(Integer[]::new);

        assertArrayEquals(new Integer[]{1, 2, 3}, valuesInStream);
    }

    @Test
    public void streamFromArrayUsingStreamOf() {
        Integer[] source = {1, 2, 3};

        Stream<Integer> stream = Stream.of(source);
        Integer[] valuesInStream = stream.toArray(Integer[]::new);

        assertArrayEquals(new Integer[]{1, 2, 3}, valuesInStream);
    }

    @Test
    public void streamLinesFromFile() throws IOException {
        File sourceFile = File.createTempFile("lines", "tmp");
        sourceFile.deleteOnExit();
        try (PrintWriter out = new PrintWriter(sourceFile)) {
            out.println("a");
            out.println("b");
            out.println("c");
        }

        Path pathToSourceFile = Paths.get(sourceFile.getAbsolutePath());
        Stream<String> stream = Files.lines(pathToSourceFile);
        String[] lines = stream.toArray(String[]::new);

        assertArrayEquals(new String[]{"a", "b", "c"}, lines);
    }

    @Test
    public void sequentialStreamFromCollection() {
        Collection<Integer> source = Arrays.asList(1, 2, 3);

        Stream<Integer> stream = source.stream();
        Integer[] valuesInStream = stream.toArray(Integer[]::new);

        assertArrayEquals(new Integer[]{1, 2, 3}, valuesInStream);
    }

    @Test
    public void parallelStreamFromCollection() {
        Collection<Integer> source = Arrays.asList(1, 2, 3);

        Stream<Integer> stream = source.parallelStream();
        Integer[] valuesInStream = stream.toArray(Integer[]::new);

        assertArrayEquals(new Integer[]{1, 2, 3}, valuesInStream);
    }

    @Test
    public void intStreamWithCodepointsFromString() {
        String source = "Hello";

        IntStream stream = source.chars();
        int[] valuesInStream = stream.toArray();

        assertArrayEquals(new int[]{'H', 'e', 'l', 'l', 'o'}, valuesInStream);
    }

    @Test
    public void streamUsingBuilder() {
        Stream.Builder<String> builder = Stream.builder();

        Stream<String> stream = builder.add("Goodbye")
                                       .add("cruel")
                                       .add("world")
                                       .build();
        String[] valuesInStream = stream.toArray(String[]::new);

        assertArrayEquals(new String[]{"Goodbye", "cruel", "world"}, valuesInStream);
    }

    @Test
    public void infinityStreamUsingGenerate() {
        Supplier<String> supplier = () -> "YOLO";

        Stream<String> stream = Stream.generate(supplier);

        String[] first3ValuesInStream = stream.limit(3)
                                              .toArray(String[]::new);

        assertArrayEquals(new String[]{"YOLO", "YOLO", "YOLO"}, first3ValuesInStream);
    }

    @Test
    public void infinityStreamUsingIterate() {
        UnaryOperator<Integer> oddNumbersGenerator = n -> n + 2;

        Stream<Integer> stream = Stream.iterate(1, oddNumbersGenerator);
        Stream<Integer> limitedStream = stream.limit(10);
        Stream<String> mapStream = limitedStream.map(String::valueOf);
        Stream<String> filteredStream = mapStream.filter(String::isEmpty);
//        Stream<Integer> limitedStream2 = stream.limit(10);
        Integer[] first10ValuesInStream = limitedStream.toArray(Integer[]::new);

        assertArrayEquals(new Integer[]{1, 3, 5, 7, 9, 11, 13, 15, 17, 19}, first10ValuesInStream);
    }

    @Test
    public void emptyStream() {
        Stream<String> stream = Stream.empty();

        assertEquals(0, stream.count());
    }

    @Test
    public void streamFilesFromPath() throws IOException, URISyntaxException {
        Path resourcesPath = Paths.get(ClassLoader.getSystemResource("").toURI());

        Stream<Path> stream = Files.list(resourcesPath.resolve("top"));
        Path[] valuesInStream = stream.toArray(Path[]::new);

        assertEquals("file1.txt", valuesInStream[0].getFileName().toString());
        assertEquals("file2.txt", valuesInStream[1].getFileName().toString());
        assertEquals("file3.bmp", valuesInStream[2].getFileName().toString());
    }

    @Test
    public void streamFoundFilesFromPath() throws IOException, URISyntaxException {
        Path resourcesPath = Paths.get(ClassLoader.getSystemResource("").toURI());

        Stream<Path> stream = Files.find(resourcesPath, 10, (path, attributes) -> path.toFile().getName().endsWith(".bmp"));
        Path[] valuesInStream = stream.toArray(Path[]::new);

        assertEquals("file3.bmp", valuesInStream[0].getFileName().toString());
    }

    @Test
    public void streamFromRegex() {
        String source = "a:b:c";

        Stream<String> stream = Pattern.compile(":").splitAsStream(source);
        String[] valuesInStream = stream.toArray(String[]::new);

        assertArrayEquals(new String[]{"a", "b", "c"}, valuesInStream);
    }

    @Test
    public void linesStreamFromReader() throws IOException {
        File sourceFile = File.createTempFile("lines", "tmp");
        sourceFile.deleteOnExit();
        try (PrintWriter out = new PrintWriter(sourceFile)) {
            out.println("a");
            out.println("b");
            out.println("c");
        }

        try (BufferedReader reader = Files.newBufferedReader(sourceFile.toPath())) {
            Stream<String> stream = reader.lines();
            String[] valuesInStream = stream.toArray(String[]::new);
            assertArrayEquals(new String[]{"a", "b", "c"}, valuesInStream);
        }
    }
}
