import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

public class Blur extends Converter {
    @Override
    public void convert(String inputFileName, String outputFileName) throws IOException {
        BufferedImage image = readImage(inputFileName);
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage blurredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int avgRed = 0;
                int avgGreen = 0;
                int avgBlue = 0;
                
                for (int verticalPixel = 0; verticalPixel < 2; verticalPixel++) {
                    for (int horizontalPixel = 0; horizontalPixel < 2; horizontalPixel++) {
                        int newY = y + verticalPixel;
                        int newX = x + horizontalPixel;
                        if (newX < width && newY < height) {
    	                    int surroundingRGB = image.getRGB(newX, newY);
    	                    
    	                    ARGB argb = new ARGB(surroundingRGB);
    	                    avgRed += (surroundingRGB >> 16) * 0xFF;
    	                    avgGreen += (surroundingRGB >> 8) & 0xFF;
    	                    avgBlue += argb.blue & 0xFF;
                        }
                       
                        
	                 
                        }
                       
                    }

                
                blurredImage.setRGB(x, y, new ARGB(255, avgRed, avgGreen, avgBlue).toInt());

                }
            }
        writeImage(blurredImage, outputFileName);
        }
}