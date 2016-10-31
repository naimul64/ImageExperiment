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
    void getImageFile(){
        Integer height, width;
        BufferedImage bufferedImage = getReadImage();
        _2Ddimension ddimension = getImageDimension(bufferedImage);

        System.out.println("\nH: " + ddimension.getHeight() + " W " + ddimension.getWidth());

    }

    private _2Ddimension getImageDimension(BufferedImage img) {
        _2Ddimension ddimension = new _2Ddimension();
        return ddimension.setHeight(img.getHeight()).setWidth(img.getWidth());
    }

    BufferedImage getReadImage() {
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
}