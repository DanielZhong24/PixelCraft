import java.awt.image.BufferedImage;
import java.io.IOException;
public class Rotate extends Converter {
	@Override
    public void convert(String inputFileName, String outputFileName) throws IOException{
		BufferedImage original = readImage(inputFileName);
	    	// Get the dimensions of the original image

		int width = original.getWidth();
		int height = original.getHeight();
		// Create a new image with swapped dimensions for the rotated result

		BufferedImage rotated = new BufferedImage(height,width,BufferedImage.TYPE_INT_ARGB);
		// Rotate the image 90 degrees clockwise
		for(int y =0; y<height;y++) {
			for(int x =0; x<width;x++) {
				// Get the pixel color at (x, y)
				int pixel = original.getRGB(x, y);
				 // Set it at the rotated position
				rotated.setRGB(height - y - 1, x, pixel);
			}
		}
		
		writeImage(rotated,outputFileName);
	}
}
