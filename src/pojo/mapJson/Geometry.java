package pojo.mapJson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Geometry {
    private String type;
    private List<List<List<Double>>> coordinates = new ArrayList<List<List<Double>>>();
//    @SerializedName("coordinates") 
    private List<List<List<List<Double>>>> mcoordinates = new ArrayList<List<List<List<Double>>>>();
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<List<List<Double>>> getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(List<List<List<Double>>> coordinates) {
        this.coordinates = coordinates;
    }
    @Override
    public String toString() {
        return "Geometry [type=" + type + ", coordinates=" + coordinates + "]";
    }
    public List<List<List<List<Double>>>> getMcoordinates() {
        return mcoordinates;
    }
    public void setMcoordinates(List<List<List<List<Double>>>> mcoordinates) {
        this.mcoordinates = mcoordinates;
    }
   

}
