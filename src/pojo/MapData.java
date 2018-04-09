package pojo;

import java.util.List;

public class MapData {
    private String name;
    private List<Boundary> boundaries;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Boundary> getBoundaries() {
        return boundaries;
    }
    public void setBoundaries(List<Boundary> boundaries) {
        this.boundaries = boundaries;
    }
    
}
