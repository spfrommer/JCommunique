package com.notification;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

import javax.swing.Timer;

import com.notification.NotificationFactory.PopupLocation;

public class FadeManager extends NotificationManager {
	private PopupLocation m_loc;
	private Screen m_screen;
	private Time m_fadeTime;

	public FadeManager() {
		m_loc = PopupLocation.NORTHEAST;
		m_screen = new Screen(true);
		m_fadeTime = Time.seconds(1);
	}

	public FadeManager(PopupLocation loc) {
		m_loc = loc;
		m_screen = new Screen(true);
		m_fadeTime = Time.seconds(1);
	}

	/**
	 * @param loc
	 * @param fadeTime
	 *            how long the fade should take. Infinite means no fade.
	 */
	public FadeManager(PopupLocation loc, Time fadeTime) {
		m_loc = loc;
		m_screen = new Screen(true);
		m_fadeTime = fadeTime;
	}

	@Override
	protected void notificationAdded(Notification note, Time time) {
		note.setLocation(m_screen.getX(m_loc), m_screen.getY(m_loc));

		float frequency = 100f;
		float numTimes = m_fadeTime.getMilliseconds() / frequency;
		float opacity = note.getOpacity();
		float fade = opacity / numTimes;

		note.setOpacity(0.5f);
		note.show();

		Timer timer = new Timer((int) frequency, new Fader(note, fade, opacity));
		timer.start();

		if (!time.isInfinite()) {
			java.util.Timer removeTimer = new java.util.Timer();
			removeTimer.schedule(new RemoveTask(note), time.getMilliseconds());
		}
	}

	@Override
	protected void notificationRemoved(Notification note) {
		float frequency = 100f;
		float numTimes = m_fadeTime.getMilliseconds() / frequency;
		float opacity = note.getOpacity();
		float fade = opacity / numTimes;

		Timer timer = new Timer((int) frequency, new Fader(note, -fade, 0));
		timer.start();
	}

	@Override
	protected void setLocation(PopupLocation loc) {

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
			if ((m_fade > 0) ? m_note.getOpacity() < m_stopFade : m_note.getOpacity() > m_stopFade) {
				System.out.println("Fading: " + m_fade);
				m_note.setOpacity(m_note.getOpacity() + m_fade);
			} else {
				((Timer) e.getSource()).stop();
				System.out.println("Stopping fade");
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
			FadeManager.this.removeNotification(m_note);
		}
	}
}
