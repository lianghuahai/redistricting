package pojo.mapJson;

import java.util.HashSet;
import java.util.Set;

public class geoData {
    private Set<Double> coordinates = new HashSet<Double>();

    public Set<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Set<Double> coordinates) {
        this.coordinates = coordinates;
    }
}
