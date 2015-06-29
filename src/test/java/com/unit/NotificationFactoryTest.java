package com.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.swing.ImageIcon;

import org.junit.Test;

import com.notification.NotificationBuilder;
import com.notification.NotificationFactory;
import com.notification.types.AcceptNotification;
import com.notification.types.IconNotification;
import com.notification.types.ProgressBarNotification;
import com.notification.types.TextNotification;
import com.notification.types.WindowNotification;
import com.theme.ThemePackage;
import com.theme.ThemePackagePresets;

public class NotificationFactoryTest {
	private static final String TITLE = "title";
	private static final String SUBTITLE = "subtitle";
	private static final String ACCEPT = "Accept!";
	private static final String DECLINE = "Decline!";
	private static final ImageIcon ICON = new ImageIcon();
	private static final ThemePackage s_pack = ThemePackagePresets.aqua();

	@Test
	public void testTextNotification() {
		NotificationFactory factory = new NotificationFactory(s_pack);
		TextNotification note = factory.buildTextNotification(TITLE, SUBTITLE);
		assertEquals("TextNotification should have the same title", TITLE, note.getTitle());
		assertEquals("TextNotification should have the same subtitle", SUBTITLE, note.getSubtitle());
	}

	@Test
	public void testAcceptNotification() {
		NotificationFactory factory = new NotificationFactory(s_pack);
		AcceptNotification note = factory.buildAcceptNotification(TITLE, SUBTITLE, ACCEPT, DECLINE);
		assertEquals("AcceptNotification should have the same title", TITLE, note.getTitle());
		assertEquals("AcceptNotification should have the same subtitle", SUBTITLE, note.getSubtitle());
		assertEquals("AcceptNotification should have the same accept text", ACCEPT, note.getAcceptText());
		assertEquals("AcceptNotification should have the same decline text", DECLINE, note.getDeclineText());
	}

	@Test
	public void testIconNotification() {
		NotificationFactory factory = new NotificationFactory(s_pack);
		IconNotification note = factory.buildIconNotification(TITLE, SUBTITLE, ICON);
		assertEquals("IconNotification should have the same title", TITLE, note.getTitle());
		assertEquals("IconNotification should have the same subtitle", SUBTITLE, note.getSubtitle());
		assertEquals("IconNotification should have the same icon", ICON, note.getIcon());
	}

	@Test
	public void testProgressNotification() {
		NotificationFactory factory = new NotificationFactory(s_pack);
		ProgressBarNotification note = factory.buildProgressBarNotification(TITLE);
		assertEquals("ProgressBarNotification should have the same title", TITLE, note.getTitle());
	}

	@Test
	public void testCustomBuilder() {
		NotificationFactory factory = new NotificationFactory(s_pack);
		factory.addBuilder(CustomNotification.class, new CustomBuilder());
		CustomNotification note = factory.build(CustomNotification.class);
		assertNotNull("The built notification should not be null", note);
	}

	private class CustomBuilder implements NotificationBuilder<CustomNotification> {
		@Override
		public CustomNotification buildNotification(ThemePackage pack, Object... args) {
			return new CustomNotification();
		}
	}

	private class CustomNotification extends WindowNotification {

	}
}
