package pojo;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import pojo.mapJson.Feature;

public class CDistrict {
    private String name;
    private State state = new State();
    private HashMap<Integer, Party> winnerParty=new HashMap<Integer, Party>();
    private Set<MapData> map = new HashSet<MapData>();
    private Set<Precinct> precinct = new HashSet<Precinct>();
    private long population;
    private HashMap<Race, Integer> race = new HashMap<Race, Integer>();
    private HashMap<Party, Integer> votes = new HashMap<Party, Integer>();
    private Set<Precinct> boundaryPrecincts = new HashSet<Precinct>();
    private float currentGoodness;
    private Feature feature = new Feature();
    private int cdCode;
    // setter and getter
    public String getName() {
        return name;
    }
    public CDistrict(){
        super();
        votes.put(Party.DEMOCRATIC, 0);
        votes.put(Party.REPUBLICAN, 0);
    }
    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public int getCdCode() {
        return cdCode;
    }

    public void setCdCode(int cdCode) {
        this.cdCode = cdCode;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
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

    public Set<Precinct> getPrecinct() {
        return precinct;
    }

    public void setPrecinct(Set<Precinct> precinct) {
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

    public Set<Precinct> getBoundaryPrecincts() {
        return boundaryPrecincts;
    }

    @Override
    public String toString() {
        return "CDistrict [name=" + name + ", state=" + state + ", winnerParty=" + winnerParty + ", map="
                + map +  ", population=" + population + ", race=" + race
                + ", votes=" + votes  + ", currentGoodness="
                + currentGoodness + ", feature=" + feature + ", cdCode=" + cdCode + "]";
    }
    public void setBoundaryPrecincts(Set<Precinct> boundaryPrecincts) {
        this.boundaryPrecincts = boundaryPrecincts;
    }

    public float getCurrentGoodness() {
        return currentGoodness;
    }

    public void setCurrentGoodness(float currentGoodness) {
        this.currentGoodness = currentGoodness;
    }

    // methods to be implemented
    public int calculateVotes() {
        return 1;
    }

    public Precinct getRandomBoundaryPrecinct() {
        int length = this.boundaryPrecincts.size();
        int index = (int) ((length) * Math.random());
        int i = 0;
        for (Precinct precinct : boundaryPrecincts) {
            if (i == index) {
                return precinct;
            }
            i++;
        }
        return null;
    }

    public float calculateObjectiveFunction() {
    	float goodness = 0;
    	Set<ObjectElement> elements= EnumSet.allOf(ObjectElement.class);
    	for(ObjectElement obj : elements) {
    	    goodness += obj.calculate();
    	}
    	return goodness;
    }

    private float calculateCompactness() {
    	float compactness = 0;
    	Set<CompactnessElement> elements= EnumSet.allOf(CompactnessElement.class);
    	for(CompactnessElement obj : elements) {
    		compactness += obj.calculate();
    	}
        return compactness;
    }

    public float getGoodnessDiff(float cGoodness) {
        return (cGoodness - this.currentGoodness);
    }

	public void removePrecinct(Precinct p) {
		this.precinct.remove(p);
		if(p.getIsBorder()){
			this.boundaryPrecincts.remove(p);
		}
		p.getNeighborCDistrictList().add(this);
		this.population -= p.getPopulation();
		this.subtractVotes(p);
        this.subtractRace(p);
	}

	public void addPrecinct(Precinct p) {
		this.precinct.add(p);
		if(p.getIsBorder()){
			this.boundaryPrecincts.add(p);
		}
		p.getNeighborCDistrictList().remove(this);
		this.population += p.getPopulation();
		this.addVotes(p);
        this.addRace(p);
		
	}

    private void addVotes(Precinct precinct) {
    	 HashMap<Party, Integer> precinctVotes = precinct.getVotes();
         for (Party p : this.votes.keySet()) {
             precinctVotes.put(p, votes.get(p) + precinctVotes.get(p));
         }
		
	}

	private void addRace(Precinct precinct) {
		HashMap<Race, Integer> precinctRace = precinct.getRace();
        for (Race r : this.race.keySet()) {
            precinctRace.put(r, race.get(r) + precinctRace.get(r));
        }
		
	}

	private void subtractVotes(Precinct precinct) {
        HashMap<Party, Integer> precinctVotes = precinct.getVotes();
        for (Party p : this.votes.keySet()) {
            precinctVotes.put(p, votes.get(p) - precinctVotes.get(p));
        }
    }

    private void subtractRace(Precinct precinct) {
    	HashMap<Race, Integer> precinctRace = precinct.getRace();
        for (Race r : this.race.keySet()) {
            precinctRace.put(r, race.get(r) - precinctRace.get(r));
        }
    }

}
