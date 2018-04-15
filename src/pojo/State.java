package pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class State {
    private  StateName name;
//    private HashMap<Year, Party> winnerParty;
    private List<CDistrict> congressionalDistricts = new ArrayList<CDistrict>();
    private long population;
    private HashMap<Race, Integer> race = new HashMap<Race, Integer>();
    private HashMap<Party, Integer> votes =  new HashMap<Party, Integer>();
    private int maxRedistrictTimes;
    private int redistrictTimes;
    private float fairness;
    private float compactness;
    private float populationVariance;
    private float minGoodness;
    private float currentGoodness;
    private double efficiencyGap;
    private Constraints constraints;
    private HashMap<ObjectElement, Integer> preference = new HashMap<ObjectElement, Integer>(); 
    
    //setter and getter
    public StateName getName() {
        return name;
    }
    public void setName(StateName name) {
        this.name = name;
    }
    
    public HashMap<ObjectElement, Integer> getPreference() {
        return preference;
    }
    public void setPreference(HashMap<ObjectElement, Integer> preference) {
        this.preference = preference;
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
    
    
    //methods to be implemented
    public void redistrict (){
        Precinct startPrecinct = new Precinct();
        boolean isMoved = true;
        while (true){
            if(isMoved){
                startPrecinct =selectStartPrecinct();
            }
            isMoved = tryMove(startPrecinct);
            if(checkTermination()){
                break;
            }
        }
    }
    
    public Precinct selectStartPrecinct(){
        CDistrict cd = getLowestGoodnessCDistrict();
        List<Precinct> Precincts = cd.getBoundaryPrecincts();
        Precinct startPrecinct = Precincts.remove(0);
        Precincts.add(startPrecinct);
        return startPrecinct;
    } 
    
    private CDistrict getLowestGoodnessCDistrict() {
        float minGoodness = 1;
        for (CDistrict cDistrict : congressionalDistricts) {
            if(minGoodness > cDistrict.getCurrentGoodness()){
                minGoodness = cDistrict.getCurrentGoodness();
            }
        }
        for (CDistrict cDistrict : congressionalDistricts) {
            if(minGoodness == cDistrict.getCurrentGoodness()){
                return cDistrict;
            }
        }
        return null;
    }
    
    public boolean tryMove(Precinct selectPrecinct){
        CDistrict randomNeighborCD = selectPrecinct.getRandomNeighborCDistrict();
        moveToDestinationCD(selectPrecinct,randomNeighborCD);
        CDistrict originCDistrict = selectPrecinct.getCDistrict();
        updateData();
        if(!isValidConstraints()){
            moveToDestinationCD(selectPrecinct,originCDistrict);
            updateData();
            return false;
        }
        if(calculateObjectiveFunction()<currentGoodness){
            moveToDestinationCD(selectPrecinct,originCDistrict);
            updateData();
            return false;
        }
        return true;
    }

    private void moveToDestinationCD(Precinct selectPrecinct, CDistrict destinationCD) {
        CDistrict originCD = selectPrecinct.getCDistrict();
        originCD.getBoundaryPrecincts().remove(selectPrecinct);
        originCD.getPrecinct().remove(selectPrecinct);
        originCD.getMap().remove(selectPrecinct.getName());
        selectPrecinct.getNeighborCDistrictList().add(originCD);
        //geo data
        
        originCD =  destinationCD;
        destinationCD.getBoundaryPrecincts().add(selectPrecinct);
        destinationCD.getPrecinct().add(selectPrecinct);
        selectPrecinct.getNeighborCDistrictList().remove(destinationCD);
    }
    public boolean checkTermination(){
        if(currentGoodness > minGoodness || redistrictTimes >= 1000){
            return true;
        }
        return false;
    }
    
    
    public State clone(){return null;}
    
    public void updateData(){}
    public boolean isValidConstraints(){return true;}
    public boolean checkRedistrictTimes(){return true;}
    public boolean checkGoodnessVariance(){return true;}
    public float calculateObjectiveFunction(){return 1;}
    public int calculatePopulation (){return 1;}
}
