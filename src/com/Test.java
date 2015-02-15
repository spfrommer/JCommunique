package com;

import com.notification.IconUtils;
import com.notification.NotificationFactory;
import com.notification.NotificationFactory.PopupLocation;
import com.notification.NotificationManager;
import com.notification.QueueManager;
import com.notification.Time;
import com.notification.types.IconNotification;
import com.theme.ThemePackagePresets;

public class Test {
	public static void main(String[] args) throws Exception {
		NotificationFactory factory = new NotificationFactory(
				ThemePackagePresets.cleanLight());
		NotificationManager manager = new QueueManager(PopupLocation.NORTHWEST,
				Time.seconds(1));

		for (int i = 0; i < 2; i++) {
			IconNotification note = factory.buildIconNotification("Test",
					"Subtest", IconUtils.createIcon("exclamation.png", 50, 50));
			note.setCloseOnClick(true);
			manager.addNotification(note, Time.seconds(5));
			Thread.sleep(3000);
		}
	}
}
