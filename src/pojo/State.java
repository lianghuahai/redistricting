package pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class State {
    private final int MAXRUNTIME = 1000;
    private final double MINGOODNESS = 0.8;
    private StateName name;
    private HashMap<Integer, Party> winnerParty;
    private Set<CDistrict> congressionalDistricts = new HashSet<CDistrict>();
    private long population;
    private HashMap<Race, Integer> race = new HashMap<Race, Integer>();
    private HashMap<Party, Integer> votes = new HashMap<Party, Integer>();
    private int redistrictTimes = 0;
    private float fairness;
    private float compactness;
    private float populationVariance;
    private float currentGoodness;
    private double efficiencyGap;
    private Constraints constraints;
    private HashMap<ObjectElement, Integer> preference = new HashMap<ObjectElement, Integer>();

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
            Precinct  startPrecinct = this.selectStartPrecinct();
            CDistrict neighborCD    = startPrecinct.getRandomNeighborCDistrict();
            this.tryMove(startPrecinct,neighborCD);
        }
    }

    public Precinct selectStartPrecinct() {
        CDistrict cd  = this.getLowestGoodnessCDistrict();
        Precinct  startPrecinct = cd.getRandomBoundaryPrecinct();
        return startPrecinct;
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

    public boolean tryMove(Precinct selectPrecinct,CDistrict destinationCD) {
        CDistrict originCD = selectPrecinct.getCDistrict();
        this.moveToDestinationCD(selectPrecinct, destinationCD);
        this.updateData(selectPrecinct, originCD, destinationCD);
        if (!isValidConstraints()) {
            moveToDestinationCD(selectPrecinct, originCD);
            updateData(selectPrecinct, destinationCD, originCD);
            return false;
        }
        float goodness = calculateObjectiveFunction();
        if (goodness < currentGoodness) {
            this.moveToDestinationCD(selectPrecinct, originCD);
            this.updateData(selectPrecinct, destinationCD, originCD);
            return false;
        }
        this.setCurrentGoodness(goodness);
        return true;
    }

    private void updateData(Precinct selectPrecinct, CDistrict originCD, CDistrict destinationCD) {
        originCD.setPopulation(originCD.getPopulation() - selectPrecinct.getPopulation());
        destinationCD.setPopulation(originCD.getPopulation() + selectPrecinct.getPopulation());
        this.modifyVotes(selectPrecinct, originCD, destinationCD);
        this.modifyRace(selectPrecinct, originCD, destinationCD);
        this.modifyPopulation(selectPrecinct, originCD, destinationCD);
    }

    private void modifyVotes(Precinct selectPrecinct, CDistrict originCD, CDistrict destinationCD) {
        HashMap<Party, Integer> precinctVotes = selectPrecinct.getVotes();
        HashMap<Party, Integer> originCDVotes = originCD.getVotes();
        HashMap<Party, Integer> destinationCDVotes = destinationCD.getVotes();
        for (Party p : originCDVotes.keySet()) {
            precinctVotes.put(p, originCDVotes.get(p) - precinctVotes.get(p));
        }
        for (Party p : destinationCDVotes.keySet()) {
            precinctVotes.put(p, destinationCDVotes.get(p) + precinctVotes.get(p));
        }
    }

    private void modifyPopulation(Precinct selectPrecinct, CDistrict originCD, CDistrict destinationCD) {
        originCD.setPopulation(originCD.getPopulation() - selectPrecinct.getPopulation());
        destinationCD.setPopulation(destinationCD.getPopulation() + selectPrecinct.getPopulation());
    }

    private void modifyRace(Precinct selectPrecinct, CDistrict originCD, CDistrict destinationCD) {
        // TODO Auto-generated method stub
    }

    private void moveToDestinationCD(Precinct selectPrecinct, CDistrict destinationCD) {
        CDistrict originCD = selectPrecinct.getCDistrict();
        originCD.getBoundaryPrecincts().remove(selectPrecinct);
        originCD.getPrecinct().remove(selectPrecinct);
        selectPrecinct.getNeighborCDistrictList().add(originCD);
        originCD = destinationCD;
        destinationCD.getBoundaryPrecincts().add(selectPrecinct);
        destinationCD.getPrecinct().add(selectPrecinct);
        selectPrecinct.getNeighborCDistrictList().remove(destinationCD);
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
        //this.copyCDistricts(congressionalDistricts,workingState.getCongressionalDistricts());
        return workingState;
    }

    private void copyCDistricts(List<CDistrict> originalCDs,List<CDistrict> destinationCDs) {
        for (CDistrict originalCD : originalCDs) {
            CDistrict destinationCD = new CDistrict();
            destinationCD.setName(originalCD.getName());
            destinationCD.setState(originalCD.getState());
            destinationCD.setPopulation(originalCD.getPopulation());
            destinationCD.setCurrentGoodness(originalCD.getCurrentGoodness());
            this.copyWinnerParty(winnerParty, destinationCD.getWinnerParty());
            this.copyRace(originalCD.getRace(), destinationCD.getRace());
            this.copyParty(originalCD.getVotes(), destinationCD.getVotes());
            //this.copyPrecincts(originalCD.getPrecinct(),destinationCD.getPrecinct(),destinationCD);
            //ToDo  - map: Set<MapData>
            //ToDo - boundaryPrecincts: Set<Precinct>
            destinationCDs.add(destinationCD);
        }
        
    }

    private void copyPrecincts(List<Precinct> originalPrecincts, List<Precinct> destinationPrecincts,
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

    public float calculateObjectiveFunction() {
        float compactness = this.calculateCompactness();
        float populationVariance = this.caculatePV();
        float partisanFairness = this.caculatePartisanFairness();
        float racialFairness = this.caculateRacialFairness();
        float goodness = this.caculateGoodness(compactness, populationVariance, partisanFairness, racialFairness);
        return goodness;
    }

    private float caculateGoodness(float compactness, float populationVariance, float partisanFairness,
            float racialFairness) {
        float compactnessWeight = preference.get(ObjectElement.COMPACTNESSWEIGHT);
        float populationVarianceWeight = preference.get(ObjectElement.POPULATIONVARIANCEWEIGHT);
        float racialFairnessWeight = preference.get(ObjectElement.RACIALFAIRNESSWEIGHT);
        float partisanFairnessWeight = preference.get(ObjectElement.PARTISANFAIRNESSWEIGHT);
        // what is formula for goodness?
        return 0;
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
}
