import java.awt.image.BufferedImage;
import java.io.IOException;

public class Invert extends Converter {
	@Override
	
	public void convert(String inputFileName, String outputFileName) throws IOException {
		BufferedImage image = readImage(inputFileName);
		int width = image.getWidth();
		int height = image.getHeight();
		
		BufferedImage inverted = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		invert(image, inverted, 0, 0, width, height);
		
		writeImage(inverted, outputFileName);
	}
	
	public void invert(BufferedImage image, BufferedImage inverted, int x, int y, int width, int height) {
		
		if (y >= height) {
			return;
		}
		
		if (x >= width) {
			invert(image, inverted, 0, y+1, width, height);
			return;
		}

		ARGB argb = new ARGB(image.getRGB(x,y));
		int newRed = 255 - argb.red;
		int newGreen = 255 - argb.green;
		int newBlue = 255 - argb.blue;
		
		inverted.setRGB(x, y, new ARGB(argb.alpha, newRed, newGreen, newBlue).toInt());
			
	
		
		invert(image, inverted, x+1, y, width, height);
		
	}
}
