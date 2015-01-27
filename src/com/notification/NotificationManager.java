package com.notification;

import java.util.ArrayList;
import java.util.List;

import com.notification.NotificationFactory.PopupLocation;

/**
 * Manages the creation and movement of Notifications. Once a notification is added, all aspects of it except for click
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
	 */
	public final void addNotification(Notification note) {
		m_notifications.add(note);
		notificationAdded(note);
	}

	/**
	 * Removes a Notification and will also hide it.
	 * 
	 * @param note
	 */
	public final void removeNotification(Notification note) {
		m_notifications.remove(note);
		notificationRemoved(note);
	}

	public final List<Notification> getNotifications() {
		return m_notifications;
	}

	protected abstract void notificationAdded(Notification note);

	protected abstract void notificationRemoved(Notification note);

	protected abstract void setLocation(PopupLocation loc);
}
