package com.notification;

import com.notification.NotificationFactory.PopupLocation;

public class Test {
	public static void main(String[] args) {
		NotificationFactory factory = new NotificationFactory();
		factory.setPopupLocation(PopupLocation.NORTH);
		SimpleTextNotification note = factory.buildNotification("Test", "Subtest");
		note.show();
	}
}
