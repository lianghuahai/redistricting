package pojo;

import java.util.HashMap;
import java.util.List;

public class State {
    private  StateName name;
//    private HashMap<Year, Party> winnerParty;
    private List<CDistrict> congressionalDistricts;
    private long population;
    private HashMap<Race, Integer> race;
    private HashMap<Party, Integer> votes;
    private int maxRedistrictTimes;
    private int redistrictTimes;
    private float fairness;
    private float compactness;
    private float populationVariance;
    private float minGoodness;
    private float currentGoodness;
    private double efficiencyGap;
    private Constraints constraints;
    
    
    public StateName getName() {
        return name;
    }
    public void setName(StateName name) {
        this.name = name;
    }
    public List<CDistrict> getCongressionalDistricts() {
        return congressionalDistricts;
    }
    public void setCongressionalDistricts(List<CDistrict> congressionalDistricts) {
        this.congressionalDistricts = congressionalDistricts;
    }
    public long getPopulation() {
        return population;
    }
    public void setPopulation(long population) {
        this.population = population;
    }
    public HashMap<Race, Integer> getRace() {
        return race;
    }
    public void setRace(HashMap<Race, Integer> race) {
        this.race = race;
    }
    public HashMap<Party, Integer> getVotes() {
        return votes;
    }
    public void setVotes(HashMap<Party, Integer> votes) {
        this.votes = votes;
    }
    public int getMaxRedistrictTimes() {
        return maxRedistrictTimes;
    }
    public void setMaxRedistrictTimes(int maxRedistrictTimes) {
        this.maxRedistrictTimes = maxRedistrictTimes;
    }
    public int getRedistrictTimes() {
        return redistrictTimes;
    }
    public void setRedistrictTimes(int redistrictTimes) {
        this.redistrictTimes = redistrictTimes;
    }
    public float getFairness() {
        return fairness;
    }
    public void setFairness(float fairness) {
        this.fairness = fairness;
    }
    public float getCompactness() {
        return compactness;
    }
    public void setCompactness(float compactness) {
        this.compactness = compactness;
    }
    public float getPopulationVariance() {
        return populationVariance;
    }
    public void setPopulationVariance(float populationVariance) {
        this.populationVariance = populationVariance;
    }
    public float getMinGoodness() {
        return minGoodness;
    }
    public void setMinGoodness(float minGoodness) {
        this.minGoodness = minGoodness;
    }
    public float getCurrentGoodness() {
        return currentGoodness;
    }
    public void setCurrentGoodness(float currentGoodness) {
        this.currentGoodness = currentGoodness;
    }
    public double getEfficiencyGap() {
        return efficiencyGap;
    }
    public void setEfficiencyGap(double efficiencyGap) {
        this.efficiencyGap = efficiencyGap;
    }
    public Constraints getConstraints() {
        return constraints;
    }
    public void setConstraints(Constraints constraints) {
        this.constraints = constraints;
    }
    
    
    
    
}
