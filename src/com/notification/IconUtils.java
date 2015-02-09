package com.notification;

import java.awt.Image;

import javax.swing.ImageIcon;

public class IconUtils {
	private IconUtils() {

	}

	/**
	 * Creates an ImageIcon of the specified path with a given width and height.
	 * 
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 */
	public static ImageIcon createIcon(String path, int width, int height) {
		java.net.URL imgURL = IconUtils.class.getResource(path);
		if (imgURL != null) {
			ImageIcon icon = new ImageIcon(imgURL);
			icon = new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));

			return icon;
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}
