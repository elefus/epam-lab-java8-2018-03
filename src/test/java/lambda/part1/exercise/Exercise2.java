package lambda.part1.exercise;

import org.junit.Test;

import java.util.StringJoiner;

import static org.junit.Assert.assertEquals;

public class Exercise2 {
	
	private final String delimiter = "-";
	
	private static String multiplyString(String string, int number) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < number; ++i) {
			builder.append(string);
		}
		return builder.toString();
	}
	
	private void testStringMultiplier(Multiplier<String> multiplier) {
		objectMethodReferenceStringMultiplierAsserts(multiplier, "aaa", "aAaA", "aA");
	}
	
	private void testIntegerMultiplier(Multiplier<Integer> multiplier) {
		assertEquals(6, multiplier.multiply(3, 2).intValue());
		assertEquals(0, multiplier.multiply(Integer.MIN_VALUE, 0).intValue());
		assertEquals(-7, multiplier.multiply(7, -1).intValue());
		assertEquals(10, multiplier.twice(5).intValue());
		assertEquals(0, multiplier.twice(0).intValue());
	}
	
	@Test
	public void implementsIntegerMultiplierUsingAnonymousClass() {
		Multiplier<Integer> multiplier = new Multiplier<Integer>() {
			@Override
			public Integer multiply(Integer value, int multiplier) {
				return value * multiplier;
			}
		};
		
		testIntegerMultiplier(multiplier);
	}
	
	@Test
	public void implementsMultiplierUsingStatementLambda() {
		Multiplier<Integer> multiplier = (Integer value, int multiplierr) -> {
			return value * multiplierr;
		};
		
		testIntegerMultiplier(multiplier);
	}
	
	@Test
	public void implementsIntegerMultiplierUsingExpressionLambda() {
		Multiplier<Integer> multiplier = (Integer value, int multiplierr) -> value * multiplierr;
		testIntegerMultiplier(multiplier);
	}
	
	@Test
	public void implementsStringMultiplierUsingClassMethodReference() {
		Multiplier<String> multiplier = Exercise2::multiplyString;
		testStringMultiplier(multiplier);
	}
	
	private String stringSumWithDelimiter(String string, int number) {
		StringJoiner joiner = new StringJoiner(delimiter);
		for (int i = 0; i < number; ++i) {
			joiner.add(string);
		}
		String result = joiner.toString();
		return result.equals(delimiter) ? "" : result;
	}
	
	@Test
	public void implementsStringMultiplierUsingObjectMethodReference() {
		Multiplier<String> multiplier =
				this::multiplyStringNotStaticForObjectMethodReferenceWithDelimiter;
		
		objectMethodReferenceStringMultiplierAsserts(multiplier, "a-a-a", "A-A", "A");
	}
	
	private String multiplyStringNotStaticForObjectMethodReferenceWithDelimiter(String string,
																				int i) {
		return stringSumWithDelimiter(string, i);
	}
	
	private void objectMethodReferenceStringMultiplierAsserts(Multiplier<String> multiplier,
															  String s, String s2, String a) {
		assertEquals(s, multiplier.multiply("a", 3));
		assertEquals("", multiplier.multiply("qwerty", 0));
		assertEquals(s2, multiplier.twice(a));
		assertEquals("", multiplier.twice(""));
	}
	
	@FunctionalInterface
	private interface Multiplier<T> {
		T multiply(T value, int multiplier);
		
		default T twice(T t) {
			return multiply(t, 2);
		}
	}
}
