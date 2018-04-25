package pojo.mapJson;


import java.util.HashSet;
import java.util.Set;

public class PrecinctJson {
    private String type;
    private Set<Feature> features  = new HashSet<Feature>();
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Set<Feature> getFeatures() {
        return features;
    }
    public void setFeatures(Set<Feature> features) {
        this.features = features;
    }
    @Override
    public String toString() {
        return "precinctJson [type=" + type + ", features=" + features + "]";
    }
    
}
