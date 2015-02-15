package com.notification;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import com.notification.NotificationFactory.PopupLocation;

/**
 * A NotificationManager which slides old Notifications above or below new
 * Notifications.
 */
public class QueueManager extends SimpleManager {
	private Timer m_timer;
	private int m_verticalPadding = 20;
	private ScrollDirection m_scroll = ScrollDirection.SOUTH;

	/**
	 * Which way the new Notifications scroll.
	 */
	public enum ScrollDirection {
		NORTH, SOUTH
	}

	{
		m_timer = new Timer(50, new MovementManager());
		m_timer.start();
	}

	public QueueManager() {
		super();
	}

	public QueueManager(PopupLocation loc) {
		super(loc);
	}

	/**
	 * @param loc
	 * @param fadeTime
	 *            how long the fade should take. Infinite means no fade.
	 */
	public QueueManager(PopupLocation loc, Time fadeTime) {
		super(loc, fadeTime);
	}

	/**
	 * @return the padding in between Notifications in the queue
	 */
	public int getVerticalPadding() {
		return m_verticalPadding;
	}

	/**
	 * Sets the vertical padding between Notifications in the queue.
	 * 
	 * @param verticalPadding
	 */
	public void setVerticalPadding(int verticalPadding) {
		m_verticalPadding = verticalPadding;
	}

	/**
	 * @return the scroll direction
	 */
	public ScrollDirection getScrollDirection() {
		return m_scroll;
	}

	/**
	 * Sets the direction in which old Notifications will scroll after new ones
	 * have been added.
	 * 
	 * @param dir
	 */
	public void setScrollDirection(ScrollDirection dir) {
		m_scroll = dir;
	}

	/**
	 * Stops controlling the Notifications and leaves them in their current
	 * positions. They will still hide after the specified time, however.
	 */
	public void stop() {
		m_timer.stop();
	}

	private class MovementManager implements ActionListener {
		private int getPreviousShownIndex(List<Notification> notes,
				int startIndex) {
			for (int i = startIndex; i < notes.size(); i++) {
				if (notes.get(i).isShown())
					return i;
			}
			return -1;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			List<Notification> notes = getNotifications();

			PopupLocation loc = getLocation();
			int x = getScreen().getX(loc);
			int y = getScreen().getY(loc);

			for (int i = notes.size() - 1; i >= 0; i--) {
				Notification note = notes.get(i);
				int prevIndex = getPreviousShownIndex(notes, i + 1);

				if (prevIndex == -1) {
					note.setLocation(x, y);
					continue;
				}

				// not really sure why this code works, but it does
				Notification prev = notes.get(prevIndex);
				int dif = note.getY() - prev.getY();
				int desdif;
				if (m_scroll == ScrollDirection.SOUTH) {
					desdif = prev.getHeight() + m_verticalPadding;
				} else {
					desdif = -prev.getHeight() - m_verticalPadding;
				}

				int delta = desdif - dif;
				int setNum = delta / 3;
				if (delta == 0) {
					continue;
				}
				if (Math.abs(delta) < 3) {
					setNum = delta > 0 ? 1 : -1;
				}
				// System.out.println("Setting loc");
				note.setLocation(note.getX(), note.getY() + setNum);
			}
		}
	}
}
