package com.notification;

public interface NotificationListener {
	/**
	 * Called when an action is completed on the Notification (e.g., a click).
	 * 
	 * @param notification
	 */
	public void actionCompleted(Notification notification);
}
