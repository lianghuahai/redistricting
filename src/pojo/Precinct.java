package pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import pojo.mapJson.Feature;

public class Precinct {
    private String name;
    private String precinctCode;
    private String secondStylePrecinctCode;
    private CDistrict newCDistrict = new CDistrict();
    private CDistrict originCDistrict = new CDistrict();
    private CDistrict CDistrict = new CDistrict();
    private Set<MapData> map = new HashSet<MapData>();
    private State state;
    private int registeredVoters;
    private int totalVoters;
    private long population;
    private HashMap<Race, Integer> race = new HashMap<Race, Integer>();
    private HashMap<Party, Integer> votes =  new HashMap<Party, Integer>();
    private boolean isFixed;
    private boolean isBorder;
    private Set<Precinct> neighborPrecincts = new HashSet<Precinct>();
    private Set<CDistrict> neighborCDistricts = new HashSet<CDistrict>();
    private  Feature feature = new Feature();
    int cdistrictId;
    private String county;
    private int stateId;
    //getter and setter
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSecondStylePrecinctCode() {
        return secondStylePrecinctCode;
    }
    public void setSecondStylePrecinctCode(String secondStylePrecinctCode) {
        this.secondStylePrecinctCode = secondStylePrecinctCode;
    }
    public String getPrecinctCode() {
        return precinctCode;
    }
    public Feature getFeature() {
        return feature;
    }
    
    public int getStateId() {
        return stateId;
    }
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }
    public String getCounty() {
        return county;
    }
    public void setCounty(String county) {
        this.county = county;
    }
    public HashMap<Party, Integer> getVotes() {
        return votes;
    }
    public void setVotes(HashMap<Party, Integer> votes) {
        this.votes = votes;
    }
    public int getCdistrictId() {
        return cdistrictId;
    }
    public void setCdistrictId(int cdistrictId) {
        this.cdistrictId = cdistrictId;
    }

    private HashMap<String, Integer> vote =  new HashMap<String, Integer>();
    public HashMap<String, Integer> getVote() {
        return vote;
    }
    public void setVote(HashMap<String, Integer> vote) {
        this.vote = vote;
    }
    public void setFeature(Feature feature) {
        this.feature = feature;
    }
    public void setPrecinctCode(String precinctCode) {
        this.precinctCode = precinctCode;
    }
    public int getRegisteredVoters() {
        return registeredVoters;
    }
    public void setRegisteredVoters(int registeredVoters) {
        this.registeredVoters = registeredVoters;
    }
    public int getTotalVoters() {
        return totalVoters;
    }
    public void setTotalVoters(int totalVoters) {
        this.totalVoters = totalVoters;
    }
    public CDistrict getCDistrict() {
        return CDistrict;
    }
    public void setCDistrict(CDistrict cDistrict) {
        CDistrict = cDistrict;
    }
    public Set<Precinct> getNeighborPrecincts() {
        return neighborPrecincts;
    }
    public void setNeighborPrecincts(Set<Precinct> neighborPrecincts) {
        this.neighborPrecincts = neighborPrecincts;
    }
    public Set<CDistrict> getNeighborCDistricts() {
        return neighborCDistricts;
    }
    public void setNeighborCDistricts(Set<CDistrict> neighborCDistricts) {
        this.neighborCDistricts = neighborCDistricts;
    }
    public CDistrict getNewCDistrict() {
        return newCDistrict;
    }
    public void setNewCDistrict(CDistrict newCDistrict) {
        this.newCDistrict = newCDistrict;
    }
    public CDistrict getOriginCDistrict() {
        return originCDistrict;
    }
    public void setOriginCDistrict(CDistrict originCDistrict) {
        this.originCDistrict = originCDistrict;
    }
    public Set<MapData> getMap() {
        return map;
    }
    public void setMap(Set<MapData> map) {
        this.map = map;
    }
    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
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
//    public HashMap<Party, Integer> getVotes() {
//        return votes;
//    }
//    public void setVotes(HashMap<Party, Integer> votes) {
//        this.votes = votes;
//    }
   
    public boolean getIsFixed() {
        return isFixed;
    }
    public void setIsFixed(boolean isFixed) {
        this.isFixed = isFixed;
    }
    public boolean getIsBorder() {
        return isBorder;
    }
    public void setIsBorder(boolean isBorder) {
        this.isBorder = isBorder;
    }
  
    
    @Override
    public String toString() {
        return "Precinct [name=" + name + ", precinctCode=" + precinctCode + ", secondStylePrecinctCode="
                + secondStylePrecinctCode + ", originCDistrict=" + originCDistrict + ", map=" + map
                + ", registeredVoters=" + registeredVoters + ", totalVoters=" + totalVoters + ", population="
                + population + ", race=" + race + ", votes=" + votes + ", isFixed=" + isFixed + ", isBorder="
                + isBorder + ", neighborPrecincts=" + neighborPrecincts + ", neighborCDistricts="
                + neighborCDistricts + ", feature=" + feature + ", cdistrictId=" + cdistrictId + ", vote="
                + vote + "]";
    }
    //methods to be implemented
    public CDistrict getRandomNeighborCDistrict (){
        int length = this.neighborCDistricts.size();
        int index = (int)((length)*Math.random());
        int i = 0;
        for (CDistrict cd : neighborCDistricts) {
            if(i==index){
                return cd;
            }
            i++;
        }
        return null;
    }
   
    public void moveToNeighborCDistrict (CDistrict cd){}
    public void addNeighborPrecinct (Precinct precinct){}
    public void addNeighborCDistrict (CDistrict cd){}
    public int calculatePopulation (){return 1;}
  
    
    
}
