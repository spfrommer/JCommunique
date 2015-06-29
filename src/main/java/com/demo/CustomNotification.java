package com.demo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import com.notification.NotificationBuilder;
import com.notification.types.BorderLayoutNotification;
import com.theme.TextTheme;
import com.theme.ThemePackage;
import com.theme.WindowTheme;

/**
 * This is a CustomNotification which will only have one line of text and a button that will turn into a progress bar.
 */
public class CustomNotification extends BorderLayoutNotification {
	private JLabel m_label;
	private JButton m_button;
	private JProgressBar m_progress;

	private TextTheme m_theme;

	public CustomNotification() {
		m_label = new JLabel();
		m_button = new JButton("Click me!");
		m_progress = new JProgressBar();

		m_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeComponent(m_button);
				addComponent(m_progress, BorderLayout.SOUTH);
				final Timer timer = new Timer(100, null);

				timer.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						m_progress.setValue(m_progress.getValue() + 1);
						m_progress.repaint();

						if (m_progress.getValue() == 100) {
							timer.stop();
							hide();
						}
					}
				});

				timer.start();
			}
		});

		this.addComponent(m_label, BorderLayout.CENTER);
		this.addComponent(m_button, BorderLayout.SOUTH);
	}

	/**
	 * This will set the title and subtitle font and color.
	 *
	 * @param theme
	 *            the TextTheme to set
	 */
	public void setTextTheme(TextTheme theme) {
		m_label.setFont(theme.title);
		m_label.setForeground(theme.titleColor);
		m_button.setFont(theme.subtitle);
		m_button.setForeground(theme.subtitleColor);

		m_theme = theme;
	}

	public String getText() {
		return m_label.getText();
	}

	public void setText(String text) {
		m_label.setText(text);
	}

	@Override
	public void setWindowTheme(WindowTheme theme) {
		super.setWindowTheme(theme);

		if (m_theme != null) {
			// the WindowNotification is going to automatically give all our labels with the set foreground color, but
			// we want to change this to the title color of the font
			m_label.setForeground(m_theme.titleColor);
			m_button.setForeground(m_theme.subtitleColor);
		}
	}

	public static class CustomBuilder implements NotificationBuilder<CustomNotification> {
		@Override
		public CustomNotification buildNotification(ThemePackage pack, Object... args) {
			CustomNotification note = new CustomNotification();
			// handled by the WindowNotification first, then we override the values we want to keep
			note.setWindowTheme(pack.getTheme(WindowTheme.class));
			// handled by us
			note.setTextTheme(pack.getTheme(TextTheme.class));

			if (args.length > 0) {
				note.setText((String) args[0]);
			} else {
				note.setText("No text supplied");
			}
			return note;
		}
	}
}
