package com;

import com.notification.NotificationFactory;
import com.notification.NotificationFactory.PopupLocation;
import com.notification.NotificationManager;
import com.notification.SimpleManager;
import com.notification.SimpleTextNotification;
import com.theme.ThemePackagePresets;

public class Test {
	public static void main(String[] args) throws Exception {
		NotificationFactory factory = new NotificationFactory(ThemePackagePresets.cleanDark());
		NotificationManager manager = new SimpleManager(PopupLocation.NORTH);

		SimpleTextNotification note = factory.buildTextNotification("Test", "Subtest");

		note.setCloseOnClick(true);

		manager.addNotification(note);
	}
}
