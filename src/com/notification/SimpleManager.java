package com.notification;

import java.util.Timer;
import java.util.TimerTask;

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
	public void notificationAdded(Notification note, Time time) {
		note.setLocation(m_screen.getX(m_loc), m_screen.getY(m_loc));
		note.show();

		if (!time.isInfinite()) {
			Timer timer = new Timer();
			timer.schedule(new RemoveTask(note), time.getMilliseconds());
		}
	}

	@Override
	public void notificationRemoved(Notification note) {
		note.hide();
	}

	@Override
	public void setLocation(PopupLocation loc) {
		m_loc = loc;
	}

	private class RemoveTask extends TimerTask {
		private Notification m_note;

		public RemoveTask(Notification note) {
			m_note = note;
		}

		@Override
		public void run() {
			SimpleManager.this.removeNotification(m_note);
		}
	}
}
