package spliterators.exercise.exercise1;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Exercise1 {

    @Test
    public void test() {
        List<String> result = getMostPopularWords(10, "Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit",
                                                    "Sed", "sodales", "consectetur", "purus", "at", "faucibus", "Donec", "mi", "quam", "tempor", "vel", "ipsum",
                                                    "non", "faucibus", "suscipit", "massa", "Morbi", "lacinia", "velit", "blandit", "tincidunt", "efficitur",
                                                    "Vestibulum", "eget", "metus", "imperdiet", "sapien", "laoreet", "faucibus", "Nunc", "eget", "vehicula",
                                                    "mauris", "ac", "auctor", "lorem", "Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing",
                                                    "elit", "Integer", "vel", "odio", "nec", "mi", "tempor", "dignissim");

        assertEquals(Arrays.asList("consectetur", "faucibus", "ipsum", "lorem", "adipiscing", "amet", "dolor", "eget", "elit", "mi"), result);
    }

    private List<String> getMostPopularWords(int count, String... words) {
        throw new UnsupportedOperationException();
    }
}
