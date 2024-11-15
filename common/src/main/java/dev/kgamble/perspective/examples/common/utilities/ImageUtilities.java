/*
 * Copyright 2024 Keith Gamble
 * [License information]
 */
package dev.kgamble.perspective.examples.common.utilities;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Utility class for image-related operations, particularly for loading and
 * resizing thumbnails.
 *
 * @author Keith Gamble
 */
public class ImageUtilities {

	/**
	 * Loads an image from a given resource path.
	 *
	 * @param resourcePath The path to the image resource.
	 * @return A BufferedImage of the loaded image, or null if loading fails.
	 */
	private static BufferedImage loadThumbnailFromFilePath(String resourcePath) {
		try {
			return ImageIO.read(ImageUtilities.class.getResourceAsStream(resourcePath));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Loads an image from a resource path and resizes it to the specified
	 * dimensions.
	 *
	 * @param resourcePath The path to the image resource.
	 * @param width        The desired width of the thumbnail.
	 * @param height       The desired height of the thumbnail.
	 * @return A BufferedImage of the resized thumbnail.
	 */
	public static BufferedImage loadThumbnailFromFilePath(String resourcePath, int width, int height) {
		BufferedImage originalImage = loadThumbnailFromFilePath(resourcePath);

		// Create a new BufferedImage with the desired size
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		// Get the Graphics2D object and set rendering hints for better quality
		Graphics2D g2d = resizedImage.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw the original image onto the new image, scaling it to fit
		g2d.drawImage(originalImage, 0, 0, width, height, null);
		g2d.dispose();

		return resizedImage;
	}
}