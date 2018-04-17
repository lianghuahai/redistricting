package pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CDistrict {
    private String name;
    private State state = new State();
    private HashMap<Integer, Party> winnerParty;
    private Set<MapData> map = new HashSet<MapData>();
    private List<Precinct> precinct = new ArrayList<Precinct>();
    private long population;
    private HashMap<Race, Integer> race = new HashMap<Race, Integer>();
    private HashMap<Party, Integer> votes= new HashMap<Party, Integer>();
    private List<Precinct> boundaryPrecincts = new ArrayList<Precinct>();
    private float currentGoodness;
    
    //setter and getter
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public State getState() {
        return state;
    }
    public HashMap<Integer, Party> getWinnerParty() {
        return winnerParty;
    }
    public void setWinnerParty(HashMap<Integer, Party> winnerParty) {
        this.winnerParty = winnerParty;
    }
    public void setState(State state) {
        this.state = state;
    }
    
    public Set<MapData> getMap() {
        return map;
    }
    public void setMap(Set<MapData> map) {
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
    
    //methods to be implemented
    public int calculatePopulation (){return 1;}
    public int calculateVotes (){return 1;}
    public Precinct getRandomBoundaryPrecinct (){
        int length = this.boundaryPrecincts.size();
        if(length==0){
            return null;
        }else{
            int index = (int)((length)*Math.random());
            return boundaryPrecincts.get(index);
        }
   }
    
}
