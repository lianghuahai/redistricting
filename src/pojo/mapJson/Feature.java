package pojo.mapJson;

public class Feature {
    private String type;
    private Geometry geometry = new Geometry();
    private Propertys properties = new Propertys();
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Geometry getGeometry() {
        return geometry;
    }
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
    public Propertys getProperties() {
        return properties;
    }
    public void setProperties(Propertys properties) {
        this.properties = properties;
    }
    @Override
    public String toString() {
        return "Feature [type=" + type + ", geometry=" + geometry + ", properties=" + properties + "]";
    }
    
    
}
