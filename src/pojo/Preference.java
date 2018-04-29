package pojo;

import java.util.HashMap;
import java.util.Map;

public class Preference {
    Map<ObjectElement,Integer> objectElementMap = new HashMap<ObjectElement,Integer>();
    private boolean isNaturalBoundary;
    private boolean isContiguity;
    
  
    public Map<ObjectElement, Integer> getObjectElementMap() {
        return objectElementMap;
    }
    public void setObjectElementMap(Map<ObjectElement, Integer> objectElementMap) {
        this.objectElementMap = objectElementMap;
    }
    public boolean getIsNaturalBoundary() {
        return isNaturalBoundary;
    }
    public void setIsNaturalBoundary(boolean isNaturalBoundary) {
        this.isNaturalBoundary = isNaturalBoundary;
    }
    public boolean getIsContiguity() {
        return isContiguity;
    }
    public void setIsContiguity(boolean isContiguity) {
        this.isContiguity = isContiguity;
    }

}
