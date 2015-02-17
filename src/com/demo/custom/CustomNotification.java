package com.demo.custom;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import com.notification.Notification;
import com.notification.NotificationBuilder;
import com.notification.types.BorderLayoutNotification;
import com.theme.TextTheme;
import com.theme.ThemePackage;

/**
 * This is a CustomNotification which will only have one line of text.
 */
public class CustomNotification extends BorderLayoutNotification {
	private JLabel m_label;

	public CustomNotification() {
		m_label = new JLabel();
		this.addComponent(m_label, BorderLayout.CENTER);
	}

	/**
	 * This will set the text font to that of the title font.
	 * 
	 * @param theme
	 */
	public void setTextTeme(TextTheme theme) {
		m_label.setFont(theme.title);
	}

	public String getText() {
		return m_label.getText();
	}

	public void setText(String text) {
		m_label.setText(text);
	}

	public static class CustomBuilder implements NotificationBuilder {
		@Override
		public Notification buildNotification(ThemePackage pack, Object[] args) {
			CustomNotification notification = new CustomNotification();
			// handled by WindowNotification
			notification.setWindowTheme(pack.windowTheme);
			// handled by us
			notification.setTextTeme(pack.textTheme);
			if (args.length > 0) {
				notification.setText((String) args[0]);
			} else {
				notification.setText("No text supplied");
			}
			return notification;
		}
	}
}
