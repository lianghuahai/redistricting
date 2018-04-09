package pojo;

public class Constraints {
    private float compactnessWeight;
    private float partisanFairnessWeight;
    private float racialFairnessWeight;
    private boolean isNaturalBoundary;
    private boolean isContiguity;
    
    public Constraints(float compactnessWeight, float partisanFairnessWeight, float racialFairnessWeight,
            boolean isNaturalBoundary, boolean isContiguity) {
        this.compactnessWeight = compactnessWeight;
        this.partisanFairnessWeight = partisanFairnessWeight;
        this.racialFairnessWeight = racialFairnessWeight;
        this.isNaturalBoundary = isNaturalBoundary;
        this.isContiguity = isContiguity;
    }
    public float getCompactnessWeight() {
        return compactnessWeight;
    }
    public void setCompactnessWeight(float compactnessWeight) {
        this.compactnessWeight = compactnessWeight;
    }
    public float getPartisanFairnessWeight() {
        return partisanFairnessWeight;
    }
    public void setPartisanFairnessWeight(float partisanFairnessWeight) {
        this.partisanFairnessWeight = partisanFairnessWeight;
    }
    public float getRacialFairnessWeight() {
        return racialFairnessWeight;
    }
    public void setRacialFairnessWeight(float racialFairnessWeight) {
        this.racialFairnessWeight = racialFairnessWeight;
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
    

}
