package pojo;

import java.util.Set;

public class MapData {
    private String name;
    private Set<Boundary> boundaries;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<Boundary> getBoundaries() {
        return boundaries;
    }
    public void setBoundaries(Set<Boundary> boundaries) {
        this.boundaries = boundaries;
    }
    
}
