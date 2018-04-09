package pojo;

import java.util.HashMap;
import java.util.List;

public class Precinct {
    private String name;
    private CDistrict newCDistrict;
    private CDistrict originCDistrict;
    private List<MapData> map;
    private State state;
    private long population;
    private HashMap<Race, Integer> race;
    private HashMap<Party, Integer> votes;
    private boolean isPreserved;
    private boolean isBorder;
    private List<Precinct> neighborPrecinctList;
    private List<CDistrict> neighborCDistrictList;
    
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public boolean isPreserved() {
        return isPreserved;
    }
    public void setPreserved(boolean isPreserved) {
        this.isPreserved = isPreserved;
    }
    public boolean isBorder() {
        return isBorder;
    }
    public void setBorder(boolean isBorder) {
        this.isBorder = isBorder;
    }
    public List<Precinct> getNeighborPrecinctList() {
        return neighborPrecinctList;
    }
    public void setNeighborPrecinctList(List<Precinct> neighborPrecinctList) {
        this.neighborPrecinctList = neighborPrecinctList;
    }
    public List<CDistrict> getNeighborCDistrictList() {
        return neighborCDistrictList;
    }
    public void setNeighborCDistrictList(List<CDistrict> neighborCDistrictList) {
        this.neighborCDistrictList = neighborCDistrictList;
    }

    
}
