package com.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.notification.Notification;
import com.notification.NotificationManager;
import com.notification.types.TextNotification;
import com.utils.Time;

public class NotificationManagerTest {
	@Test
	public void addAndRemoveShouldTriggerChildCalls() {
		CustomNotificationManager manager = new CustomNotificationManager();
		TextNotification note = new TextNotification();

		manager.addNotification(note, Time.infinite());
		assertEquals("addNotification() should trigger notificationAdded()", 1, manager.addedCounter);
		manager.removeNotification(note);
		assertEquals("removeNotification() should trigger notificationRemoved()", 1, manager.removedCounter);
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

	@Test
	public void managerShouldNotDoubleAdd() {
		CustomNotificationManager manager = new CustomNotificationManager();
		TextNotification note = new TextNotification();

		manager.addNotification(note, Time.infinite());
		manager.addNotification(note, Time.infinite());
		assertEquals("Notification should have been added only once", 1, manager.addedCounter);
	}

	@Test
	public void scheduleRemovalShouldRemove() {
		CustomNotificationManager manager = new CustomNotificationManager();
		TextNotification note = new TextNotification();

		manager.addNotification(note, Time.infinite());
		manager.scheduleRemoval(note, Time.milliseconds(50));
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals("Notification should have been removed once", 1, manager.removedCounter);
	}

	@Test
	public void managerShouldNotDoubleRemove() {
		CustomNotificationManager manager = new CustomNotificationManager();
		TextNotification note = new TextNotification();

		manager.addNotification(note, Time.infinite());
		manager.scheduleRemoval(note, Time.milliseconds(50));
		manager.removeNotification(note);
		manager.removeNotification(note);
		assertEquals("Notification should have been removed only once", 1, manager.removedCounter);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals("Notification should have been removed only once, even after scheduling", 1, manager.removedCounter);
	}

	private class CustomNotificationManager extends NotificationManager {
		private int addedCounter = 0;
		private int removedCounter = 0;

		@Override
		protected void notificationAdded(Notification note, Time time) {
			addedCounter++;
		}

		@Override
		protected void notificationRemoved(Notification note) {
			removedCounter++;
		}

		@Override
		public void scheduleRemoval(Notification note, Time time) {
			super.scheduleRemoval(note, time);
		}
	}
}
