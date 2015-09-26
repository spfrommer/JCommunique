package com.demo;

import com.notification.NotificationFactory;
import com.notification.NotificationFactory.Location;
import com.notification.manager.QueueManager;
import com.notification.types.WindowNotification;
import com.platform.Platform;
import com.theme.ThemePackagePresets;
import com.utils.IconUtils;
import com.utils.Time;

public class QueueManagerDemo {
	public static void main(String[] args) throws Exception {
		Platform.instance().setAdjustForPlatform(true);

		NotificationFactory factory = new NotificationFactory(ThemePackagePresets.cleanLight());
		QueueManager manager = new QueueManager(Location.NORTHWEST);
		// sets how quickly old notifications should move out of the way for new ones
		manager.setSnapFactor(0.23);

		for (int i = 0; i < 10; i++) {
			int type = i % 3;
			WindowNotification note = null;
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
			// when the notification is clicked, it should hide itself
			note.setCloseOnClick(true);
			// make it show in the queue for five seconds
			manager.addNotification(note, Time.seconds(10));
			// one second delay between creations
			Thread.sleep(1000);
		}
	}
}