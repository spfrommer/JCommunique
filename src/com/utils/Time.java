package com.utils;

/**
 * Represents the time the Notification should be shown.
 */
public final class Time {
	private int m_milliseconds;
	private boolean m_infinite;

	private Time(int milliseconds, boolean infinite) {
		m_milliseconds = milliseconds;
		m_infinite = infinite;
	}

	/**
	 * @param seconds
	 *            the number of seconds the Notification should display. This is truncated at the millisecond.
	 * @return
	 */
	public static Time seconds(double seconds) {
		return new Time((int) (seconds * 1000), false);
	}

	/**
	 * @param milliseconds
	 *            the number of milliseconds the Notification should display.
	 * @return
	 */
	public static Time milliseconds(int milliseconds) {
		return new Time(milliseconds, false);
	}

	/**
	 * Specifies that the Notification should display infinitely.
	 *
	 * @return
	 */
	public static Time infinite() {
		return new Time(-1, true);
	}

	/**
	 * @param time
	 * @return the sum of the two times
	 */
	public Time add(Time time) {
		return new Time(m_milliseconds + time.getMilliseconds(), m_infinite || time.isInfinite());
	}

	/**
	 * @return the number of seconds that the Notification displays, of -1 if it is infinite
	 */
	public double getSeconds() {
		if (m_infinite)
			return -1;
		return (double) m_milliseconds / 1000;
	}

	/**
	 * @return the number of milliseconds that the Notification displays, or -1 if it is infinite
	 */
	public int getMilliseconds() {
		if (m_infinite)
			return -1;
		return m_milliseconds;
	}

	/**
	 * @return whether or not the Notification is infinite
	 */
	public boolean isInfinite() {
		return m_infinite;
	}
}
