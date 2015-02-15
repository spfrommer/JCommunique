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
public class IconNotification extends ComponentNotification {
	private TextTheme m_theme;

	private String m_title;
	private String m_subtitle;

	private JLabel m_titleLabel;
	private JLabel m_subtitleLabel;

	private JLabel m_iconLabel;

	public static final int ICON_PADDING = 10;

	public IconNotification(ImageIcon icon) {
		m_iconLabel = new JLabel();
		m_iconLabel.setIcon(icon);
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
	}

	public String getTitle() {
		return m_title;
	}

	public void setTitle(String title) {
		m_titleLabel.setText(title);
		m_title = title;
	}

	public String getSubtitle() {
		return m_subtitle;
	}

	public void setSubtitle(String subtitle) {
		m_subtitleLabel.setText(subtitle);
		m_subtitle = subtitle;
	}

	@Override
	public void themeSet(WindowTheme theme) {
		super.themeSet(theme);
		m_titleLabel.setBackground(theme.background);
		m_subtitleLabel.setBackground(theme.background);

		m_titleLabel.setForeground(theme.foreground);
		m_subtitleLabel.setForeground(theme.foreground);
	}
}
