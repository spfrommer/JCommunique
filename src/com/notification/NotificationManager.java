package com.notification;

import java.util.ArrayList;
import java.util.List;

import com.utils.Time;

/**
 * Manages the creation and movement of Notifications. Once a Notification is added, all aspects of it except for click
 * handeling are managed by the NotificationManager. This includes things such as showing and hiding.
 */
public abstract class NotificationManager {
	private List<Notification> m_notifications;

	public NotificationManager() {
		m_notifications = new ArrayList<Notification>();
	}

	/**
	 * Adds a Notification and will also make it visible.
	 *
	 * @param note
	 * @param time
	 *            the amount of time the Notification should display (e.g., Time.seconds(1) will make the Notification
	 *            display for one second).
	 */
	public final void addNotification(Notification note, Time time) {
		note.setNotificationManager(this);
		m_notifications.add(note);
		notificationAdded(note, time);
	}

	/**
	 * Removes a Notification and will also hide it.
	 *
	 * @param note
	 */
	public final void removeNotification(Notification note) {
		m_notifications.remove(note);
		notificationRemoved(note);
		note.setNotificationManager(null);
	}

	public final List<Notification> getNotifications() {
		return m_notifications;
	}

	protected abstract void notificationAdded(Notification note, Time time);

	protected abstract void notificationRemoved(Notification note);
}
