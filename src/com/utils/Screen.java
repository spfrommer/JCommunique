package com.utils;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import com.notification.NotificationFactory.Location;

public class Screen {
	private int m_width;
	private int m_height;

	private int m_leftX;
	private int m_centerX;
	private int m_rightX;

	private int m_topY;
	private int m_centerY;
	private int m_bottomY;

	public static final int PADDING = 80;

	/**
	 * @param spanMultiple
	 *            whether the Screen should span all monitors on a multi-monitor device
	 */
	public Screen(boolean spanMultiple) {
		if (spanMultiple) {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			m_width = screenSize.width;
			m_height = screenSize.height;

		} else {
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			m_width = gd.getDisplayMode().getWidth();
			m_height = gd.getDisplayMode().getHeight();
		}

		calculatePositions();
	}

	private void calculatePositions() {
		m_leftX = PADDING;
		m_centerX = (int) (m_width / 2d);
		m_rightX = m_width - PADDING;

		m_topY = PADDING;
		m_centerY = (int) (m_height / 2d);
		m_bottomY = m_height - PADDING;
	}

	public int getX(Location loc) {
		switch (loc) {
		case SOUTHWEST:
			return m_leftX;
		case WEST:
			return m_leftX;
		case NORTHWEST:
			return m_leftX;
		case NORTH:
			return m_centerX;
		case SOUTH:
			return m_centerX;
		case SOUTHEAST:
			return m_rightX;
		case EAST:
			return m_rightX;
		case NORTHEAST:
			return m_rightX;
		default:
			return -1;
		}
	}

	public int getY(Location loc) {
		switch (loc) {
		case SOUTHWEST:
			return m_bottomY;
		case WEST:
			return m_centerY;
		case NORTHWEST:
			return m_topY;
		case NORTH:
			return m_topY;
		case SOUTH:
			return m_bottomY;
		case SOUTHEAST:
			return m_bottomY;
		case EAST:
			return m_centerY;
		case NORTHEAST:
			return m_topY;
		default:
			return -1;
		}
	}
}
