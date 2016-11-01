import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by insan on 10/31/16.
 */
public class ImageResizer {
    Logger logger = Logger.getLogger(this.getClass().getName().toString());

    enum MeanOption {
        ARITHMATIC_MEAN,
        GEOMETRIC_MEAN,
        HARMONIC_MEAN
    }

    void getImageFile() {
        BufferedImage bufferedImage = getReadImage();
        _2Ddimension ddimension = getImageDimension(bufferedImage);

        RGBA[][] rgbValueArray = getImageRgbValue(bufferedImage);

        //exportImage(rgbValueArray, bufferedImage.getHeight(), bufferedImage.getWidth());

        resizeImageByHalf(rgbValueArray, ddimension.getHeight(), ddimension.getWidth());

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
            System.out.println("Successfully loaded image");
        } catch (IOException e) {
            System.out.println("Sorry failed to load image");
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

        int h = 0, w = 0;

        try {
            for (h = 0; h < height; h++) {
                for (w = 0; w < width; w++) {
                    int pixel = bufferedImage.getRGB(w, h);
                    RGBA rgba = getPixelRgba(pixel);
                    imageRgbValue[h][w] = rgba;
                }
            }
        } catch (Exception e) {
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

    private void resizeImageByHalf(RGBA[][] imagePixelsRGBA, Integer height, Integer width) {
        RGBA[][] meanedRgbArray = new RGBA[height / 2][width / 2];
        int a = 0, b = 0, i = 0, j = 0;
        for (a = 0, i = 0; i < height; i += 2, a++) {
            if(a == height/2){continue;}
            for (b= 0 , j = 0; j < width; j += 2, b++) {
                if (b==width/2){continue;}
                try {
                    RGBA rgba = new RGBA();
                    List<Integer> integerList = new LinkedList<>();
                    integerList.add(imagePixelsRGBA[i][j].getAlpha());
                    integerList.add(imagePixelsRGBA[i + 1][j].getAlpha());
                    integerList.add(imagePixelsRGBA[i + 1][j + 1].getAlpha());
                    integerList.add(imagePixelsRGBA[i][j + 1].getAlpha());
                    rgba.setAlpha(arithmeticMean(integerList));
                    integerList.clear();

                    integerList.add(imagePixelsRGBA[i][j].getRed());
                    integerList.add(imagePixelsRGBA[i + 1][j].getRed());
                    integerList.add(imagePixelsRGBA[i + 1][j + 1].getRed());
                    integerList.add(imagePixelsRGBA[i][j + 1].getRed());
                    rgba.setRed(arithmeticMean(integerList));
                    integerList.clear();

                    integerList.add(imagePixelsRGBA[i][j].getGreen());
                    integerList.add(imagePixelsRGBA[i + 1][j].getGreen());
                    integerList.add(imagePixelsRGBA[i + 1][j + 1].getGreen());
                    integerList.add(imagePixelsRGBA[i][j + 1].getGreen());
                    rgba.setGreen(arithmeticMean(integerList));
                    integerList.clear();

                    integerList.add(imagePixelsRGBA[i][j].getBlue());
                    integerList.add(imagePixelsRGBA[i + 1][j].getBlue());
                    integerList.add(imagePixelsRGBA[i + 1][j + 1].getBlue());
                    integerList.add(imagePixelsRGBA[i][j + 1].getBlue());
                    rgba.setBlue(arithmeticMean(integerList));
                    integerList.clear();


                    meanedRgbArray[a][b] = rgba;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(" a " + a + " b " + b + " i " + i + " j " + j);
                }
            }
        }

        exportImage(meanedRgbArray, height / 2, width / 2);
    }

    private Integer getMean(List<Integer> integerList, MeanOption meanOption) {
        if (meanOption == MeanOption.ARITHMATIC_MEAN) {
            return arithmeticMean(integerList);
        } else if (meanOption == MeanOption.GEOMETRIC_MEAN) {
            return geometricMean(integerList);
        } else if (meanOption == MeanOption.HARMONIC_MEAN) {
            return harmonicMean(integerList);
        }

        return null;
    }

    private Integer arithmeticMean(List<Integer> integerList) {
        Integer zero = 0;
        Long sum = zero.longValue();
        for (int i = 0; i < integerList.size(); i++) {
            sum += integerList.get(i).longValue();
        }
        return Long.valueOf(sum / integerList.size()).intValue();
    }

    private Integer geometricMean(List<Integer> integerList) {
        Integer one = 1;
        Long multiplication = one.longValue();
        for (int i = 0; i < integerList.size(); i++) {
            multiplication *= integerList.get(i).longValue();
        }

        return Long.valueOf(multiplication / integerList.size()).intValue();
    }

    private Integer harmonicMean(List<Integer> integerList) {
        Integer zero = 0;
        Long sum = zero.longValue();
        for (int i = 0; i < integerList.size(); i++) {
            sum += integerList.get(i).longValue();
        }
        return Long.valueOf(sum / integerList.size()).intValue();
    }
}