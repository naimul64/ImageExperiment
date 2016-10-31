import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * Created by insan on 10/31/16.
 */
public class ImageResizer {
    Logger logger = Logger.getLogger(this.getClass().getName().toString());

    void getImageFile() {
        BufferedImage bufferedImage = getReadImage();
        _2Ddimension ddimension = getImageDimension(bufferedImage);

        RGBA[][] rgbValueArray = getImageRgbValue(bufferedImage);

        exportImage(rgbValueArray, bufferedImage.getHeight(), bufferedImage.getWidth());

        System.out.println("\nH: " + ddimension.getHeight() + " W " + ddimension.getWidth());

    }

    private _2Ddimension getImageDimension(BufferedImage img) {
        _2Ddimension ddimension = new _2Ddimension();
        return ddimension.setHeight(img.getHeight()).setWidth(img.getWidth());
    }

    private BufferedImage getReadImage() {
        BufferedImage img = null;
        Path path = null;
        try {
            path = Paths.get(ImageResizer.class.getResource(".").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }

        try {
            img = ImageIO.read(new File(path.getParent().getParent().getParent() + "/Images/mountain.jpeg"));
            System.out.print("Successfully loaded image");
        } catch (IOException e) {
            System.out.print("Sorry failed to load image");
            return null;
        }

        return img;
    }

    private RGBA[][] getImageRgbValue(BufferedImage bufferedImage) {
        Integer height, width;
        _2Ddimension ddimension = getImageDimension(bufferedImage);
        height = ddimension.getHeight();
        width = ddimension.getWidth();
        RGBA[][] imageRgbValue = new RGBA[height][width];

        int h=0,w=0;

        try{
            for (h = 0; h < height; h++) {
                for (w = 0; w < width; w++) {
                    int pixel = bufferedImage.getRGB(w, h);
                    RGBA rgba = getPixelRgba(pixel);
                    imageRgbValue[h][w] = rgba;
                }
            }
        } catch (Exception e){
            System.out.println("I = " + h + " J = " + w);
        }

        return imageRgbValue;
    }

    private void exportImage(RGBA[][] imageRgbaValue, int height, int width) {
        BufferedImage bufferedImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                RGBA rgba = imageRgbaValue[i][j];
                int pixel = (rgba.getAlpha() << 24) | (rgba.getRed() << 16) | (rgba.getGreen() << 8) | rgba.getBlue();
                bufferedImage.setRGB(i, j, pixel);
            }
        }

        try {
            // retrieve image
            File outputfile = new File("saved.png");
            ImageIO.write(bufferedImage, "png", outputfile);
        } catch (IOException e) {
            System.out.println("Failed to create image");
            return;
        }

        System.out.println("Successfully Exported Image!");
    }

    private RGBA getPixelRgba(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;

        RGBA rgba = new RGBA();

        return rgba.setRed(red).setGreen(green).setBlue(blue).setAlpha(alpha);
    }
}