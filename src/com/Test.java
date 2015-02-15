package com;

import com.notification.SimpleManager;
import com.notification.IconUtils;
import com.notification.NotificationFactory;
import com.notification.NotificationFactory.PopupLocation;
import com.notification.types.IconNotification;
import com.notification.NotificationManager;
import com.notification.Time;
import com.theme.ThemePackagePresets;

public class Test {
	public static void main(String[] args) throws Exception {
		NotificationFactory factory = new NotificationFactory(ThemePackagePresets.cleanLight());
		NotificationManager manager = new SimpleManager(PopupLocation.NORTH, Time.seconds(2));

		// SimpleTextNotification note = factory.buildTextNotification("Test", "Subtest");
		// note.setCloseOnClick(true);
		IconNotification note = factory.buildIconNotification("Test", "Subtest", IconUtils.createIcon("exclamation.png", 50, 50));
		manager.addNotification(note, Time.seconds(3));
	}
}
