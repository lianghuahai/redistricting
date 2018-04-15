package pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Precinct {
    private String name;
    private CDistrict newCDistrict = new CDistrict();
    private CDistrict originCDistrict = new CDistrict();
    private CDistrict CDistrict = new CDistrict();
    private List<MapData> map = new ArrayList<MapData>();
    private State state;
    private long population;
    private HashMap<Race, Integer> race = new HashMap<Race, Integer>();
    private HashMap<Party, Integer> votes =  new HashMap<Party, Integer>();
    private boolean isFixed;
    private boolean isBorder;
    private List<Precinct> neighborPrecincts = new ArrayList<Precinct>();
    private List<CDistrict> neighborCDistricts = new ArrayList<CDistrict>();
    
    //getter and setter
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public CDistrict getCDistrict() {
        return CDistrict;
    }
    public void setCDistrict(CDistrict cDistrict) {
        CDistrict = cDistrict;
    }
    public List<Precinct> getNeighborPrecincts() {
        return neighborPrecincts;
    }
    public void setNeighborPrecincts(List<Precinct> neighborPrecincts) {
        this.neighborPrecincts = neighborPrecincts;
    }
    public List<CDistrict> getNeighborCDistricts() {
        return neighborCDistricts;
    }
    public void setNeighborCDistricts(List<CDistrict> neighborCDistricts) {
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
    public List<MapData> getMap() {
        return map;
    }
    public void setMap(List<MapData> map) {
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
    public HashMap<Party, Integer> getVotes() {
        return votes;
    }
    public void setVotes(HashMap<Party, Integer> votes) {
        this.votes = votes;
    }
   
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
    public List<Precinct> getNeighborPrecinctList() {
        return neighborPrecincts;
    }
    public void setNeighborPrecinctList(List<Precinct> neighborPrecincts) {
        this.neighborPrecincts = neighborPrecincts;
    }
    public List<CDistrict> getNeighborCDistrictList() {
        return neighborCDistricts;
    }
    public void setNeighborCDistrictList(List<CDistrict> neighborCDistricts) {
        this.neighborCDistricts = neighborCDistricts;
    }

    //methods to be implemented
    public CDistrict getRandomNeighborCDistrict (){
        List<CDistrict> neighborCDs = getNeighborCDistrictList();
        CDistrict cd = new CDistrict();
        if(neighborCDs.size()>0){
            cd = neighborCDs.get(0);
        }else{
            return null;
        }
        neighborCDs.add(cd);
        return cd;
    }
    public void moveToNeighborCDistrict (CDistrict cd){}
    public void addNeighborPrecinct (Precinct precinct){}
    public void addNeighborCDistrict (CDistrict cd){}
    public int calculatePopulation (){return 1;}
    
    
}
