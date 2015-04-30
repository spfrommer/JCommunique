package com.manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.notification.Notification;
import com.notification.NotificationFactory.Location;
import com.utils.Screen;
import com.utils.Time;

/**
 * Slides Notifications into a certain area. This may not work on all machines.
 */
public class SlideManager extends NotificationManager {
	private Location m_loc;
	private Screen m_standardScreen;
	private Screen m_noPaddingScreen;
	private SlideDirection m_direction;
	private double m_slideSpeed;
	private boolean m_overwrite;

	public enum SlideDirection {
		NORTH, SOUTH, EAST, WEST
	}

	{
		m_standardScreen = Screen.standard();
		m_noPaddingScreen = Screen.withPadding(0);
		m_slideSpeed = 300;
		m_overwrite = false;
	}

	public SlideManager() {
		m_loc = Location.NORTHEAST;
		recalculateSlideDirection();
	}

	public SlideManager(Location loc) {
		m_loc = loc;
		recalculateSlideDirection();
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
		if (!m_overwrite)
			recalculateSlideDirection();
	}

	/**
	 * @return the direction that the Notification should slide in from
	 */
	public SlideDirection getSlideDirection() {
		return m_direction;
	}

	/**
	 * Sets the direction that the Notification should slide in from.
	 *
	 * @param slide
	 */
	public void setSlideDirection(SlideDirection slide) {
		m_direction = slide;
		m_overwrite = true;
	}

	/**
	 * @return how fast the Notification should in pixels / second
	 */
	public double getSlideSpeed() {
		return m_slideSpeed;
	}

	/**
	 * Sets how fast the Notification should slide in pixels / second.
	 *
	 * @param slideSpeed
	 */
	public void setSlideSpeed(double slideSpeed) {
		m_slideSpeed = slideSpeed;
	}

	protected Screen getScreen() {
		return m_standardScreen;
	}

	private void recalculateSlideDirection() {
		switch (m_loc) {
		case NORTHWEST:
			m_direction = SlideDirection.SOUTH;
			break;
		case NORTH:
			m_direction = SlideDirection.SOUTH;
			break;
		case NORTHEAST:
			m_direction = SlideDirection.SOUTH;
			break;
		case EAST:
			m_direction = SlideDirection.WEST;
			break;
		case SOUTHEAST:
			m_direction = SlideDirection.NORTH;
			break;
		case SOUTH:
			m_direction = SlideDirection.NORTH;
			break;
		case SOUTHWEST:
			m_direction = SlideDirection.NORTH;
			break;
		case WEST:
			m_direction = SlideDirection.EAST;
			break;
		}
	}

	@Override
	protected void notificationAdded(Notification note, Time time) {
		int noPaddingX = m_noPaddingScreen.getX(m_loc, note);
		int noPaddingY = m_noPaddingScreen.getY(m_loc, note);
		int standardX = m_standardScreen.getX(m_loc, note);
		int standardY = m_standardScreen.getY(m_loc, note);

		Slider slider = null;
		double frequency = 50;
		double slideDelta = m_slideSpeed / frequency;

		switch (m_direction) {
		case NORTH: {
			note.setLocation(standardX, noPaddingY);
			slider = new Slider(note, m_direction, 0, -slideDelta, standardX, standardY);
		}
			break;
		case SOUTH: {
			note.setLocation(standardX, noPaddingY);
			slider = new Slider(note, m_direction, 0, slideDelta, standardX, standardY);
		}
			break;
		case EAST: {
			note.setLocation(noPaddingX, standardY);
			slider = new Slider(note, m_direction, slideDelta, 0, standardX, standardY);
		}
			break;
		case WEST:
			note.setLocation(noPaddingX, standardY);
			slider = new Slider(note, m_direction, -slideDelta, 0, standardX, standardY);
			break;
		}

		Timer timer = new Timer((int) frequency, slider);
		timer.start();
		note.show();
	}

	@Override
	protected void notificationRemoved(Notification note) {

	}

	public class Slider implements ActionListener {
		private Notification m_note;
		private SlideDirection m_dir;
		private double m_deltaX;
		private double m_deltaY;
		private double m_stopX;
		private double m_stopY;

		private double m_x;
		private double m_y;

		public Slider(Notification note, SlideDirection dir, double deltaX, double deltaY, double stopX, double stopY) {
			m_note = note;
			m_dir = dir;
			m_deltaX = deltaX;
			m_deltaY = deltaY;
			m_stopX = stopX;
			m_stopY = stopY;

			m_x = note.getX();
			m_y = note.getY();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			m_x += m_deltaX;
			m_y += m_deltaY;

			if (m_dir == SlideDirection.SOUTH) {
				if (m_y >= m_stopY) {
					m_y = m_stopY;
					((Timer) e.getSource()).stop();
				}
			} else if (m_dir == SlideDirection.NORTH) {
				if (m_y <= m_stopY) {
					m_y = m_stopY;
					((Timer) e.getSource()).stop();
				}
			} else if (m_dir == SlideDirection.EAST) {
				if (m_x >= m_stopX) {
					m_x = m_stopX;
					((Timer) e.getSource()).stop();
				}
			} else if (m_dir == SlideDirection.WEST) {
				if (m_x <= m_stopX) {
					m_x = m_stopX;
					((Timer) e.getSource()).stop();
				}
			}

			m_note.setLocation((int) (m_x), (int) (m_y));
		}
	}
}
