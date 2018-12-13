/**
 * Created by insan on 10/31/16.
 */
class _2Ddimension{
    private Integer height;
    private Integer width;

    _2Ddimension setHeight(Integer h){
        this.height = h;
        return this;
    }

    _2Ddimension setWidth(Integer w){
        this.width = w;
        return this;
    }

    Integer getHeight(){
        return this.height;
    }

    Integer getWidth(){
        return this.width;
    }
}
