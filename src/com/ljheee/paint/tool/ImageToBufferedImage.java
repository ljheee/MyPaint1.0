package com.ljheee.paint.tool;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class ImageToBufferedImage {

	/**
	 * Image×ª»¯³ÉBufferedImage
	 * 
	 * @param image
	 * @return
	 */
	public static BufferedImage toBufferedImage(Image image) {

		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		image = new ImageIcon(image).getImage();

		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			// Determine the type of transparency of the new buffered image
			int transparency = Transparency.OPAQUE;

			// Create the buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// The system does not have a screen
			e.printStackTrace();
		}

		if (bimage == null) {
			// Create a buffered image using the default color model
			int type = BufferedImage.TYPE_INT_RGB;
			// int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
			/*
			 * if (hasAlpha) { type = BufferedImage.TYPE_INT_ARGB; }
			 */
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		}

		// Copy image to buffered image
		Graphics g = bimage.createGraphics();

		// Paint the image onto the buffered image
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}
}
