package pojo.mapJson;


import java.util.HashSet;
import java.util.Set;

import pojo.Preference;

public class PrecinctJson {
    private String type;
    private Set<Feature> features  = new HashSet<Feature>();
    private String sName ;
    private transient boolean isNaturalBoundary;
    private transient boolean isContiguity;
    private transient int COMPACTNESSWEIGHT;
    private transient int PARTISANFAIRNESSWEIGHT;
    private transient int POPULATIONVARIANCEWEIGHT;
    private transient int RACIALFAIRNESSWEIGHT;
    
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
