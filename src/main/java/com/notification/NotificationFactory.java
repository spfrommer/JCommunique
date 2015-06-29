package com.notification;

import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.exception.NotificationException;
import com.notification.types.AcceptNotification;
import com.notification.types.IconNotification;
import com.notification.types.ProgressBarNotification;
import com.notification.types.TextNotification;
import com.theme.ThemePackage;
import com.theme.ThemePackagePresets;

/**
 * Creates Notifications using a ThemePackage. It is possible to add custom Notifications by adding
 * NotificationBuilders.
 */
public final class NotificationFactory {
	private ThemePackage m_pack;
	private HashMap<Class<? extends Notification>, NotificationBuilder<? extends Notification>> m_builders;

	public enum Location {
		NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST
	}

	{
		m_builders = new HashMap<Class<? extends Notification>, NotificationBuilder<? extends Notification>>();
		m_builders.put(TextNotification.class, new TextNotificationBuilder());
		m_builders.put(AcceptNotification.class, new AcceptNotificationBuilder());
		m_builders.put(IconNotification.class, new IconNotificationBuilder());
		m_builders.put(ProgressBarNotification.class, new ProgressBarNotificationBuilder());
	}

	public NotificationFactory() {
		setThemePackage(ThemePackagePresets.cleanLight());
	}

	public NotificationFactory(ThemePackage pack) {
		setThemePackage(pack);
	}

	/**
	 * Sets the themes that the factory should use when creating notifications. See ThemePackagePresets for some default
	 * themes.
	 *
	 * @param pack
	 */
	public void setThemePackage(ThemePackage pack) {
		m_pack = pack;
	}

	/**
	 * Adds a custom NotificationBuilder. Notifications can then be built using build(Class notificationClass).
	 *
	 * @param notificationClass
	 * @param builder
	 */
	public <T extends Notification> void addBuilder(Class<T> notificationClass, NotificationBuilder<T> builder) {
		m_builders.put(notificationClass, builder);
	}

	/**
	 * Removes the NotificationBuilder associated with this notification class.
	 *
	 * @param notificationClass
	 */
	public <T extends Notification> void removeBuilder(Class<T> notificationClass) {
		m_builders.remove(notificationClass);
	}

	/**
	 * Builds a Notification using the NotificationBuilder associated with the notification class.
	 *
	 * @param notificationClass
	 * @return
	 */
	public <T extends Notification> T build(Class<T> notificationClass) {
		if (!m_builders.containsKey(notificationClass))
			throw new RuntimeException("No NotificationBuilder for: " + notificationClass);

		@SuppressWarnings("unchecked")
		T note = (T) m_builders.get(notificationClass).buildNotification(m_pack, new Object[0]);
		return note;
	}

	/**
	 * Builds a Notification using the NotificationBuilder associated with the notification class.
	 *
	 * @param notificationClass
	 * @param args
	 *            the args passed to the NotificationBuilder
	 * @return
	 */
	public <T extends Notification> T build(Class<T> notificationClass, Object... args) {
		if (!m_builders.containsKey(notificationClass))
			throw new RuntimeException("No NotificationBuilder for: " + notificationClass);

		@SuppressWarnings("unchecked")
		T note = (T) m_builders.get(notificationClass).buildNotification(m_pack, args);
		return note;
	}

	/**
	 * Builds a SimpleTextNotification.
	 *
	 * @param title
	 * @param subtitle
	 * @return
	 */
	public TextNotification buildTextNotification(String title, String subtitle) {
		return build(TextNotification.class, title, subtitle);
	}

	/**
	 * Builds an AcceptNotification with "Accept" and "Decline" as the button messages.
	 *
	 * @param title
	 * @param subtitle
	 * @return
	 */
	public AcceptNotification buildAcceptNotification(String title, String subtitle) {
		return build(AcceptNotification.class, title, subtitle);
	}

	/**
	 * Builds an AcceptNotification with the specified Strings.
	 *
	 * @param title
	 * @param subtitle
	 * @param accept
	 *            the text on the accept button
	 * @param decline
	 *            the text on the decline button
	 * @return
	 */
	public AcceptNotification buildAcceptNotification(String title, String subtitle, String accept, String decline) {
		return build(AcceptNotification.class, title, subtitle, accept, decline);
	}

	/**
	 * Builds an IconNotification.
	 *
	 * @param title
	 * @param subtitle
	 * @param icon
	 * @return
	 */
	public IconNotification buildIconNotification(String title, String subtitle, ImageIcon icon) {
		return build(IconNotification.class, title, subtitle, icon);
	}

	/**
	 * Builds a ProgressBarNotification.
	 *
	 * @param title
	 * @return
	 */
	public ProgressBarNotification buildProgressBarNotification(String title) {
		return build(ProgressBarNotification.class, title);
	}

	private class TextNotificationBuilder implements NotificationBuilder<TextNotification> {
		@Override
		public TextNotification buildNotification(ThemePackage pack, Object... args) {
			if (args.length != 2)
				throw new NotificationException("TextNotifications need two arguments: title, subtitle!");

			TextNotification note = new TextNotification();
			note.setWindowTheme(pack.windowTheme);
			note.setTextTheme(pack.textTheme);
			note.setTitle((String) args[0]);
			note.setSubtitle((String) args[1]);
			return note;
		}
	}

	private class AcceptNotificationBuilder implements NotificationBuilder<AcceptNotification> {
		@Override
		public AcceptNotification buildNotification(ThemePackage pack, Object... args) {
			if (args.length != 2 && args.length != 4)
				throw new NotificationException(
						"AcceptNotifications need two or four arguments: title, subtitle, accept text, decline text!");

			AcceptNotification note = new AcceptNotification();
			note.setWindowTheme(pack.windowTheme);
			note.setTextTheme(pack.textTheme);
			note.setTitle((String) args[0]);
			note.setSubtitle((String) args[1]);
			if (args.length == 4) {
				note.setAcceptText((String) args[2]);
				note.setDeclineText((String) args[3]);
			}

			return note;
		}
	}

	private class IconNotificationBuilder implements NotificationBuilder<IconNotification> {
		@Override
		public IconNotification buildNotification(ThemePackage pack, Object... args) {
			if (args.length != 3)
				throw new NotificationException("IconNotifications need three arguments: title, subtitle, icon!");

			IconNotification note = new IconNotification();
			note.setWindowTheme(pack.windowTheme);
			note.setTextTheme(pack.textTheme);
			note.setTitle((String) args[0]);
			note.setSubtitle((String) args[1]);
			note.setIcon((Icon) args[2]);
			return note;
		}
	}

	private class ProgressBarNotificationBuilder implements NotificationBuilder<ProgressBarNotification> {
		@Override
		public ProgressBarNotification buildNotification(ThemePackage pack, Object... args) {
			if (args.length != 1)
				throw new NotificationException("ProgressBarNotifications need one argument: title!");

			ProgressBarNotification note = new ProgressBarNotification();
			note.setWindowTheme(pack.windowTheme);
			note.setTextTheme(pack.textTheme);
			note.setTitle((String) args[0]);
			return note;
		}
	}
}
