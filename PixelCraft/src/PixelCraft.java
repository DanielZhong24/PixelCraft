import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class PixelCraft {
    static String getOutputFilename(String inputFileName, String converterName) {
        int dotIndex = inputFileName.lastIndexOf(".");
        return inputFileName.substring(0, dotIndex) + "_" + converterName + inputFileName.substring(dotIndex);
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java -cp \"path/to/classes\" PixelCraft <ConverterName/Grayscale/Rotate/Blur/etc> <image_file.png>");
            System.exit(1);
        }
        String converterName = args[0];
        String inputFileName = args[1];
        String outputFileName = getOutputFilename(inputFileName, converterName);
        try {
            Class<?> clazz = Class.forName(converterName);
            Converter converter = (Converter) clazz.getDeclaredConstructor().newInstance();
            converter.convert(inputFileName, outputFileName);
        } catch (IOException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
