import java.awt.image.BufferedImage;
import java.io.IOException;

public class Pixelate extends Converter {

	@Override
	
	public void convert(String inputFileName, String outputFileName) throws IOException {
		BufferedImage image = readImage(inputFileName);
		
		int width = image.getWidth();
		int height = image.getHeight();
		int size = 5;
	
		
		BufferedImage pixelated = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		for (int y = 0; y < height; y+= size) {
			for (int x = 0; x < width; x+= size) {
				int pixel = image.getRGB(x, y);
				
				for (int surroundingY = y; surroundingY < height; surroundingY++) {
					for (int surroundingX = x; surroundingX < width; surroundingX++) {
						pixelated.setRGB(surroundingX, surroundingY, pixel);
					}
				}
			
			}
		}
		writeImage(pixelated, outputFileName);
	}
}
