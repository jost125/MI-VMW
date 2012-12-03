package flickr.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class SimilarityRanker {
	public double getRank(File image, Color color) throws CannotGetRankException {
		double result = 0.0;
		try {
			List<Color> pixels = getPixels(image);

			double sum = 0.0;
			for (Color pixel : pixels) {
				sum += Math.sqrt(Math.pow(color.getRed() - pixel.getRed(), 2) + Math.pow(color.getGreen() - pixel.getGreen(), 2) + Math.pow(color.getBlue() - pixel.getBlue(), 2));
			}

			result = sum / pixels.size();
		} catch (IOException ex) {
			throw new CannotGetRankException(ex);
		}

		return result;
	}

	private List<Color> getPixels(File image) throws IOException {
		BufferedImage img = ImageIO.read(image);
		final int width = img.getWidth();
		final int height = img.getHeight();

		List<Color> pixels = new ArrayList<Color>(width * height);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pixels.add(getRGBColor(img.getRGB(x, y)));
			}
		}

		return pixels;
	}

	private Color getRGBColor(int color) {
		short red = (short)((color >>> 16) & 0xFF);
		short green = (short)((color >>> 8) & 0xFF);
		short blue = (short)(color & 0xFF);

		return new Color(red, green, blue);
	}
}
