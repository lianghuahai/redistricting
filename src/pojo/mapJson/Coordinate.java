package pojo.mapJson;

import java.util.HashSet;
import java.util.Set;

public class Coordinate {
    private Set<geoData> position = new HashSet<geoData>();

    public Set<geoData> getPosition() {
        return position;
    }

    public void setPosition(Set<geoData> position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Coordinate [position=" + position + "]";
    }
    
}
