package com.notification;

/**
 * Creates Notifications using a ThemePackage.
 */
public class NotificationFactory {
	private ThemePackage m_pack;
	private PopupLocation m_loc;

	private Screen m_screen;

	public enum PopupLocation {
		NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST
	}

	public NotificationFactory() {
		setThemePackage(ThemePackagePresets.cleanLight());
		setPopupLocation(PopupLocation.NORTHEAST);

		m_screen = new Screen(true);
	}

	public NotificationFactory(ThemePackage pack) {
		setThemePackage(pack);
		setPopupLocation(PopupLocation.NORTHEAST);

		m_screen = new Screen(true);
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
	 * Sets the location that the Notifications should show in.
	 * 
	 * @param loc
	 */
	public void setPopupLocation(PopupLocation loc) {
		m_loc = loc;
	}

	/**
	 * Builds a SimpleTextNotification that must be shown with show().
	 * 
	 * @param title
	 * @param subtitle
	 * @return
	 */
	public SimpleTextNotification buildNotification(String title, String subtitle) {
		SimpleTextNotification text = new SimpleTextNotification();
		text.setWindowTheme(m_pack.windowTheme);
		text.setTextTheme(m_pack.textTheme);
		text.setTitle(title);
		text.setSubtitle(subtitle);

		text.setLocation(m_screen.getX(m_loc), m_screen.getY(m_loc));

		return text;
	}
}
