package com.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JWindow;

import org.junit.Test;

import com.notification.Notification;
import com.notification.NotificationListener;
import com.notification.types.TextNotification;
import com.notification.types.WindowNotification;
import com.theme.WindowTheme;

public class WindowNotificationTest {
	@Test
	public void opacityShouldNotExceed1() {
		WindowNotification note = new TextNotification(); // needs an instance of a concrete class
		note.setOpacity(2);
		assertEquals("WindowNotification opacity should not exceed 1", 1, note.getOpacity(), TestUtils.TINY_DELTA);
	}

	@Test
	public void opacityShouldNotGoBelow0() {
		WindowNotification note = new TextNotification();
		note.setOpacity(-1);
		assertEquals("WindowNotification opacity should not go below 0", 0, note.getOpacity(), TestUtils.TINY_DELTA);
	}

	@Test
	public void showAndHideShouldMakeWindowVisibleAndInvisible() {
		WindowNotification note = new TextNotification();
		note.show();
		assertTrue("Note should be visible after shown", note.isShown());
		note.hide();
		assertFalse("Note should not be visible after shown", note.isShown());
	}

	@Test
	public void showShouldFireListeners() {
		WindowNotification note = new TextNotification();
		NotificationListener listener = new NotificationListener() {
			@Override
			public void actionCompleted(Notification note, String action) {
				assertEquals("Show should trigger SHOWN message", WindowNotification.SHOWN, action);
			}
		};
		note.addNotificationListener(listener);
		note.show();
		note.removeNotificationListener(listener);
		note.hide();
	}

	@Test
	public void hideShouldFireListeners() {
		WindowNotification note = new TextNotification();
		NotificationListener listener = new NotificationListener() {
			@Override
			public void actionCompleted(Notification note, String action) {
				assertEquals("Hide should trigger HIDDEN message", WindowNotification.HIDDEN, action);
			}
		};
		note.show();
		note.addNotificationListener(listener);
		note.hide();
		note.removeNotificationListener(listener);
	}

	@Test
	public void clickShouldFireListeners() {
		ClickNotification note = new ClickNotification();
		NotificationListener listener = new NotificationListener() {
			@Override
			public void actionCompleted(Notification note, String action) {
				assertEquals("Click should trigger CLICKED message", WindowNotification.CLICKED, action);
			}
		};
		note.show();
		note.addNotificationListener(listener);
		note.simulateClick();
		note.removeNotificationListener(listener);
	}

	private class ClickNotification extends WindowNotification {
		public void simulateClick() {
			JPanel panel = new JPanel();
			setPanel(panel);
			MouseEvent me = new MouseEvent(panel, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 10, 10, 1, false);
			panel.dispatchEvent(me);
		}
	}

	@Test
	public void setWindowThemeShouldRecurse() {
		RecursiveTestNotification note = new RecursiveTestNotification();
		WindowTheme theme = new WindowTheme();
		theme.foreground = Color.RED;
		theme.background = Color.BLUE;
		note.setWindowTheme(theme);
		note.assertMatchesWindowTheme(theme);
	}

	private class RecursiveTestNotification extends WindowNotification {
		private JPanel m_panel;
		private JPanel m_subPanel;

		public RecursiveTestNotification() {
			m_panel = new JPanel();
			m_subPanel = new JPanel();
			super.setPanel(m_panel);
			m_panel.add(m_subPanel);
		}

		public void assertMatchesWindowTheme(WindowTheme theme) {
			assertEquals("Main panel background color should match WindowTheme", theme.background, m_panel.getBackground());
			assertEquals("Sub panel background color should match WindowTheme", theme.background, m_subPanel.getBackground());
			assertEquals("Main panel foreground color should match WindowTheme", theme.foreground, m_panel.getForeground());
			assertEquals("Sub panel foreground color should match WindowTheme", theme.foreground, m_subPanel.getForeground());
		}
	}

	@Test
	public void windowThemeShouldApply() {
		WindowAccessNotification note = new WindowAccessNotification();
		WindowTheme theme = new WindowTheme();
		theme.foreground = Color.RED;
		theme.background = Color.BLUE;
		theme.opacity = 0.5;
		theme.width = 100;
		theme.height = 400;
		note.setWindowTheme(theme);

		assertEquals("Window opacity should equal theme opacity", theme.opacity, note.getOpacity(), TestUtils.TINY_DELTA);
		assertEquals("Window width should equal theme width", theme.width, note.getWidth());
		assertEquals("Window height should equal theme height", theme.height, note.getHeight());
		assertEquals("Window foreground should equal theme foreground", theme.foreground, note.getInternalWindow()
				.getForeground());
		assertEquals("Window background should equal theme background", theme.background, note.getInternalWindow()
				.getBackground());
	}

	private class WindowAccessNotification extends WindowNotification {
		public JWindow getInternalWindow() {
			return getWindow();
		}
	}
}
