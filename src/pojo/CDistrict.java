package pojo;

import java.util.HashMap;
import java.util.List;

public class CDistrict {
    private String name;
    private State state;
//    private HashMap<Year, Party> winnerParty;
    private List<MapData> map;
    private List<Precinct> precinct;
    private long population;
    private HashMap<Race, Integer> race;
    private HashMap<Party, Integer> votes;
    private List<Precinct> boundaryPrecincts;
    private float currentGoodness;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }
    public List<MapData> getMap() {
        return map;
    }
    public void setMap(List<MapData> map) {
        this.map = map;
    }
    public List<Precinct> getPrecinct() {
        return precinct;
    }
    public void setPrecinct(List<Precinct> precinct) {
        this.precinct = precinct;
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
    public List<Precinct> getBoundaryPrecincts() {
        return boundaryPrecincts;
    }
    public void setBoundaryPrecincts(List<Precinct> boundaryPrecincts) {
        this.boundaryPrecincts = boundaryPrecincts;
    }
    public float getCurrentGoodness() {
        return currentGoodness;
    }
    public void setCurrentGoodness(float currentGoodness) {
        this.currentGoodness = currentGoodness;
    }
    
    
    
}
