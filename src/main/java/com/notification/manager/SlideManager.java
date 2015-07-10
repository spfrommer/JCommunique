package com.notification.manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.Timer;

import com.notification.Notification;
import com.notification.NotificationFactory.Location;
import com.notification.NotificationManager;
import com.utils.Screen;
import com.utils.Time;

/**
 * Slides Notifications into a certain area. This may not work on all machines.
 */
public class SlideManager extends NotificationManager {
	private Location m_loc;

	private Screen m_standardScreen;
	private Screen m_noPaddingScreen;

	private HashMap<SlideDirection, Slider> m_sliders;
	private SlideDirection m_slideIn;
	private SlideDirection m_slideOut;
	private double m_slideSpeed;

	private HashMap<Notification, SlideDirection> m_slideOutDirections;
	// store the return directions so that even if the slideIn and slideOut values are changed current Notifications are
	// not affected

	private boolean m_overwrite;

	public enum SlideDirection {
		NORTH, SOUTH(SlideDirection.NORTH), EAST, WEST(SlideDirection.EAST);

		private SlideDirection m_opposite;

		SlideDirection() {

		}

		SlideDirection(SlideDirection opposite) {
			m_opposite = opposite;
			opposite.m_opposite = this;
		}

		public SlideDirection getOpposite() {
			return m_opposite;
		}
	}

	{
		m_sliders = new HashMap<SlideDirection, Slider>();
		m_sliders.put(SlideDirection.NORTH, new NorthSlider());
		m_sliders.put(SlideDirection.SOUTH, new SouthSlider());
		m_sliders.put(SlideDirection.EAST, new EastSlider());
		m_sliders.put(SlideDirection.WEST, new WestSlider());
		m_standardScreen = Screen.standard();
		m_noPaddingScreen = Screen.withPadding(0);
		m_slideSpeed = 300;
		m_slideOutDirections = new HashMap<Notification, SlideDirection>();
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
	 *            the Location to show at
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
		return m_slideIn;
	}

	/**
	 * Sets the direction that the Notification should slide to.
	 *
	 * @param slide
	 *            the direction that Notifications should slide to
	 */
	public void setSlideDirection(SlideDirection slide) {
		m_slideIn = slide;
		m_slideOut = slide.getOpposite();
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
	 *            the speed of the slide in pixels / second
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
		case NORTH:
		case NORTHEAST:
			m_slideIn = SlideDirection.SOUTH;
			break;
		case EAST:
			m_slideIn = SlideDirection.WEST;
			break;
		case SOUTHEAST:
		case SOUTH:
		case SOUTHWEST:
			m_slideIn = SlideDirection.NORTH;
			break;
		case WEST:
			m_slideIn = SlideDirection.EAST;
			break;
		}
		m_slideOut = m_slideIn.getOpposite();
	}

	@Override
	protected void notificationAdded(Notification note, Time time) {
		double delay = 50;
		double slideDelta = m_slideSpeed / delay;
		m_sliders.get(m_slideIn).setBorderPosition(note);
		m_sliders.get(m_slideIn).animate(note, delay, slideDelta, true);
		note.show();

		double slideTime = m_standardScreen.getPadding() / m_slideSpeed;
		scheduleRemoval(note, time.add(Time.seconds(slideTime)));

		m_slideOutDirections.put(note, m_slideOut);
	}

	@Override
	protected void notificationRemoved(Notification note) {
		double delay = 50;
		double slideDelta = m_slideSpeed / delay;
		m_sliders.get(m_slideOutDirections.get(note)).animate(note, delay, slideDelta, false);
		m_slideOutDirections.remove(note);
	}

	private abstract class Slider implements ActionListener {
		protected Notification m_note;
		protected double m_delta;
		protected double m_stopX;
		protected double m_stopY;

		protected double m_x;
		protected double m_y;

		protected boolean m_slideIn;

		public void animate(Notification note, double delay, double slideDelta, boolean slideIn) {
			m_note = note;
			m_x = note.getX();
			m_y = note.getY();
			m_delta = Math.abs(slideDelta);
			m_slideIn = slideIn;
			if (m_slideIn) {
				m_stopX = m_standardScreen.getX(m_loc, note);
				m_stopY = m_standardScreen.getY(m_loc, note);
			} else {
				m_stopX = m_noPaddingScreen.getX(m_loc, note);
				m_stopY = m_noPaddingScreen.getY(m_loc, note);
			}

			Timer timer = new Timer((int) delay, this);
			timer.start();
		}

		protected void manageStop(ActionEvent e) {
			((Timer) e.getSource()).stop();
			if (!m_slideIn)
				m_note.hide();
		}

		public abstract void setBorderPosition(Notification note);
	}

	private class NorthSlider extends Slider {
		@Override
		public void actionPerformed(ActionEvent e) {
			m_y -= m_delta;

			if (m_y <= m_stopY) {
				m_y = m_stopY;
				manageStop(e);
			}

			m_note.setLocation((int) (m_x), (int) (m_y));
		}

		@Override
		public void setBorderPosition(Notification note) {
			note.setLocation(m_standardScreen.getX(m_loc, note), m_noPaddingScreen.getY(m_loc, note));
		}
	}

	private class SouthSlider extends Slider {
		@Override
		public void actionPerformed(ActionEvent e) {
			m_y += m_delta;

			if (m_y >= m_stopY) {
				m_y = m_stopY;
				manageStop(e);
			}

			m_note.setLocation((int) (m_x), (int) (m_y));
		}

		@Override
		public void setBorderPosition(Notification note) {
			note.setLocation(m_standardScreen.getX(m_loc, note), m_noPaddingScreen.getY(m_loc, note));
		}
	}

	private class EastSlider extends Slider {
		@Override
		public void actionPerformed(ActionEvent e) {
			m_x += m_delta;

			if (m_x >= m_stopX) {
				m_x = m_stopX;
				manageStop(e);
			}

			m_note.setLocation((int) (m_x), (int) (m_y));
		}

		@Override
		public void setBorderPosition(Notification note) {
			note.setLocation(m_noPaddingScreen.getX(m_loc, note), m_standardScreen.getY(m_loc, note));
		}
	}

	private class WestSlider extends Slider {
		@Override
		public void actionPerformed(ActionEvent e) {
			m_x -= m_delta;

			if (m_x <= m_stopX) {
				m_x = m_stopX;
				manageStop(e);
			}

			m_note.setLocation((int) (m_x), (int) (m_y));
		}

		@Override
		public void setBorderPosition(Notification note) {
			note.setLocation(m_noPaddingScreen.getX(m_loc, note), m_standardScreen.getY(m_loc, note));
		}
	}

}
