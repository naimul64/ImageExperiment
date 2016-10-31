/**
 * Created by insan on 10/31/16.
 */
public class RGBA {
    private Integer red;
    private Integer green;
    private Integer blue;
    private Integer alpha;

    Integer getRed(){
        return this.red;
    }
    Integer getGreen(){
        return this.green;
    }
    Integer getBlue(){
        return this.blue;
    }
    Integer getAlpha(){
        return this.alpha;
    }

    RGBA setRed(Integer red){
        this.red = red;
        return this;
    }
    RGBA setGreen(Integer green){
        this.green = green;
        return this;
    }
    RGBA setBlue(Integer blue){
        this.blue = blue;
        return this;
    }
    RGBA setAlpha(Integer alpha){
        this.alpha = alpha;
        return this;
    }
}
