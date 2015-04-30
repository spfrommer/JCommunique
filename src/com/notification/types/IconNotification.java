package com.notification.types;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.theme.TextTheme;
import com.theme.WindowTheme;

/**
 * An IconNotification displays text, but with an icon.
 */
public class IconNotification extends BorderLayoutNotification {
	protected TextTheme m_theme;

	private String m_title;
	private String m_subtitle;

	private JLabel m_titleLabel;
	private JLabel m_subtitleLabel;

	private JLabel m_iconLabel;

	public static final int ICON_PADDING = 10;

	public IconNotification() {
		m_iconLabel = new JLabel();
		m_titleLabel = new JLabel();
		m_subtitleLabel = new JLabel();

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(m_titleLabel, BorderLayout.NORTH);
		panel.add(m_subtitleLabel, BorderLayout.CENTER);
		panel.setBorder(new EmptyBorder(0, ICON_PADDING, 0, 0));

		this.addComponent(m_iconLabel, BorderLayout.WEST);
		this.addComponent(panel, BorderLayout.CENTER);
	}

	/**
	 * Sets the icon to use.
	 * 
	 * @param icon
	 */
	public void setIcon(ImageIcon icon) {
		m_iconLabel.setIcon(icon);
	}

	/**
	 * @return the title String
	 */
	public String getTitle() {
		return m_title;
	}

	/**
	 * Sets the title String.
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		m_titleLabel.setText(title);
		m_title = title;
	}

	/**
	 * @return the subtitle String
	 */
	public String getSubtitle() {
		return m_subtitle;
	}

	/**
	 * Sets the subtitle String.
	 * 
	 * @param subtitle
	 */
	public void setSubtitle(String subtitle) {
		m_subtitleLabel.setText(subtitle);
		m_subtitle = subtitle;
	}

	protected TextTheme getTextTheme() {
		return m_theme;
	}

	/**
	 * @param theme
	 *            the two Fonts that should be used.
	 */
	public void setTextTheme(TextTheme theme) {
		m_theme = theme;
		m_titleLabel.setFont(theme.title);
		m_subtitleLabel.setFont(theme.subtitle);
		m_titleLabel.setForeground(theme.titleColor);
		m_subtitleLabel.setForeground(theme.subtitleColor);
	}

	@Override
	public void setWindowTheme(WindowTheme theme) {
		super.setWindowTheme(theme);

		if (m_theme != null) {
			m_titleLabel.setForeground(m_theme.titleColor);
			m_subtitleLabel.setForeground(m_theme.subtitleColor);
		}
	}
}