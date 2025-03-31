import java.awt.image.BufferedImage;
import java.io.IOException;

public class Grayscale extends Converter {

    @Override
    public void convert(String inputFileName, String outputFileName) throws IOException {
        BufferedImage image = readImage(inputFileName);
        // Get the dimensions of the image
        int width = image.getWidth();
        int height = image.getHeight();
        
        // Create a new image for the grayscale result
        BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        // Loop through each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ARGB argb = new ARGB(image.getRGB(x, y));          // Extract ARGB color values
                int gray = (argb.red + argb.green + argb.blue) / 3; // Calculate the average for grayscale
                grayscaleImage.setRGB(x, y, new ARGB(argb.alpha, gray, gray, gray).toInt());  // Set grayscale pixel
            }
        }

        writeImage(grayscaleImage, outputFileName);
    }
}
