package com.notification;

import com.theme.ThemePackage;

/**
 * This interface is implemented for building custom Notifications.
 */
public interface NotificationBuilder {
	/**
	 * Builds a Notification in accordance with the ThemePackage.
	 * 
	 * @param pack
	 * @param args
	 * @return
	 */
	public Notification buildNotification(ThemePackage pack, Object[] args);
}
