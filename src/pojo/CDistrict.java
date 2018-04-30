package pojo;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pojo.mapJson.Feature;
import utils.PropertyManager;

public class CDistrict {
    private String name;

    private State state = new State();

    private HashMap<Integer, Party> winnerParty = new HashMap<Integer, Party>();

    private Set<MapData> map = new HashSet<MapData>();

    private Set<Precinct> precinct = new HashSet<Precinct>();

    private long population;

    private HashMap<Race, Integer> race = new HashMap<Race, Integer>();

    private HashMap<Party, Integer> votes = new HashMap<Party, Integer>();

    private Set<Precinct> boundaryPrecincts = new HashSet<Precinct>();

    private float currentGoodness;

    private Feature feature = new Feature();

    private int cdCode;

    private int registerVoters;

    private int totalVoters;

    // setter and getter
    private int stateId;

    private String color;

    public String getName() {
        return name;
    }

    public CDistrict() {
        super();
        votes.put(Party.DEMOCRATIC, 0);
        votes.put(Party.REPUBLICAN, 0);
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public State getState() {
        return state;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getRegisterVoters() {
        return registerVoters;
    }

    public void setRegisterVoters(int registerVoters) {
        this.registerVoters = registerVoters;
    }

    public int getTotalVoters() {
        return totalVoters;
    }

    public void setTotalVoters(int totalVoters) {
        this.totalVoters = totalVoters;
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
                + map + ", population=" + population + ", race=" + race + ", votes=" + votes
                + ", currentGoodness=" + currentGoodness + ", feature=" + feature + ", cdCode=" + cdCode
                + "]";
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
        System.out.println("length"+length);
        int index = (int) ((length) * Math.random());
        int i = 0;
        for (Precinct precinct : boundaryPrecincts) {
            if (i == index) {
                System.out.println("return precinct");
                return precinct;
            }
            i++;
        }
        System.out.println("return null precinct");
        return null;
    }

    public float calculateObjectiveFunction() {
        float goodness = 0;
        Map<ObjectElement, Integer> objectElementMap = this.getState().getPreference().getObjectElementMap();
        goodness += (int)objectElementMap.get(ObjectElement.COMPACTNESSWEIGHT) * calculateCompactness();
        goodness += (int)objectElementMap.get(ObjectElement.PARTISANFAIRNESSWEIGHT) * calculatePartisanFairness();
        goodness += (int)objectElementMap.get(ObjectElement.POPULATIONVARIANCEWEIGHT) * calculatePopulationVariance();
        goodness += (int)objectElementMap.get(ObjectElement.RACIALFAIRNESSWEIGHT) * calculateRacialFairness();
        return goodness;
    }

    public float calculateCompactness() {
        float compactness = 0;
        return compactness;
    }

    public float calculatePartisanFairness() {
        float pFairness = 0;
        return pFairness;
    }

    public float calculatePopulationVariance() {
        int numOfCDs = this.getState().getCongressionalDistricts().size();
        float variance = (float)Math.pow((double)(this.population - this.getState().getPopulationMean()),(double) 2);
        return variance/numOfCDs;
    }

    public int calculateRacialFairness() {
        return 0;
    }

    public float getGoodnessDiff(float cGoodness) {
        return (cGoodness - this.currentGoodness);
    }

    public void removePrecinct(Precinct p) {
        this.precinct.remove(p);
        if (p.getIsBorder()) {
            this.boundaryPrecincts.remove(p);
        }
        p.getNeighborCDistricts().add(this);
        this.population -= p.getPopulation();
        //this.subtractVotes(p);
        //this.subtractRace(p);
    }

    public void addPrecinct(Precinct p) {
        this.precinct.add(p);
        if (p.getIsBorder()) {
            this.boundaryPrecincts.add(p);
        }
        p.getNeighborCDistricts().remove(this);
        this.population += p.getPopulation();
        //this.addVotes(p);
        //this.addRace(p);

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

    public void setUpPrecinctMapJson(Set<Feature> features, int colorCount) {
        Set<Precinct> ps = this.getPrecinct();
        for (Precinct p : ps) {
            for (Feature f : features) {
                if (f.getProperties().getVTDST10().equals(p.getPrecinctCode())) {
                    f.getProperties().setPOPULATION(p.getPopulation());
                    f.getProperties().setREGISTERVOTERS(p.getRegisteredVoters());
                    f.getProperties().setTOTALVOTERS(p.getTotalVoters());
                    f.getProperties().setFill(PropertyManager.getInstance().getValue("color0" + colorCount));
                    p.setFeature(f);
                    this.setColor(f.getProperties().getFill());
                    break;
                }
            }
        }
    }

    public boolean foundNeighborPrecinct(Precinct targetPrecinct,String neighborPrecinctCode) {
        Set<Precinct> precincts = this.getPrecinct();
        for (Precinct precinct : precincts) {
            if(neighborPrecinctCode.equals(precinct.getPrecinctCode())){
                targetPrecinct.getNeighborPrecincts().add(precinct);
                return true;
            }
        }
        return false;
    }

    public void setupBoundaryPrecincts() {
        for (Precinct precinct : this.getPrecinct()) {
            if(precinct.getNeighborPrecincts().size()!=0){
                for (Precinct neighborPrecincts : precinct.getNeighborPrecincts()) {
                    if(!precinct.getCDistrict().getName().equals(neighborPrecincts.getCDistrict().getName())){
                        this.getBoundaryPrecincts().add(precinct);
                        precinct.getNeighborCDistricts().add(neighborPrecincts.getCDistrict());
                        precinct.setIsBorder(true);
                        break;
                    }
                }
            }
        }
    }

}
