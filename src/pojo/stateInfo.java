package pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class stateInfo {
    private long population; 
    private double aveIncome;
    private int numOfCds;
    private int numOfPds;
    private double area;
    private List<CDInfor> cdInforList = new ArrayList<CDInfor>();
    private List<CDGoodness> details= new ArrayList<CDGoodness>();
    private double goodness; 
    private double compactness; 
    private double racialFairness; 
    private double patisanFairness; 
    private double populationVariance; 
//    private 
    
    
    public long getPopulation() {
        return population;
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
    public double getRacialFairness() {
        return racialFairness;
    }
    public void setRacialFairness(double racialFairness) {
        this.racialFairness = racialFairness;
    }
    public double getPatisanFairness() {
        return patisanFairness;
    }
    public void setPatisanFairness(double patisanFairness) {
        this.patisanFairness = patisanFairness;
    }
    public double getPopulationVariance() {
        return populationVariance;
    }
    public void setPopulationVariance(double populationVariance) {
        this.populationVariance = populationVariance;
    }
    public List<CDGoodness> getDetails() {
        return details;
    }
    public void setDetails(List<CDGoodness> details) {
        this.details = details;
    }
    public void setPopulation(long population) {
        this.population = population;
    }
    public double getAveIncome() {
        return aveIncome;
    }
    public void setAveIncome(double aveIncome) {
        this.aveIncome = aveIncome;
    }
    public int getNumOfCds() {
        return numOfCds;
    }
    public void setNumOfCds(int numOfCds) {
        this.numOfCds = numOfCds;
    }
    public double getArea() {
        return area;
    }
    public void setArea(double area) {
        this.area = area;
    }
    public int getNumOfPds() {
        return numOfPds;
    }
    public void setNumOfPds(int numOfPds) {
        this.numOfPds = numOfPds;
    }
    public List<CDInfor> getCdInforList() {
        return cdInforList;
    }
    public void setCdInforList(List<CDInfor> cdInforList) {
        this.cdInforList = cdInforList;
    }
   
    public void setupGoodness(State originalState) {
        Set<CDistrict> cds = originalState.getCongressionalDistricts();
        for (int i = 1; i <= cds.size(); i++) {
            for (CDistrict cDistrict : cds) {
                if(cDistrict.getName().equals("cd"+i)){
                    setupDetails(cDistrict);
                }
            }
        }
        
    }
    private void setupDetails(CDistrict cDistrict) {
        CDGoodness cdGoodness = new CDGoodness();
        cdGoodness.setCdName(cDistrict.getName());
        cdGoodness.setCompactness(cDistrict.getCompactness());
        cdGoodness.setGoodness(cDistrict.getCurrentGoodness());
        cdGoodness.setPartisanFairness(cDistrict.getPartisanFairness());
        cdGoodness.setPopulationVariance(cDistrict.getPopulationVariance());
        cdGoodness.setRacialFairness(cDistrict.getRacialFairness());
        this.details.add(cdGoodness);
    }
    
    
    
}
