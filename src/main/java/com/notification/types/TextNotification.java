package com.notification.types;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import com.theme.TextTheme;
import com.theme.WindowTheme;

/**
 * A text notification which will display a title and a subtitle.
 */
public class TextNotification extends BorderLayoutNotification {
	protected JLabel m_titleLabel;
	protected JLabel m_subtitleLabel;

	private TextTheme m_textTheme;

	public TextNotification() {
		m_titleLabel = new JLabel();
		m_subtitleLabel = new JLabel();

		this.addComponent(m_titleLabel, BorderLayout.NORTH);
		this.addComponent(m_subtitleLabel, BorderLayout.CENTER);
	}

	public String getTitle() {
		return m_titleLabel.getText();
	}

	public void setTitle(String title) {
		m_titleLabel.setText(title);
	}

	public String getSubtitle() {
		return m_subtitleLabel.getText();
	}

	public void setSubtitle(String subtitle) {
		m_subtitleLabel.setText(subtitle);
	}

	protected TextTheme getTextTheme() {
		return m_textTheme;
	}

	/**
	 * @param theme
	 *            the two Fonts that should be used.
	 */
	public void setTextTheme(TextTheme theme) {
		m_textTheme = theme;
		m_titleLabel.setFont(theme.title);
		m_subtitleLabel.setFont(theme.subtitle);
		m_titleLabel.setForeground(theme.titleColor);
		m_subtitleLabel.setForeground(theme.subtitleColor);
	}

	@Override
	public void setWindowTheme(WindowTheme theme) {
		super.setWindowTheme(theme);

		if (m_textTheme != null) {
			m_titleLabel.setForeground(m_textTheme.titleColor);
			m_subtitleLabel.setForeground(m_textTheme.subtitleColor);
		}
	}
}
