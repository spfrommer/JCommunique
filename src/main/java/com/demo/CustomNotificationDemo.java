package com.demo;

import com.notification.NotificationFactory;
import com.notification.NotificationManager;
import com.notification.NotificationFactory.Location;
import com.notification.manager.SimpleManager;
import com.platform.Platform;
import com.theme.ThemePackagePresets;
import com.utils.Time;

public class CustomNotificationDemo {
	public static void main(String[] args) throws Exception {
		Platform.instance().setAdjustForPlatform(true);
		// UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		// register the custom builder with the factory
		NotificationFactory factory = new NotificationFactory(ThemePackagePresets.cleanLight());
		factory.addBuilder(CustomNotification.class, new CustomNotification.CustomBuilder());

		// add the Notification
		NotificationManager manager = new SimpleManager(Location.NORTH);
		manager.addNotification(factory.build(CustomNotification.class, "this is test text"), Time.infinite());
	}
}
