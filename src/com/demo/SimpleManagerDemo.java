package com.demo;

import com.manager.NotificationManager;
import com.manager.SimpleManager;
import com.notification.NotificationFactory;
import com.notification.NotificationFactory.Location;
import com.notification.types.AcceptNotification;
import com.notification.types.IconNotification;
import com.notification.types.TextNotification;
import com.platform.Platform;
import com.theme.ThemePackagePresets;
import com.utils.IconUtils;
import com.utils.Time;

/**
 * This is a simple demo which uses the SimpleManager to show two different types of Notifications.
 */
public class SimpleManagerDemo {
	public static void main(String[] args) throws InterruptedException {
		// this will make the Notifications match the limits of the platform
		// this will mean no fading on unix machines (since it doesn't look too good)
		Platform.instance().setAdjustForPlatform(true);

		// makes a factory with the built-in clean theme
		NotificationFactory factory = new NotificationFactory(ThemePackagePresets.cleanDark());
		// a normal manager that just pops up the notification
		NotificationManager plain = new SimpleManager(Location.NORTHWEST);
		// a fade manager that will make the window fade in and out over a two second period
		SimpleManager fade = new SimpleManager(Location.SOUTHWEST);
		fade.setFadeEnabled(true);
		fade.setFadeTime(Time.seconds(1));

		// adds a text notification to the first manager
		TextNotification notification = factory.buildTextNotification("This is the dark theme", "Managed by a SimpleManager");
		notification.setCloseOnClick(true);
		// the notification will stay there forever until you click it to exit
		plain.addNotification(notification, Time.infinite());

		Thread.sleep(2000);
		// adds an icon notification that should fade in - note that results may vary depending on the platform
		IconNotification icon = factory.buildIconNotification("This is a really really really long title", "See the cutoff?",
				IconUtils.createIcon("/com/demo/exclamation.png", 50, 50));
		new SimpleManager(Location.WEST).addNotification(icon, Time.seconds(2));

		Thread.sleep(2000);
		AcceptNotification accept = factory.buildAcceptNotification("Do you accept?", "This is a test.");
		fade.addNotification(accept, Time.seconds(4));

		boolean didAccept = accept.blockUntilReply();
		TextNotification reply = null;
		if (didAccept) {
			reply = factory.buildTextNotification("You accepted", "");
		} else {
			reply = factory.buildTextNotification("You did not accept", "");
		}
		reply.setCloseOnClick(true);
		fade.addNotification(reply, Time.infinite());
	}
}
