package pojo;

import java.util.ArrayList;
import java.util.List;

public class stateInfo {
    private long population; 
    private double aveIncome;
    private int numOfCds;
    private int numOfPds;
    private double area;
    private List<CDInfor> cdInforList = new ArrayList<CDInfor>();
    private float goodness; 
    private float compactness; 
    private float racialFairness; 
    private float patisanFairness; 
    
    public long getPopulation() {
        return population;
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
    public float getGoodness() {
        return goodness;
    }
    public void setGoodness(float goodness) {
        this.goodness = goodness;
    }
    public float getCompactness() {
        return compactness;
    }
    public void setCompactness(float compactness) {
        this.compactness = compactness;
    }
    public float getRacialFairness() {
        return racialFairness;
    }
    public void setRacialFairness(float racialFairness) {
        this.racialFairness = racialFairness;
    }
    public float getPatisanFairness() {
        return patisanFairness;
    }
    public void setPatisanFairness(float patisanFairness) {
        this.patisanFairness = patisanFairness;
    }
    
    
    
}
