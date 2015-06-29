package com.unit;

import com.notification.types.ProgressBarNotification;

import static org.junit.Assert.*;
import org.junit.Test;

public class ProgressBarNotificationTest {
	@Test
	public void progressBarShouldStartWith0() {
		ProgressBarNotification note = new ProgressBarNotification();
		assertEquals("ProgressBarNotification should start with 0 progress", 0, note.getProgress());
	}

	@Test
	public void progressBarShouldNotExceed100() {
		ProgressBarNotification note = new ProgressBarNotification();
		note.setProgress(123);
		assertEquals("ProgressBarNotification should not exceed 100 progress", 100, note.getProgress());
	}

	@Test
	public void progressBarShouldNotGoBelow0() {
		ProgressBarNotification note = new ProgressBarNotification();
		note.setProgress(-10);
		assertEquals("ProgressBarNotification should not go below 0 progress", 0, note.getProgress());
	}
}
