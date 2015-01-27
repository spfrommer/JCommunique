package com.notification;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;

/**
 * Lays out Swing Components in a BorderLayout.
 */
public class ComponentNotification extends WindowNotification {
	private JPanel m_panel;

	public ComponentNotification() {
		super();

		m_panel = new JPanel(new BorderLayout());
		setPanel(m_panel);
	}

	/**
	 * Adds a Component to the Notification.
	 * 
	 * @param comp
	 * @param borderLayout
	 *            the BorderLayout String, e.g. BorderLayout.NORTH
	 */
	public void addComponent(Component comp, String borderLayout) {
		m_panel.add(comp, borderLayout);
	}

	@Override
	protected void themeSet(WindowTheme theme) {

	}
}
