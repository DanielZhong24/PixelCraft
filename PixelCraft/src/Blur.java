import java.awt.image.BufferedImage;
import java.io.IOException;

public class Blur extends Converter {
    private static final int KERNEL_SIZE = 30; 
    
    @Override
    public void convert(String inputFileName, String outputFileName) throws IOException {
        BufferedImage original = readImage(inputFileName);
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage blurred = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int radius = KERNEL_SIZE / 2;
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                
                int r = 0;
                int g = 0; 
                int b = 0;
                int count = 0;
                
                for (int ky = -radius; ky <= radius; ky++) {
                    for (int kx = -radius; kx <= radius; kx++) {
                        int px = Math.min(Math.max(x + kx, 0), width - 1);  
                        int py = Math.min(Math.max(y + ky, 0), height - 1);
                        
                        int pixel = original.getRGB(px, py);
                        r += (pixel >> 16) & 0xFF;
                        g += (pixel >> 8) & 0xFF;
                        b += pixel & 0xFF;
                        count++;
                    }
                }
                
                int avgPixel = 0xFF000000 | ((r/count) << 16) | ((g/count) << 8) | (b/count);
                blurred.setRGB(x, y, avgPixel);
            }
        }
        
        writeImage(blurred, outputFileName);
    }
}