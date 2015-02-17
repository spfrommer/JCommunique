package com.demo.custom;

import com.manager.NotificationManager;
import com.manager.SimpleManager;
import com.notification.NotificationFactory;
import com.theme.ThemePackagePresets;
import com.utils.Time;

public class CustomNotificationDemo {
	public static void main(String[] args) {
		// register the custom builder with the factory
		NotificationFactory factory = new NotificationFactory(ThemePackagePresets.cleanLight());
		factory.addBuilder("custom", new CustomNotification.CustomBuilder());

		// add the Notification
		NotificationManager manager = new SimpleManager();
		manager.addNotification(factory.build("custom", "this is test text"), Time.seconds(5));
	}
}
