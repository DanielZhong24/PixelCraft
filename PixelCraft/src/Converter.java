import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Converter {
    /**
     * Reads an image from a file.
     */
    protected BufferedImage readImage(String inputFileName) throws IOException {
        File inputFile = new File(inputFileName);
        return ImageIO.read(inputFile);
    }

    /**
     * Writes an image to a file.
     */
    protected void writeImage(BufferedImage image, String outputFileName) throws IOException {
        File outputFile = new File(outputFileName);
        ImageIO.write(image, "PNG", outputFile);
    }

    /**
     * Abstract method to be implemented by subclasses.
     */
    public abstract void convert(String inputFileName, String outputFileName) throws IOException;
}