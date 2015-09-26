package com.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.utils.MathUtils;

public class MathUtilsTest {
	@Test
	public void testSignNegative() {
		assertEquals("Sign of negtive number should be negative", -1, MathUtils.sign(-8.5));
	}

	@Test
	public void testSignPositive() {
		assertEquals("Sign of positive number should be positive", 1, MathUtils.sign(8.5));
	}

	@Test
	public void testSignZero() {
		assertEquals("Sign of zero should be zero", 0, MathUtils.sign(0));
	}

	@Test
	public void testClampMin() {
		assertEquals("Clamp should work for min", 5.0, MathUtils.clamp(2.0, 5.0, 7.0), TestUtils.TINY_DELTA);
	}

	@Test
	public void testClampMax() {
		assertEquals("Clamp should work for max", 7.0, MathUtils.clamp(10.0, 5.0, 7.0), TestUtils.TINY_DELTA);
	}

	@Test
	public void testClampMiddle() {
		assertEquals("Clamp should work for in between values", 6.0, MathUtils.clamp(6.0, 5.0, 7.0), TestUtils.TINY_DELTA);
	}
}
