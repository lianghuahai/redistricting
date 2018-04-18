package pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CDistrict {
    private String name;
    private State state = new State();
    private HashMap<Integer, Party> winnerParty;
    private Set<MapData> map = new HashSet<MapData>();
    private Set<Precinct> precinct = new HashSet<Precinct>();
    private long population;
    private HashMap<Race, Integer> race = new HashMap<Race, Integer>();
    private HashMap<Party, Integer> votes = new HashMap<Party, Integer>();
    private Set<Precinct> boundaryPrecincts = new HashSet<Precinct>();
    private float currentGoodness;

    // setter and getter
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

    public void movePrecinctToDestinationCD(Precinct selectPrecinct, CDistrict destinationCD) {
        this.getBoundaryPrecincts().remove(selectPrecinct);
        this.getPrecinct().remove(selectPrecinct);
        selectPrecinct.getNeighborCDistrictList().remove(destinationCD);
        selectPrecinct.getNeighborCDistrictList().add(this);
        destinationCD.getBoundaryPrecincts().add(selectPrecinct);
        destinationCD.getPrecinct().add(selectPrecinct);
    }

    public float calculateObjectiveFunction() {
        float compactness = this.calculateCompactness();
        float populationVariance = this.caculatePV();
        float partisanFairness = this.caculatePartisanFairness();
        float racialFairness = this.caculateRacialFairness();
        float goodness = this.caculateGoodness(compactness, populationVariance, partisanFairness,racialFairness);
        return goodness;
    }

    private float caculateGoodness(float compactness, float populationVariance, float partisanFairness,float racialFairness) {
        HashMap<ObjectElement, Integer> preference = this.getState().getPreference();
        float compactnessWeight = preference.get(ObjectElement.COMPACTNESSWEIGHT);
        float populationVarianceWeight = preference.get(ObjectElement.POPULATIONVARIANCEWEIGHT);
        float racialFairnessWeight = preference.get(ObjectElement.RACIALFAIRNESSWEIGHT);
        float partisanFairnessWeight = preference.get(ObjectElement.PARTISANFAIRNESSWEIGHT);
        float goodness = compactnessWeight * compactness + populationVarianceWeight * populationVariance
                        + partisanFairness * partisanFairnessWeight + racialFairnessWeight + racialFairness;
        return goodness;
    }

    private float caculatePartisanFairness() {
        return 0;
    }

    private float caculatePV() {
        return 0;
    }

    private float calculateCompactness() {
        return 0;
    }

    private float caculateRacialFairness() {
        return 0;
    }

    public int calculatePopulation() {

        return 1;
    }

    public float getGoodnessDiff(float cGoodness) {
        return (cGoodness - this.currentGoodness);
    }
}
