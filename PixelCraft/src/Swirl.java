import java.awt.image.BufferedImage;
import java.io.IOException;

public class Swirl extends Converter {
	
	@Override
	public void convert(String inputFileName, String outputFileName) throws IOException {
		BufferedImage image = readImage(inputFileName);
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		BufferedImage mirrored = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		mirror(image, mirrored, 0, 0, width, height);
		
		writeImage(mirrored, outputFileName);
	}
	
	public void mirror(BufferedImage image, BufferedImage mirrored, int x, int y, int width, int height) {
		
		// If y is greater than image height stop
		if (y >= height) {
			return;
		}
		
		// Moves to next row when x reaches image width
		if (x >= width) {
			mirror(image, mirrored, 0, y+1, width, height);
			return;
		}
		
		// Gets the pixel from the original image
		int pixel = image.getRGB(x,y);
		
		// Sets the mirrored pixel by swapping the rightmost pixel
		mirrored.setRGB(width - x - 1, y, pixel);
		
		// Moves to the next pixel in the column
		mirror(image, mirrored, x+1, y, width, height);
		
	}
}