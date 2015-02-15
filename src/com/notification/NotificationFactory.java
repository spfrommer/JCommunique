package com.notification;

import java.util.HashMap;

import javax.swing.ImageIcon;

import com.notification.types.IconNotification;
import com.notification.types.SimpleTextNotification;
import com.theme.ThemePackage;
import com.theme.ThemePackagePresets;

/**
 * Creates Notifications using a ThemePackage.
 */
public class NotificationFactory {
	private ThemePackage m_pack;
	private HashMap<String, NotificationBuilder> m_builders = new HashMap<String, NotificationBuilder>();

	public enum PopupLocation {
		NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST
	}

	public NotificationFactory() {
		setThemePackage(ThemePackagePresets.cleanLight());
	}

	public NotificationFactory(ThemePackage pack) {
		setThemePackage(pack);
	}

	/**
	 * Sets the themes that the factory should use when creating notifications.
	 * See ThemePackagePresets for some default themes.
	 * 
	 * @param pack
	 */
	public void setThemePackage(ThemePackage pack) {
		m_pack = pack;
	}

	/**
	 * Adds a custom NotificationBuilder. Notifications can then be built using
	 * build(String name).
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
	 * Builds a Notification using the NotificationBuilder associated with the
	 * name.
	 * 
	 * @param name
	 * @return
	 */
	public Notification build(String name) {
		if (!m_builders.containsKey(name))
			throw new RuntimeException("No NotificationBuilder for: " + name);
		Notification note = m_builders.get(name).buildNotification(m_pack);
		return note;
	}

	/**
	 * Builds a SimpleTextNotification.
	 * 
	 * @param title
	 * @param subtitle
	 * @return
	 */
	public SimpleTextNotification buildTextNotification(String title,
			String subtitle) {
		SimpleTextNotification text = new SimpleTextNotification();
		text.setWindowTheme(m_pack.windowTheme);
		text.setTextTheme(m_pack.textTheme);
		text.setTitle(title);
		text.setSubtitle(subtitle);

		return text;
	}

	/**
	 * Builds an IconNotification.
	 * 
	 * @param title
	 * @param subtitle
	 * @param icon
	 * @return
	 */
	public IconNotification buildIconNotification(String title,
			String subtitle, ImageIcon icon) {
		IconNotification iconNote = new IconNotification(icon);
		iconNote.setWindowTheme(m_pack.windowTheme);
		iconNote.setTextTheme(m_pack.textTheme);
		iconNote.setTitle(title);
		iconNote.setSubtitle(subtitle);

		return iconNote;
	}
}
