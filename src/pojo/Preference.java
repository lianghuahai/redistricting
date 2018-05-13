package pojo;

import java.util.HashMap;
import java.util.Map;

import utils.PropertyManager;

public class Preference {
//    Map<ObjectElement,Integer> objectElementMap = new HashMap<ObjectElement,Integer>();
    private boolean isNaturalBoundary;
    private boolean isContiguity;
    private int COMPACTNESSWEIGHT;
    private int PARTISANFAIRNESSWEIGHT;
    private int POPULATIONVARIANCEWEIGHT;
    private int RACIALFAIRNESSWEIGHT;
    
//    public Map<ObjectElement, Integer> getObjectElementMap() {
//        return objectElementMap;
//    }
//    public void setObjectElementMap(Map<ObjectElement, Integer> objectElementMap) {
//        this.objectElementMap = objectElementMap;
//    }
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
    public int getCOMPACTNESSWEIGHT() {
        return COMPACTNESSWEIGHT;
    }
    public void setCOMPACTNESSWEIGHT(int cOMPACTNESSWEIGHT) {
        COMPACTNESSWEIGHT = cOMPACTNESSWEIGHT;
    }
    public int getPARTISANFAIRNESSWEIGHT() {
        return PARTISANFAIRNESSWEIGHT;
    }
    public void setPARTISANFAIRNESSWEIGHT(int pARTISANFAIRNESSWEIGHT) {
        PARTISANFAIRNESSWEIGHT = pARTISANFAIRNESSWEIGHT;
    }
    public int getPOPULATIONVARIANCEWEIGHT() {
        return POPULATIONVARIANCEWEIGHT;
    }
    public void setPOPULATIONVARIANCEWEIGHT(int pOPULATIONVARIANCEWEIGHT) {
        POPULATIONVARIANCEWEIGHT = pOPULATIONVARIANCEWEIGHT;
    }
    public int getRACIALFAIRNESSWEIGHT() {
        return RACIALFAIRNESSWEIGHT;
    }
    public void setRACIALFAIRNESSWEIGHT(int rACIALFAIRNESSWEIGHT) {
        RACIALFAIRNESSWEIGHT = rACIALFAIRNESSWEIGHT;
    }
    @Override
    public String toString() {
        return "Preference [isNaturalBoundary=" + isNaturalBoundary + ", isContiguity=" + isContiguity
                + ", COMPACTNESSWEIGHT=" + COMPACTNESSWEIGHT + ", PARTISANFAIRNESSWEIGHT="
                + PARTISANFAIRNESSWEIGHT + ", POPULATIONVARIANCEWEIGHT=" + POPULATIONVARIANCEWEIGHT
                + ", RACIALFAIRNESSWEIGHT=" + RACIALFAIRNESSWEIGHT + "]";
    }
    
}
