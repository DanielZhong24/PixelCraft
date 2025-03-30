import java.awt.image.BufferedImage;
import java.io.IOException;

public class Crop extends Converter {
	@Override
	
	public void convert(String inputFileName, String outputFileName) throws IOException {
		BufferedImage image = readImage(inputFileName);
		
		// Gets image height and width;
		int width = image.getWidth();
		int height = image.getHeight();
		
		// Placeholder x and y values to be cropped from
		int x = 300;
		int y = 400;
		
		// Takes value of smaller width and height so image does not go out of bounds
		int cropWidth = Math.min(x, width);
		int cropHeight = Math.min(y, height);
		
		// Creates a new image called cropped
		BufferedImage cropped = new BufferedImage(cropWidth, cropHeight, BufferedImage.TYPE_INT_ARGB);
		
		// Calls the crop method that will crop the image
		crop(image, cropped, cropWidth, cropHeight);
		
		writeImage(cropped, outputFileName);
	}
	
	
	public void crop(BufferedImage image, BufferedImage cropped, int x, int y) {
		
		// Loops through the height and width
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				// Gets the ARGB value of the current pixel from original image
				ARGB argb = new ARGB(image.getRGB(j, i));
				// Sets the pixel at the same position on the new image
				cropped.setRGB(j, i, new ARGB(argb.alpha, argb.red, argb.green, argb.blue).toInt());
			}
		}
	}
}
