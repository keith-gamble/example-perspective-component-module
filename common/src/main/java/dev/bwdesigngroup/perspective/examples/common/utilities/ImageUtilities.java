/*
 * Copyright 2022 Keith Gamble
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package dev.bwdesigngroup.perspective.examples.common.utilities;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.imageio.ImageIO;
import java.io.IOException;


/**
 *
 * @author Keith Gamble
 */
public class ImageUtilities {
	private static BufferedImage loadThumbnailFromFilePath(String resourcePath) {
        try {
            BufferedImage originalImage = ImageIO.read(ImageUtilities.class.getResourceAsStream(resourcePath));
            return originalImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

	public static BufferedImage loadThumbnailFromFilePath(String resourcePath, int width, int height) {
		final int THUMBNAIL_WIDTH = width;
		final int THUMBNAIL_HEIGHT = height;

		BufferedImage originalImage = loadThumbnailFromFilePath(resourcePath);
		
		// Create a new BufferedImage with the desired size
		BufferedImage resizedImage = new BufferedImage(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		// Get the Graphics2D object and set rendering hints for better quality
		Graphics2D g2d = resizedImage.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Draw the original image onto the new image, scaling it to fit
		g2d.drawImage(originalImage, 0, 0, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, null);
		g2d.dispose();
		
		return resizedImage;
    }
}
