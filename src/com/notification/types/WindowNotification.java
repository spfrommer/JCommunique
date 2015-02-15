package com.notification.types;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JWindow;

import com.notification.Notification;
import com.theme.WindowTheme;

/**
 * A Notification which displays in a JWindow, handles click events, and allows
 * subclasses to supply a JPanel. The default Notification dimensions are set;
 * if subclasses want to override this, they can do so in their constructors.
 */
public abstract class WindowNotification extends Notification {
	private JWindow m_window;
	private JPanel m_panel;
	private boolean m_closeOnClick;

	private WindowTheme m_theme;

	private MouseAdapter m_listener;

	public static final int DEFAULT_WIDTH = 300;
	public static final int DEFAULT_HEIGHT = 100;

	public WindowNotification() {
		m_window = new JWindow();
		m_window.setAlwaysOnTop(true);

		m_listener = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				fireListeners();
				if (m_closeOnClick)
					hide();
			}
		};

		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	protected JWindow getWindow() { return m_window; }

	protected void setPanel(JPanel panel) {
		if (m_panel != null) {
			m_window.remove(m_panel);
			m_panel.removeMouseListener(m_listener);
		}

		m_panel = panel;

		m_window.add(m_panel);
		m_panel.addMouseListener(m_listener);
	}

	/**
	 * @return whether or not the Notification should close when it's clicked
	 */
	public boolean isCloseOnClick() {
		return m_closeOnClick;
	}

	/**
	 * @param close
	 *            whether or not the Notification should close when it's clicked
	 */
	public void setCloseOnClick(boolean close) {
		m_closeOnClick = close;
	}

	protected WindowTheme getWindowTheme() {
		return m_theme;
	}

	/**
	 * Sets the theme of the WindowNotification. It is up to the subclasses how
	 * they want to interpret the "image" attribute of the theme.
	 * 
	 * @param theme
	 */
	public void setWindowTheme(WindowTheme theme) {
		m_theme = theme;

		m_window.setBackground(theme.background);
		m_window.setForeground(theme.foreground);
		m_window.setOpacity(theme.opacity);

		m_panel.setBackground(theme.background);
		m_panel.setForeground(theme.foreground);

		themeSet(theme);
	}

	protected abstract void themeSet(WindowTheme theme);

	@Override
	public int getX() {
		return m_window.getX();
	}

	@Override
	public int getY() {
		return m_window.getY();
	}

	@Override
	public void setLocation(int x, int y) {
		m_window.setLocation(x, y);
	}

	@Override
	public int getWidth() {
		return m_window.getWidth();
	}

	@Override
	public int getHeight() {
		return m_window.getHeight();
	}

	@Override
	public void setSize(int width, int height) {
		m_window.setSize(width, height);
	}

	/**
	 * Gets the opacity of the window.
	 * 
	 * @return
	 */
	@Override
	public float getOpacity() {
		return m_window.getOpacity();
	}

	/**
	 * Sets the opacity, overriding the value given in the window theme.
	 * 
	 * @param opacity
	 *            the opacity between MINIMUM_OPACITY and 1f. The bottom limit
	 *            is to ensure that it looks good on all platforms.
	 */
	@Override
	public void setOpacity(float opacity) {
		if (opacity < Notification.MINIMUM_OPACITY) {
			opacity = Notification.MINIMUM_OPACITY;
		}

		if (opacity > 1f) {
			opacity = 1f;
		}

		m_window.setOpacity(opacity);
	}

	@Override
	protected void directSetOpacity(float opacity) {
		if (opacity < 0) {
			opacity = 0;
		}

		if (opacity > 1f) {
			opacity = 1f;
		}

		m_window.setOpacity(opacity);
	}

	@Override
	public void show() {
		m_window.setVisible(true);
	}

	@Override
	public void hide() {
		m_window.setVisible(false);
	}

	@Override
	public boolean isShown() {
		return m_window.isVisible();
	}
}
