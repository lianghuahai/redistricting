package pojo.mapJson;


import java.util.HashSet;
import java.util.Set;

import pojo.Preference;

public class PrecinctJson {
    private String sName ;
    private String type;
    private boolean isNaturalBoundary;
    private boolean isContiguity;
    private int COMPACTNESSWEIGHT;
    private int PARTISANFAIRNESSWEIGHT;
    private int POPULATIONVARIANCEWEIGHT;
    private int RACIALFAIRNESSWEIGHT;
    
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
    public String getsName() {
        return sName;
    }
    public void setsName(String sName) {
        this.sName = sName;
    }
    public boolean isNaturalBoundary() {
        return isNaturalBoundary;
    }
    public void setNaturalBoundary(boolean isNaturalBoundary) {
        this.isNaturalBoundary = isNaturalBoundary;
    }
    public boolean isContiguity() {
        return isContiguity;
    }
    public void setContiguity(boolean isContiguity) {
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
    
}
