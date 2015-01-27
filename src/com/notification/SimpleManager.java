package com.notification;

import com.notification.NotificationFactory.PopupLocation;

public class SimpleManager extends NotificationManager {
	private PopupLocation m_loc;
	private Screen m_screen;

	public SimpleManager() {
		m_loc = PopupLocation.NORTHEAST;
		m_screen = new Screen(true);
	}

	public SimpleManager(PopupLocation loc) {
		m_loc = loc;
		m_screen = new Screen(true);
	}

	@Override
	public void notificationAdded(Notification note) {
		note.setLocation(m_screen.getX(m_loc), m_screen.getY(m_loc));
		note.show();
	}

	@Override
	public void notificationRemoved(Notification note) {
		note.hide();
	}

	@Override
	public void setLocation(PopupLocation loc) {
		m_loc = loc;
	}
}
