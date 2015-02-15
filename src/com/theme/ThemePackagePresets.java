package com.theme;

import java.awt.Color;
import java.awt.Font;

public class ThemePackagePresets {
	private ThemePackagePresets() {
	}

	public static ThemePackage cleanLight() {
		ThemePackage pack = new ThemePackage();

		WindowTheme window = new WindowTheme();
		window.background = new Color(255, 255, 255);
		window.foreground = new Color(10, 10, 10);
		window.opacity = 0.8f;

		TextTheme text = new TextTheme();
		text.title = new Font("Arial", Font.BOLD, 25);
		text.subtitle = new Font("Arial", Font.PLAIN, 20);

		pack.windowTheme = window;
		pack.textTheme = text;

		return pack;
	}

	public static ThemePackage cleanDark() {
		ThemePackage pack = new ThemePackage();

		WindowTheme window = new WindowTheme();
		window.background = new Color(0, 0, 50);
		// JLABEL DOES NOT LIKE FOREGROUND COLOR BEING PURE WHITE, FOR SOME
		// REASON. IT SETS IT TO BLACK. THIS WORKS,
		// HOWEVER.
		window.foreground = new Color(254, 254, 254);
		window.opacity = 0.8f;

		TextTheme text = new TextTheme();
		text.title = new Font("Arial", Font.BOLD, 25);
		text.subtitle = new Font("Arial", Font.PLAIN, 20);

		pack.windowTheme = window;
		pack.textTheme = text;

		return pack;
	}
}
