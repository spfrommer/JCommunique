package com.demo.custom;

import com.manager.NotificationManager;
import com.manager.SimpleManager;
import com.notification.NotificationFactory;
import com.notification.NotificationFactory.Location;
import com.theme.ThemePackagePresets;
import com.utils.Time;

public class CustomNotificationDemo {
	public static void main(String[] args) throws Exception {
		// UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		// register the custom builder with the factory
		NotificationFactory factory = new NotificationFactory(ThemePackagePresets.cleanLight());
		factory.addBuilder("custom", new CustomNotification.CustomBuilder());

		// add the Notification
		NotificationManager manager = new SimpleManager(Location.NORTH);
		manager.addNotification(factory.build("custom", "this is test text"), Time.infinite());
	}
}
