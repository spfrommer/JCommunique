package com.notification;

import java.awt.Color;
import java.awt.Font;

public class ThemePackagePresets {
	private ThemePackagePresets() {
	}

	public static ThemePackage cleanLight() {
		ThemePackage pack = new ThemePackage();

		WindowTheme window = new WindowTheme();
		window.foreground = new Color(10, 10, 10);
		window.background = new Color(255, 255, 255);
		window.opacity = 0.8f;

		TextTheme text = new TextTheme();
		text.title = new Font("Arial", Font.BOLD, 25);
		text.subtitle = new Font("Arial", Font.PLAIN, 20);

		pack.windowTheme = window;
		pack.textTheme = text;

		return pack;
	}
}
