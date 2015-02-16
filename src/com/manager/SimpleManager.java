package com.manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

import javax.swing.Timer;

import com.notification.Notification;
import com.notification.NotificationFactory.Location;
import com.platform.Platform;
import com.utils.Screen;
import com.utils.Time;

/**
 * Simply displays new Notifications in one corner of the screen on top of each other. Has an option for fading (note -
 * results will vary across different platforms).
 */
public class SimpleManager extends NotificationManager {
	private Location m_loc;
	private Screen m_screen;

	private boolean m_fadeEnabled = false;
	private Time m_fadeTime;

	public SimpleManager() {
		m_loc = Location.NORTHEAST;
		m_screen = new Screen(true);
		m_fadeEnabled = false;
		m_fadeTime = Time.seconds(1);
	}

	public SimpleManager(Location loc) {
		m_loc = loc;
		m_screen = new Screen(true);
		m_fadeEnabled = false;
		m_fadeTime = Time.seconds(1);
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
	public Location getLocation() {
		return m_loc;
	}

	/**
	 * Sets the location where the Notifications show up.
	 * 
	 * @param loc
	 */
	public void setLocation(Location loc) {
		m_loc = loc;
	}

	protected Screen getScreen() {
		return m_screen;
	}

	private double getDeltaFade(double opacity, double frequency) {
		double numTimes = m_fadeTime.getMilliseconds() / frequency;
		double fade = opacity / numTimes;
		return fade;
	}

	@Override
	protected void notificationAdded(Notification note, Time time) {
		note.setLocation(m_screen.getX(m_loc), m_screen.getY(m_loc));

		if (Platform.instance().isUsed()) {
			m_fadeEnabled = Platform.instance().isSupported("fade");
		}

		if (m_fadeEnabled) {
			double frequency = 100f;
			double opacity = note.getOpacity();

			note.setOpacity(0);
			Timer timer = new Timer((int) frequency, new Fader(note, getDeltaFade(opacity, frequency), opacity));
			timer.start();

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
		if (Platform.instance().isUsed()) {
			m_fadeEnabled = Platform.instance().isSupported("fade");
		}

		if (m_fadeEnabled) {
			double frequency = 50f;

			Timer timer = new Timer((int) frequency, new Fader(note, -getDeltaFade(note.getOpacity(), frequency), 0));
			timer.start();
		} else {
			note.hide();
		}
	}

	private class Fader implements ActionListener {
		private Notification m_note;
		private double m_fade;
		private double m_stopFade;

		public Fader(Notification note, double fade, double stopFade) {
			m_note = note;
			m_fade = fade;
			m_stopFade = stopFade;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if ((m_fade > 0) ? m_note.getOpacity() < m_stopFade : m_note.getOpacity() > m_stopFade) {
				m_note.setOpacity(m_note.getOpacity() + m_fade);
			} else {
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
