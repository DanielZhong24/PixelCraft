import java.awt.image.BufferedImage;
import java.io.IOException;

public class Crop extends Converter {
	@Override
	
	public void convert(String inputFileName, String outputFileName) throws IOException {
		BufferedImage image = readImage(inputFileName);
		
		int width = image.getWidth();
		int height = image.getHeight();
		int x = 300;
		int y = 400;
		
		int cropWidth = Math.min(x, width);
		int cropHeight = Math.min(y, height);
		
		BufferedImage cropped = new BufferedImage(cropWidth, cropHeight, BufferedImage.TYPE_INT_ARGB);
		
		crop(image, cropped, cropWidth, cropHeight);
		
		writeImage(cropped, outputFileName);
	}
	
	
	public void crop(BufferedImage image, BufferedImage cropped, int x, int y) {
		
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				ARGB argb = new ARGB(image.getRGB(j, i));
				cropped.setRGB(j, i, new ARGB(argb.alpha, argb.red, argb.green, argb.blue).toInt());
			}
		}
	}
}
