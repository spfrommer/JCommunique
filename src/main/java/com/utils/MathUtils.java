package com.utils;

public class MathUtils {
	/**
	 * @param number
	 *            the number to find the sign of
	 * @return the sign of the number
	 */
	public static int sign(double number) {
		return (int) Math.signum(number);
	}
}
