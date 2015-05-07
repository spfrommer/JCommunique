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

	private static final int FADE_DELAY = 100; // milliseconds

	{
		m_screen = Screen.standard();
		m_fadeEnabled = false;
		m_fadeTime = Time.seconds(1);
	}

	public SimpleManager() {
		m_loc = Location.NORTHEAST;
	}

	public SimpleManager(Location loc) {
		m_loc = loc;
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
		syncFadeEnabledWithPlatform();

		return m_fadeEnabled;
	}

	/**
	 * Sets whether or not fading is enabled.
	 *
	 * @param fadeEnabled
	 */
	public void setFadeEnabled(boolean fadeEnabled) {
		m_fadeEnabled = fadeEnabled;

		syncFadeEnabledWithPlatform();
	}

	private void syncFadeEnabledWithPlatform() {
		if (m_fadeEnabled && Platform.instance().isUsed())
			m_fadeEnabled = Platform.instance().isSupported("fade");
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

	@Override
	protected void notificationAdded(Notification note, Time time) {
		note.setLocation(m_screen.getX(m_loc, note), m_screen.getY(m_loc, note));

		if (isFadeEnabled()) {
			double opacity = note.getOpacity();
			note.setOpacity(0);
			startFade(note, opacity);
			scheduleRemoval(note, time.add(m_fadeTime));
		} else {
			scheduleRemoval(note, time);
		}

		note.show();
	}

	@Override
	protected void notificationRemoved(Notification note) {
		if (isFadeEnabled()) {
			startFade(note, -note.getOpacity());
		} else {
			note.hide();
		}
	}

	private void startFade(Notification note, double deltaOpacity) {
		Timer timer = new Timer(FADE_DELAY, new Fader(note, getDeltaFade(deltaOpacity), deltaOpacity));
		timer.start();
	}

	private double getDeltaFade(double deltaOpacity) {
		double numTimes = m_fadeTime.getMilliseconds() / (double) FADE_DELAY;
		double fade = deltaOpacity / numTimes;
		return fade;
	}

	private void scheduleRemoval(Notification note, Time time) {
		if (!time.isInfinite()) {
			java.util.Timer removeTimer = new java.util.Timer();
			removeTimer.schedule(new RemoveTask(note), time.getMilliseconds());
		}
	}

	private class Fader implements ActionListener {
		private Notification m_note;
		private double m_deltaFade;
		private double m_stopFade;

		public Fader(Notification note, double deltaFade, double stopFade) {
			m_note = note;
			m_deltaFade = deltaFade;
			m_stopFade = stopFade;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if ((m_deltaFade > 0) ? m_note.getOpacity() < m_stopFade : m_note.getOpacity() > m_stopFade) {
				m_note.setOpacity(m_note.getOpacity() + m_deltaFade);
			} else {
				((Timer) e.getSource()).stop();
				if (m_deltaFade < 0) {
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
