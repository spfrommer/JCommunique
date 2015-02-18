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
		window.foreground = new Color(160, 205, 250);
		window.opacity = 0.8f;
		window.width = 300;
		window.height = 100;

		TextTheme text = new TextTheme();
		text.title = new Font("Arial", Font.BOLD, 25);
		text.subtitle = new Font("Arial", Font.PLAIN, 20);
		text.titleColor = new Color(10, 10, 10);
		text.subtitleColor = new Color(10, 10, 10);

		pack.windowTheme = window;
		pack.textTheme = text;

		return pack;
	}

	public static ThemePackage cleanDark() {
		ThemePackage pack = new ThemePackage();

		WindowTheme window = new WindowTheme();
		window.background = new Color(0, 0, 0);
		window.foreground = new Color(16, 124, 162);
		window.opacity = 0.8f;
		window.width = 300;
		window.height = 100;

		TextTheme text = new TextTheme();
		text.title = new Font("Arial", Font.BOLD, 25);
		text.subtitle = new Font("Arial", Font.PLAIN, 20);
		text.titleColor = new Color(200, 200, 200);
		text.subtitleColor = new Color(200, 200, 200);

		pack.windowTheme = window;
		pack.textTheme = text;

		return pack;
	}
}
