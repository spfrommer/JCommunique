package com.unit;

import com.notification.Notification;
import com.notification.types.TextNotification;
import com.notification.NotificationManager;
import com.utils.Time;

import static org.junit.Assert.*;
import org.junit.Test;

public class NotificationManagerTest {
	@Test
	public void addAndRemoveShouldTriggerChildCalls() {
		CustomNotificationManager manager = new CustomNotificationManager();
		TextNotification note = new TextNotification();
		
		manager.addNotification(note, Time.infinite());
		assertTrue("addNotification() should trigger notificationAdded()", manager.added);
		manager.removeNotification(note);
		assertTrue("removeNotification() should trigger notificationRemoved()", manager.removed);
	}
	
	@Test
	public void addAndRemoveShouldModifyList() {
		CustomNotificationManager manager = new CustomNotificationManager();
		TextNotification note = new TextNotification();
		
		manager.addNotification(note, Time.infinite());
		assertEquals("addNotification() should increase Notification list size", 1, manager.getNotifications().size());
		manager.removeNotification(note);
		assertEquals("removeNotification() should increase Notification list size", 0, manager.getNotifications().size());
	}
	
	private class CustomNotificationManager extends NotificationManager {
		public boolean added = false;
		public boolean removed = false;
		@Override
		protected void notificationAdded(Notification note, Time time) {
			added = true;
		}
		@Override
		protected void notificationRemoved(Notification note) {
			removed = true;
		}
	}
}
