package com.demo;

import com.manager.SlideManager;
import com.manager.SlideManager.SlideDirection;
import com.notification.NotificationFactory;
import com.notification.NotificationFactory.Location;
import com.theme.ThemePackagePresets;
import com.utils.Time;

public class SlideManagerDemo {
	public static void main(String[] args) throws Exception {
		// makes a factory with the clean theme
		NotificationFactory factory = new NotificationFactory(ThemePackagePresets.cleanLight());
		// adds a new manager which displays Notifications at the bottom right of the screen
		SlideManager manager = new SlideManager(Location.SOUTHEAST);

		// adds a notification that appears southeast and slides in the default direction, north
		manager.addNotification(factory.buildTextNotification("This is sliding north", "Appeared southeast"), Time.infinite());

		manager.setSlideDirection(SlideDirection.WEST);
		// adds a notification that appears southeast and slides west
		manager.addNotification(factory.buildTextNotification("This is sliding west", "Appeared southeast"), Time.infinite());
		// these two notifications should finish in the same end position

		Thread.sleep(1000);
		manager.setLocation(Location.NORTHEAST);
		manager.setSlideDirection(SlideDirection.SOUTH);
		manager.addNotification(factory.buildTextNotification("This is sliding south", "Appeared northeast"), Time.infinite());
	}
}