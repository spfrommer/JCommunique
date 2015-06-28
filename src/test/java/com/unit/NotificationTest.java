package com.unit;

import com.notification.Notification;
import com.notification.types.TextNotification;

import static org.junit.Assert.*;
import org.junit.Test;

public class NotificationTest {
	@Test
	public void notificationShouldStartUnmanaged() {
		Notification note = new TextNotification();
		assertFalse("Notification should start unmanaged", note.isManaged());
	}
}
