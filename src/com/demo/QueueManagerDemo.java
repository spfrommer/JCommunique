package com.demo;

import com.manager.QueueManager;
import com.notification.Notification;
import com.notification.NotificationFactory;
import com.notification.NotificationFactory.Location;
import com.platform.Platform;
import com.theme.ThemePackagePresets;
import com.utils.IconUtils;
import com.utils.Time;

public class QueueManagerDemo {
	public static void main(String[] args) throws Exception {
		Platform.instance().setAdjustForPlatform(true);

		NotificationFactory factory = new NotificationFactory(ThemePackagePresets.cleanLight());
		QueueManager manager = new QueueManager(Location.NORTHWEST);

		for (int i = 0; i < 10; i++) {
			int type = i % 3;
			Notification note = null;
			switch (type) {
			case 0:
				note = factory.buildIconNotification("IconNotification", "Subtitle",
						IconUtils.createIcon("/com/demo/exclamation.png", 50, 50));
				break;
			case 1:
				note = factory.buildTextNotification("TextNotification", "Subtitle");
				break;
			case 2:
				note = factory.buildAcceptNotification("AcceptNotification", "Do you accept?");
				break;
			}
			// note.setCloseOnClick(true);
			// make it show in the queue for five seconds
			manager.addNotification(note, Time.seconds(10));
			// one second delay between creations
			Thread.sleep(1000);
		}
	}
}