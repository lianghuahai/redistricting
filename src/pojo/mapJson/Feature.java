package pojo.mapJson;

import java.util.HashSet;
import java.util.Set;

public class Feature {
    private String type;
    private Set<Geometry> geometry = new HashSet<Geometry>();
    private Set<Property> properties = new HashSet<Property>();
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Set<Geometry> getGeometry() {
        return geometry;
    }
    public void setGeometry(Set<Geometry> geometry) {
        this.geometry = geometry;
    }
    public Set<Property> getProperties() {
        return properties;
    }
    public void setProperties(Set<Property> properties) {
        this.properties = properties;
    }
    
}
