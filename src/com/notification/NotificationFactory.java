package com.notification;

import java.util.HashMap;

import javax.swing.ImageIcon;

import com.notification.types.AcceptNotification;
import com.notification.types.IconNotification;
import com.notification.types.TextNotification;
import com.theme.ThemePackage;
import com.theme.ThemePackagePresets;

/**
 * Creates Notifications using a ThemePackage.
 */
public final class NotificationFactory {
	private ThemePackage m_pack;
	private HashMap<String, NotificationBuilder> m_builders = new HashMap<String, NotificationBuilder>();

	public enum Location {
		NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST
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
	 * Adds a custom NotificationBuilder. Notifications can then be built using build(String name).
	 * 
	 * @param name
	 * @param builder
	 */
	public void addBuilder(String name, NotificationBuilder builder) {
		m_builders.put(name, builder);
	}

	/**
	 * Removes the NotificationBuilder associated with this name.
	 * 
	 * @param name
	 */
	public void removeBuilder(String name) {
		m_builders.remove(name);
	}

	/**
	 * Builds a Notification using the NotificationBuilder associated with the name.
	 * 
	 * @param name
	 * @return
	 */
	public Notification build(String name) {
		if (!m_builders.containsKey(name))
			throw new RuntimeException("No NotificationBuilder for: " + name);
		Notification note = m_builders.get(name).buildNotification(m_pack, new Object[0]);
		return note;
	}

	/**
	 * Builds a Notification using the NotificationBuilder associated with the name.
	 * 
	 * @param name
	 * @param args
	 *            the args passed to the NotificationBuilder
	 * @return
	 */
	public Notification build(String name, Object... args) {
		if (!m_builders.containsKey(name))
			throw new RuntimeException("No NotificationBuilder for: " + name);
		Notification note = m_builders.get(name).buildNotification(m_pack, args);
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
		TextNotification text = new TextNotification();
		text.setWindowTheme(m_pack.windowTheme);
		text.setTextTheme(m_pack.textTheme);
		text.setTitle(title);
		text.setSubtitle(subtitle);

		return text;
	}

	/**
	 * Builds an AcceptNotification with "Accept" and "Decline" as the button messages.
	 * 
	 * @param title
	 * @param subtitle
	 * @return
	 */
	public AcceptNotification buildAcceptNotification(String title, String subtitle) {
		AcceptNotification accept = new AcceptNotification();
		accept.setWindowTheme(m_pack.windowTheme);
		accept.setTextTheme(m_pack.textTheme);
		accept.setTitle(title);
		accept.setSubtitle(subtitle);
		accept.setAcceptText("Accept");
		accept.setDeclineText("Decline");

		return accept;
	}

	/**
	 * Builds an AcceptNotification with the specified Strings.
	 * 
	 * @param title
	 * @param subtitle
	 * @param acceptText
	 *            the text on the accept button
	 * @param declineText
	 *            the text on the decline button
	 * @return
	 */
	public AcceptNotification buildAcceptNotification(String title, String subtitle, String acceptText, String declineText) {
		AcceptNotification accept = new AcceptNotification();
		accept.setWindowTheme(m_pack.windowTheme);
		accept.setTextTheme(m_pack.textTheme);
		accept.setTitle(title);
		accept.setSubtitle(subtitle);
		accept.setAcceptText(acceptText);
		accept.setDeclineText(declineText);

		return accept;
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
		IconNotification iconNote = new IconNotification(icon);
		iconNote.setWindowTheme(m_pack.windowTheme);
		iconNote.setTextTheme(m_pack.textTheme);
		iconNote.setTitle(title);
		iconNote.setSubtitle(subtitle);

		return iconNote;
	}
}
