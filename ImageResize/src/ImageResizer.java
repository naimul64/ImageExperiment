import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by insan on 10/31/16.
 */
public class ImageResizer {
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
