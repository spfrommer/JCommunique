package com.notification.manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import com.notification.Notification;
import com.notification.NotificationFactory.Location;

/**
 * A NotificationManager which slides old Notifications above or below new Notifications.
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

	public QueueManager(Location loc) {
		super(loc);
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
	 *            the padding that should be maintained between the bottom of the top Notification and the top of the
	 *            bottom Notification, in pixels
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
	 * Sets the direction in which old Notifications will scroll after new ones have been added.
	 *
	 * @param dir
	 *            the direction to scroll in
	 */
	public void setScrollDirection(ScrollDirection dir) {
		m_scroll = dir;
	}

	/**
	 * Stops controlling the Notifications and leaves them in their current positions. They will still hide after the
	 * specified time, however.
	 */
	public void stop() {
		m_timer.stop();
	}

	private class MovementManager implements ActionListener {
		private int getPreviousShownIndex(List<Notification> notes, int startIndex) {
			for (int i = startIndex; i < notes.size(); i++) {
				if (notes.get(i).isShown())
					return i;
			}
			return -1;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			List<Notification> notes = getNotifications();
			if (notes.size() == 0)
				return;

			Location loc = getLocation();
			int x = getScreen().getX(loc, notes.get(0));
			int y = getScreen().getY(loc, notes.get(0));

			for (int i = notes.size() - 1; i >= 0; i--) {
				Notification note = notes.get(i);
				int prevIndex = getPreviousShownIndex(notes, i + 1);

				int dif = 0;
				int desdif = 0;
				if (prevIndex == -1) {
					dif = note.getY() - y;
					desdif = 0;
				} else {
					Notification prev = notes.get(prevIndex);
					dif = note.getY() - prev.getY();
					if (m_scroll == ScrollDirection.SOUTH) {
						desdif = prev.getHeight() + m_verticalPadding;
					} else {
						desdif = -prev.getHeight() - m_verticalPadding;
					}
				}

				// not really sure why this code works, but it does
				int delta = desdif - dif;
				int setNum = delta / 3;
				if (delta == 0) {
					continue;
				}
				if (Math.abs(delta) < 3) {
					setNum = delta > 0 ? 1 : -1;
				}
				note.setLocation(x, note.getY() + setNum);
			}
		}
	}
}
