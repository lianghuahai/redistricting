package pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import utils.PropertyManager;

public class State {
    private final int MAXRUNTIME = Integer.parseInt((PropertyManager.getInstance().getValue("state.MAXRUNTIME")));
    private final double MINGOODNESS = Double.parseDouble((PropertyManager.getInstance().getValue("state.MINGOODNESS")));
    private StateName name;
    private HashMap<Integer, Party> winnerParty;
    private Set<CDistrict> congressionalDistricts = new HashSet<CDistrict>();
    private long population;
    private HashMap<Race, Integer> race = new HashMap<Race, Integer>();
    private HashMap<Party, Integer> votes = new HashMap<Party, Integer>();
    private int redistrictTimes;
    private float fairness;
    private float compactness;
    private float populationVariance;
    private float currentGoodness;
    private double efficiencyGap;
    private Constraints constraints;
    private HashMap<ObjectElement, Integer> preference = new HashMap<ObjectElement, Integer>();

    
    public State(){
        super();
        votes.put(Party.DEMOCRATIC, 0);
        votes.put(Party.REPUBLICAN, 0);
    }
    public int getMAXRUNTIME() {
        return MAXRUNTIME;
    }

    public double getMINGOODNESS() {
        return MINGOODNESS;
    }

    // setter and getter
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

    public Set<CDistrict> getCongressionalDistricts() {
        return congressionalDistricts;
    }

    public void setCongressionalDistricts(Set<CDistrict> congressionalDistricts) {
        this.congressionalDistricts = congressionalDistricts;
    }

    public HashMap<Integer, Party> getWinnerParty() {
        return winnerParty;
    }

    public void setWinnerParty(HashMap<Integer, Party> winnerParty) {
        this.winnerParty = winnerParty;
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

    // methods to be implemented
    public void startAlgorithm() {
        while(!this.checkTermination()){
            this.redistrictTimes++;
            Precinct  startedPrecinct = this.selectStartPrecinct();
            CDistrict neighborCD    = startedPrecinct.getRandomNeighborCDistrict();
            this.tryMove(startedPrecinct,neighborCD);
        }
    }

    public Precinct selectStartPrecinct() {
        CDistrict cd  = this.getLowestGoodnessCDistrict();
        Precinct  startedPrecinct = cd.getRandomBoundaryPrecinct();
        return startedPrecinct;
    }

    private CDistrict getLowestGoodnessCDistrict() {
        float minGoodness = Float.MAX_VALUE;
        CDistrict cd = new CDistrict();
        for (CDistrict cDistrict : congressionalDistricts) {
            if (minGoodness > cDistrict.getCurrentGoodness()) {
                minGoodness = cDistrict.getCurrentGoodness();
                cd=cDistrict;
            }
        }
        return cd;
    }

    public boolean tryMove(Precinct selectedPrecinct,CDistrict destinationCD) {
        CDistrict originCD = selectedPrecinct.getCDistrict();
        originCD.removePrecinct(selectedPrecinct);
        destinationCD.addPrecinct(selectedPrecinct);
        selectedPrecinct.setCDistrict(destinationCD);
        selectedPrecinct.setOriginCDistrict(originCD);
        if (!isValidConstraints()) {
        		destinationCD.removePrecinct(selectedPrecinct);
            originCD.addPrecinct(selectedPrecinct);
            selectedPrecinct.setCDistrict(originCD);
            return false;
        }
        if(validateGoodnessImprovement(originCD,destinationCD)){
        		destinationCD.removePrecinct(selectedPrecinct);
            originCD.addPrecinct(selectedPrecinct);
            selectedPrecinct.setCDistrict(originCD);
            return false;
        }
        return true;
    }

    private boolean validateGoodnessImprovement(CDistrict originCD, CDistrict destinationCD) {
        float newGoodnessOCD = originCD.calculateObjectiveFunction();
        float newGoodnessDCD = destinationCD.calculateObjectiveFunction();
        float goodnessDiff= originCD.getGoodnessDiff(newGoodnessOCD) + destinationCD.getGoodnessDiff(newGoodnessDCD);
        if(goodnessDiff > 0){
            originCD.setCurrentGoodness(newGoodnessOCD);
            destinationCD.setCurrentGoodness(newGoodnessDCD);
            return true;
        }
        return false;
    }

    public boolean checkTermination() {
        if (this.checkGoodness() || this.checkRedistrictTimes()) {
            return true;
        }
        return false;
    }

    private boolean checkGoodness() {
        return (currentGoodness > MINGOODNESS);
    }

    public boolean checkRedistrictTimes() {
        return (redistrictTimes >= MAXRUNTIME);
    }

    public State clone() {
        State workingState = new State();
        workingState.setName(name);
        workingState.setPopulation(population);
        workingState.setEfficiencyGap(efficiencyGap);
        workingState.setFairness(fairness);
        workingState.setCompactness(compactness);
        workingState.setPopulationVariance(populationVariance);
        workingState.setCurrentGoodness(currentGoodness);
        workingState.setPreference(preference);
        this.copyWinnerParty(winnerParty, workingState.getWinnerParty());
        this.copyRace(race, workingState.getRace());
        this.copyParty(votes, workingState.getVotes());
        this.copyCDistricts(congressionalDistricts,workingState.getCongressionalDistricts());
        return workingState;
    }

    private void copyCDistricts(Set<CDistrict> originalCDs,Set<CDistrict> destinationCDs) {
        for (CDistrict originalCD : originalCDs) {
            CDistrict destinationCD = new CDistrict();
            destinationCD.setName(originalCD.getName());
            destinationCD.setState(originalCD.getState());
            destinationCD.setPopulation(originalCD.getPopulation());
            destinationCD.setCurrentGoodness(originalCD.getCurrentGoodness());
            this.copyWinnerParty(winnerParty, destinationCD.getWinnerParty());
            this.copyRace(originalCD.getRace(), destinationCD.getRace());
            this.copyParty(originalCD.getVotes(), destinationCD.getVotes());
            this.copyPrecincts(originalCD.getPrecinct(),destinationCD.getPrecinct(),destinationCD);
            //ToDo  - map: Set<MapData>
            //ToDo - boundaryPrecincts: Set<Precinct>
            destinationCDs.add(destinationCD);
        }
        
    }

    private void copyPrecincts(Set<Precinct> originalPrecincts, Set<Precinct> destinationPrecincts,
            CDistrict destinationCD) {
        for (Precinct originalP : originalPrecincts) {
            Precinct workingP = new Precinct();
            workingP.setName(originalP.getName());
            workingP.setState(originalP.getState());
            workingP.setPopulation(originalP.getPopulation());
            workingP.setIsBorder(originalP.getIsBorder());
            workingP.setIsFixed(originalP.getIsFixed());
            workingP.setCDistrict(destinationCD);
            workingP.setMap(originalP.getMap());
            this.copyRace(originalP.getRace(), workingP.getRace());
            this.copyParty(originalP.getVotes(), workingP.getVotes());
            //Todo - neighborPrecinctList: Set<Precinct>
            //Todo - neighborCDistrictList: Set<CDistrict>
            destinationPrecincts.add(workingP);
        }
        
    }

    public void copyRace(HashMap<Race, Integer> originalRace, HashMap<Race, Integer> workingRace) {
        for (Race precinctRace : originalRace.keySet()) {
            workingRace.put(precinctRace, originalRace.get(precinctRace));
        }
    }
    public void copyWinnerParty(HashMap<Integer, Party> originalParty, HashMap<Integer, Party> destinationParty) {
        for (int year : originalParty.keySet()) {
            destinationParty.put(year, originalParty.get(year));
        }
    }
    public void copyParty(HashMap<Party, Integer> originalParty, HashMap<Party, Integer> workingParty) {
        for (Party precinctParty : originalParty.keySet()) {
            workingParty.put(precinctParty, originalParty.get(precinctParty));
        }
    }

    public boolean isValidConstraints() {
        if (!checkPreservedPrecinctConstraints()) {
            return false;
        }
        if (!checkNaturalBoundaryConstraints()) {
            return false;
        }
        if (!checkContiguityConstraints()) {
            return false;
        }
        return true;
    }

    private boolean checkNaturalBoundaryConstraints() {
        return true;
    }

    private boolean checkPreservedPrecinctConstraints() {
        return true;
    }

    private boolean checkContiguityConstraints() {
        return true;
    }
    
    public void initialStateByNumOfCDs( int NumOfCDs){
        Set<CDistrict> cds = this.getCongressionalDistricts();
        for (int a = 1; a <= NumOfCDs; a++) {
            CDistrict cd = new CDistrict();
            cd.setName("cd" + a);
            cds.add(cd);
        }
        
    }
}
