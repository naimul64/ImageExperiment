import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by insan on 10/31/16.
 */
public class ImageResizer {
    Logger logger = Logger.getLogger(this.getClass().getName().toString());
    void getImageFile(){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("/home/insan/Documents/MY_PROJECTS/ImageExperiment/Images/mountain.jpeg"));
            System.out.print("Successfully loaded image");
        } catch (IOException e) {
            System.out.print("Sorry failed to load image");
        }
    }
}
