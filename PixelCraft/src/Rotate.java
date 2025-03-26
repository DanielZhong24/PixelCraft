import java.awt.image.BufferedImage;
import java.io.IOException;
public class Rotate extends Converter {
	@Override
    public void convert(String inputFileName, String outputFileName) throws IOException{
		BufferedImage original = readImage(inputFileName);
		int width = original.getWidth();
		int height = original.getHeight();
		
		BufferedImage rotated = new BufferedImage(height,width,BufferedImage.TYPE_INT_ARGB);
		
		for(int y =0; y<height;y++) {
			for(int x =0; x<width;x++) {
				int pixel = original.getRGB(x, y);
				rotated.setRGB(height - y - 1, x, pixel);
			}
		}
		
		writeImage(rotated,outputFileName);
	}
}
