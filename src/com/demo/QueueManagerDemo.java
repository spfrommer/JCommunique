package com.demo;

import com.manager.QueueManager;
import com.notification.NotificationFactory;
import com.notification.NotificationFactory.Location;
import com.notification.types.IconNotification;
import com.platform.Platform;
import com.theme.ThemePackagePresets;
import com.utils.IconUtils;
import com.utils.Time;

public class QueueManagerDemo {
	public static void main(String[] args) throws Exception {
		Platform.instance().setAdjustForPlatform(true);

		NotificationFactory factory = new NotificationFactory(ThemePackagePresets.cleanLight());
		QueueManager manager = new QueueManager(Location.NORTHWEST);

		Thread.sleep(2000);

		for (int i = 0; i < 5; i++) {
			IconNotification note = factory.buildIconNotification("Test", "Subtest",
					IconUtils.createIcon("/com/demo/exclamation.png", 50, 50));
			note.setCloseOnClick(true);
			// make it show in the queue for five seconds
			manager.addNotification(note, Time.seconds(5));
			// one second delay between creations
			Thread.sleep(1000);
		}
	}
}