package streams.part1.example;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;

/**
 * @see <a href="https://youtu.be/kxgo7Y4cdA8">Через тернии к лямбдам, часть 1</a>
 * @see <a href="https://youtu.be/JRBWBJ6S4aU">Через тернии к лямбдам, часть 2</a>
 * @see <a href="https://youtu.be/O8oN4KSZEXE">Stream API, часть 1</a>
 * @see <a href="https://youtu.be/i0Jr2l3jrDA">Stream API, часть 2</a>
 */
@SuppressWarnings("ConstantConditions")
public class Example2 {

    @Test
    public void getIvansLastNames() {
        List<Employee> employees = Example1.getEmployees();

        String[] ivansLastNames = null;

        assertArrayEquals(new String[]{"Мельников", "Александров"}, ivansLastNames);
    }

    @Test
    public void checkAny25AgedIvanHasDevExperience() {
        boolean any25IvanHasDevExperience = false;

        assertTrue(any25IvanHasDevExperience);
    }
}
