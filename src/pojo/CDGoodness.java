package pojo;

public class CDGoodness {
    private String cdName;
    private double goodness;
    private double compactness;
    private double populationVariance;
    private double partisanFairness;
    private double racialFairness;
    
    private String representive="N/A";
    public double getGoodness() {
        return goodness;
    }
    public void setGoodness(double goodness) {
        this.goodness = goodness;
    }
    public double getCompactness() {
        return compactness;
    }
    public void setCompactness(double compactness) {
        this.compactness = compactness;
    }
    public double getPopulationVariance() {
        return populationVariance;
    }
    public void setPopulationVariance(double populationVariance) {
        this.populationVariance = populationVariance;
    }
    public double getPartisanFairness() {
        return partisanFairness;
    }
    public void setPartisanFairness(double partisanFairness) {
        this.partisanFairness = partisanFairness;
    }
    public double getRacialFairness() {
        return racialFairness;
    }
    public void setRacialFairness(double racialFairness) {
        this.racialFairness = racialFairness;
    }
    public String getCdName() {
        return cdName;
    }
    public void setCdName(String cdName) {
        this.cdName = cdName;
    }
    public String getRepresentive() {
        return representive;
    }
    public void setRepresentive(String representive) {
        this.representive = representive;
    }
    public void setupCDgoodness(double d, double e, double f, double g, double h) {
        // //Equal Population   Partisan Fairness     Racial Fairness  Compactness     Goodness       
        this.populationVariance=d;
        this.partisanFairness=e;
        this.racialFairness=f;
        this.compactness=g;
        this.goodness=h;
    }
    
}
