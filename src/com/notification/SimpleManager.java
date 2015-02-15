package com.notification;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

import javax.swing.Timer;

import com.notification.NotificationFactory.PopupLocation;

/**
 * Simply displays new Notifications in one corner of the screen on top of each
 * other. Has an option for fading.
 */
public class SimpleManager extends NotificationManager {
	private PopupLocation m_loc;
	private Screen m_screen;

	private boolean m_fadeEnabled = false;
	private Time m_fadeTime;

	public SimpleManager() {
		m_loc = PopupLocation.NORTHEAST;
		m_screen = new Screen(true);
		m_fadeEnabled = false;
		m_fadeTime = Time.seconds(1);
	}

	public SimpleManager(PopupLocation loc) {
		m_loc = loc;
		m_screen = new Screen(true);
		m_fadeEnabled = false;
		m_fadeTime = Time.seconds(1);
	}

	/**
	 * @param loc
	 * @param fadeTime
	 *            how long the fade should take. Infinite means no fade.
	 */
	public SimpleManager(PopupLocation loc, Time fadeTime) {
		m_loc = loc;
		m_screen = new Screen(true);
		m_fadeEnabled = true;
		m_fadeTime = fadeTime;
	}

	/**
	 * @return the time for fading
	 */
	public Time getFadeTime() {
		return m_fadeTime;
	}

	/**
	 * Sets the fade time. To enable, call setFadeEnabled(boolean).
	 * 
	 * @param fadeTime
	 */
	public void setFadeTime(Time fadeTime) {
		m_fadeTime = fadeTime;
	}

	/**
	 * @return whether or not fading is enabled
	 */
	public boolean isFadeEnabled() {
		return m_fadeEnabled;
	}

	/**
	 * Sets whether or not fading is enabled.
	 * 
	 * @param fadeEnabled
	 */
	public void setFadeEnabled(boolean fadeEnabled) {
		m_fadeEnabled = fadeEnabled;
	}

	/**
	 * @return the location where the Notifications show up
	 */
	public PopupLocation getLocation() {
		return m_loc;
	}

	/**
	 * Sets the location where the Notifications show up.
	 * 
	 * @param loc
	 */
	public void setLocation(PopupLocation loc) {
		m_loc = loc;
	}

	protected Screen getScreen() {
		return m_screen;
	}

	@Override
	protected void notificationAdded(Notification note, Time time) {
		note.setLocation(m_screen.getX(m_loc), m_screen.getY(m_loc));

		if (m_fadeEnabled) {
			float frequency = 100f;
			float numTimes = m_fadeTime.getMilliseconds() / frequency;
			float opacity = note.getOpacity();
			float fade = opacity / numTimes;

			Timer timer = new Timer((int) frequency, new Fader(note, fade,
					opacity));
			timer.start();

			note.setOpacity(Notification.MINIMUM_OPACITY);
			note.show();
		} else {
			note.show();
		}

		if (!time.isInfinite()) {
			java.util.Timer removeTimer = new java.util.Timer();
			removeTimer.schedule(new RemoveTask(note), time.getMilliseconds());
		}
	}

	@Override
	protected void notificationRemoved(Notification note) {
		if (m_fadeEnabled) {
			float frequency = 100f;
			float numTimes = m_fadeTime.getMilliseconds() / frequency;
			float opacity = note.getOpacity();
			float fade = opacity / numTimes;

			Timer timer = new Timer((int) frequency, new Fader(note, -fade, 0));
			timer.start();

			System.out.println("Removing fader");
		} else {
			note.hide();
		}
	}

	private class Fader implements ActionListener {
		private Notification m_note;
		private float m_fade;
		private float m_stopFade;

		public Fader(Notification note, float fade, float stopFade) {
			m_note = note;
			m_fade = fade;
			m_stopFade = stopFade;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if ((m_fade > 0) ? m_note.getOpacity() < m_stopFade : m_note
					.getOpacity() > m_stopFade) {
				System.out.println("Fading " + m_note.getOpacity());
				m_note.directSetOpacity(m_note.getOpacity() + m_fade);
			} else {
				System.out.println("Stopping fade");
				((Timer) e.getSource()).stop();
				if (m_fade < 0) {
					m_note.hide();
				}
			}
		}
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
