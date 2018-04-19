package pojo.mapJson;

import java.util.HashSet;
import java.util.Set;

public class Geometry {
    private String type;
    private Set<Coordinate> coordinates  = new HashSet<Coordinate>();
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Set<Coordinate> getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(Set<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

}
