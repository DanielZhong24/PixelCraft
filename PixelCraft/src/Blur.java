import java.awt.image.BufferedImage;
import java.io.IOException;

public class Blur extends Converter {

    // Define the size of the kernel (blur intensity)
    private static final int KERNEL_SIZE = 30; 
    
    @Override
    public void convert(String inputFileName, String outputFileName) throws IOException {

        // Read the input image and create an output image with the same dimensions
        BufferedImage original = readImage(inputFileName);
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage blurred = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Calculate the radius for the blur kernel
        int radius = KERNEL_SIZE / 2;

        // Iterate over each pixel in the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                // Initialize accumulators for color channels
                int r = 0;
                int g = 0; 
                int b = 0;
                int count = 0;

                // Apply the blur kernel
                for (int ky = -radius; ky <= radius; ky++) {
                    for (int kx = -radius; kx <= radius; kx++) {

                        // Clamp the pixel coordinates to stay within the image boundaries
                        int px = Math.min(Math.max(x + kx, 0), width - 1);  
                        int py = Math.min(Math.max(y + ky, 0), height - 1);
                        
                        // Extract the pixel color channels
                        int pixel = original.getRGB(px, py);
                        r += (pixel >> 16) & 0xFF;   // Red channel
                        g += (pixel >> 8) & 0xFF;    // Green channel
                        b += pixel & 0xFF;           // Blue channel
                        count++;
                    }
                }
                
                // Calculate the average color for the blurred pixel
                int avgPixel = 0xFF000000 | ((r / count) << 16) | ((g / count) << 8) | (b / count);
                blurred.setRGB(x, y, avgPixel);
            }
        }
        
        // Save the blurred image
        writeImage(blurred, outputFileName);
    }
}
