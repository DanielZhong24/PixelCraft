import java.awt.image.BufferedImage;
import java.io.IOException;

public class Dither extends Converter {

    // Define the color palette with 8 colors (black, red, green, blue, yellow, magenta, cyan, white)
    private static final int[] PALETTE = {
        0x000000,  // Black
        0xFF0000,  // Red
        0x00FF00,  // Green
        0x0000FF,  // Blue
        0xFFFF00,  // Yellow
        0xFF00FF,  // Magenta
        0x00FFFF,  // Cyan
        0xFFFFFF   // White
    };

    @Override
    public void convert(String inputFileName, String outputFileName) throws IOException {

        // Read the input image and create an output image with the same dimensions
        BufferedImage image = readImage(inputFileName);
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage dithered = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Separate color channels into 2D arrays
        int[][] red = new int[width][height];
        int[][] green = new int[width][height];
        int[][] blue = new int[width][height];

        // Extract red, green, and blue channels
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ARGB argb = new ARGB(image.getRGB(x, y));
                red[x][y] = argb.red;
                green[x][y] = argb.green;
                blue[x][y] = argb.blue;
            }
        }

        // Apply Floyd-Steinberg dithering
        for (int y = 0; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {

                // Map current pixel to the closest palette color
                int oldRed = red[x][y];
                int newRed = findClosestPaletteColor(oldRed, 16);
                int redError = oldRed - newRed;

                int oldGreen = green[x][y];
                int newGreen = findClosestPaletteColor(oldGreen, 8); 
                int greenError = oldGreen - newGreen;

                int oldBlue = blue[x][y];
                int newBlue = findClosestPaletteColor(oldBlue, 0);
                int blueError = oldBlue - newBlue;

                // Set the dithered pixel
                dithered.setRGB(x, y, new ARGB(255, newRed, newGreen, newBlue).toInt());

                // Distribute the quantization error to neighboring pixels
                red[x + 1][y]     = clamp(red[x + 1][y]     + (redError * 15 / 16));
                red[x - 1][y + 1] = clamp(red[x - 1][y + 1] + (redError * 8 / 16));
                red[x][y + 1]     = clamp(red[x][y + 1]     + (redError * 6 / 16));
                red[x + 1][y + 1] = clamp(red[x + 1][y + 1] + (redError * 4 / 16));

                green[x + 1][y]     = clamp(green[x + 1][y]     + (greenError * 10 / 16));
                green[x - 1][y + 1] = clamp(green[x - 1][y + 1] + (greenError * 4 / 16));
                green[x][y + 1]     = clamp(green[x][y + 1]     + (greenError * 6 / 16));
                green[x + 1][y + 1] = clamp(green[x + 1][y + 1] + (greenError * 2 / 16));

                blue[x + 1][y]     = clamp(blue[x + 1][y]     + (blueError * 10 / 16));
                blue[x - 1][y + 1] = clamp(blue[x - 1][y + 1] + (blueError * 4 / 16));
                blue[x][y + 1]     = clamp(blue[x][y + 1]     + (blueError * 6 / 16));
                blue[x + 1][y + 1] = clamp(blue[x + 1][y + 1] + (blueError * 2 / 16));
            }
        }

        // Save the dithered image
        writeImage(dithered, outputFileName);
    }

    // Find the closest color in the palette for the given channel value
    private int findClosestPaletteColor(int channelValue, int shift) {
        int closest = 0;
        int minDiff = Integer.MAX_VALUE;

        for (int color : PALETTE) {
            int paletteChannelValue = (color >> shift) & 0xFF;
            int diff = Math.abs(channelValue - paletteChannelValue);

            if (diff < minDiff) {
                minDiff = diff;
                closest = paletteChannelValue;
            }
        }
        return closest;
    }

    // Clamp color channel values to stay within the valid range (0-255)
    private int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}
