import java.awt.image.BufferedImage;
import java.io.IOException;

public class Red extends Converter {
    /**
     * Convert the color to red shade by decreasing green and blue shades.
     */
    @Override
    public void convert(String inputFileName, String outputFileName) throws IOException {
        BufferedImage image = readImage(inputFileName);
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage redShadeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ARGB argb = new ARGB(image.getRGB(x, y));
                int newRed = argb.red;
                int newGreen = (int)(argb.green *0.25);
                int newBlue = (int)(argb.blue *0.25);

                redShadeImage.setRGB(x, y, new ARGB(argb.alpha, newRed, newGreen, newBlue).toInt());
            }
        }

        writeImage(redShadeImage, outputFileName);
    }
}