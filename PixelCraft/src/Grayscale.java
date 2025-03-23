import java.awt.image.BufferedImage;
import java.io.IOException;

public class Grayscale extends Converter {
    /**
     * Convert the color of the images by finding the average of red, green and blue
     */
    @Override
    public void convert(String inputFileName, String outputFileName) throws IOException {
        BufferedImage image = readImage(inputFileName);
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ARGB argb = new ARGB(image.getRGB(x, y));
                int gray = (argb.red + argb.green + argb.blue) / 3;
                grayscaleImage.setRGB(x, y, new ARGB(argb.alpha, gray, gray, gray).toInt());
            }
        }

        writeImage(grayscaleImage, outputFileName);
    }
}