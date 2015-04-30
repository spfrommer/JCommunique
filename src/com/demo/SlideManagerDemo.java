package com.demo;

import com.manager.SlideManager;
import com.notification.NotificationFactory;
import com.notification.NotificationFactory.Location;
import com.theme.ThemePackagePresets;
import com.utils.Time;

public class SlideManagerDemo {
	public static void main(String[] args) throws Exception {
		NotificationFactory factory = new NotificationFactory(ThemePackagePresets.cleanLight());
		SlideManager manager = new SlideManager(Location.SOUTH);

		manager.addNotification(factory.buildTextNotification("Title", "subtitle"), Time.infinite());
	}
}