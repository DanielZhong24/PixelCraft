import java.awt.image.BufferedImage;
import java.io.IOException;

public class Invert extends Converter {
	@Override
	
	public void convert(String inputFileName, String outputFileName) throws IOException {
		BufferedImage image = readImage(inputFileName);
		
		// Gets image height and width
		int width = image.getWidth();
		int height = image.getHeight();
		
		// Creates a new image called Inverted
		BufferedImage inverted = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		// Calls the invert method with the original and new image, x and y values to be compared with
		invert(image, inverted, 0, 0, width, height);
		
		writeImage(inverted, outputFileName);
	}
	
	public void invert(BufferedImage image, BufferedImage inverted, int x, int y, int width, int height) {
		
		// If y is greater than image height, stop
		if (y >= height) {
			return;
		}
		
		// Moves to next column when x reaches image width
		if (x >= width) {
			invert(image, inverted, 0, y+1, width, height);
			return;
		}

		
		// Colour calculation for inverting
		ARGB argb = new ARGB(image.getRGB(x,y));
		int newRed = 255 - argb.red;
		int newGreen = 255 - argb.green;
		int newBlue = 255 - argb.blue;
		
		inverted.setRGB(x, y, new ARGB(argb.alpha, newRed, newGreen, newBlue).toInt());
			
	
		// Moves to the next pixel in the row
		invert(image, inverted, x+1, y, width, height);
		
	}
}
