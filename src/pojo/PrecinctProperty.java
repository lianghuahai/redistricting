package pojo;

import java.util.ArrayList;
import java.util.List;

public class PrecinctProperty {
    private String VTDST10;
    private String fill;
    private boolean terminated;
    private double goodness;
    private double compactness;
    private double populationVariance;
    private double partisanFairness;
    private double racialFairness;
    
    private double originalGoodness;
    private double originalCompactness;
    private double originalPopulationVariance;
    private double originalPartisanFairness;
    private double originalRacialFairness;
    private List<CDGoodness> oldGoodness= new ArrayList<CDGoodness>();
    private List<CDGoodness> newGoodness= new ArrayList<CDGoodness>();
    public String getVTDST10() {
        return VTDST10;
    }
    public void setVTDST10(String VTDST10) {
        this.VTDST10 = VTDST10;
    }
    public String getFill() {
        return fill;
    }
    public void setFill(String fill) {
        this.fill = fill;
    }
    public boolean getTerminated() {
        return terminated;
    }
    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }
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
    public double getOriginalGoodness() {
        return originalGoodness;
    }
    public void setOriginalGoodness(double originalGoodness) {
        this.originalGoodness = originalGoodness;
    }
    public double getOriginalCompactness() {
        return originalCompactness;
    }
    public void setOriginalCompactness(double originalCompactness) {
        this.originalCompactness = originalCompactness;
    }
    public double getOriginalPopulationVariance() {
        return originalPopulationVariance;
    }
    public void setOriginalPopulationVariance(double originalPopulationVariance) {
        this.originalPopulationVariance = originalPopulationVariance;
    }
    public double getOriginalPartisanFairness() {
        return originalPartisanFairness;
    }
    public void setOriginalPartisanFairness(double originalPartisanFairness) {
        this.originalPartisanFairness = originalPartisanFairness;
    }
    public double getOriginalRacialFairness() {
        return originalRacialFairness;
    }
    public void setOriginalRacialFairness(double originalRacialFairness) {
        this.originalRacialFairness = originalRacialFairness;
    }
    public List<CDGoodness> getOldGoodness() {
        return oldGoodness;
    }
    public void setOldGoodness(List<CDGoodness> oldGoodness) {
        this.oldGoodness = oldGoodness;
    }
    public List<CDGoodness> getNewGoodness() {
        return newGoodness;
    }
    public void setNewGoodness(List<CDGoodness> newGoodness) {
        this.newGoodness = newGoodness;
    }
   
  
    
}
