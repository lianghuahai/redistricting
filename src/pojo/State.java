package pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pojo.mapJson.Feature;
import utils.PropertyManager;

public class State {
    private final int MAXRUNTIME = Integer.parseInt((PropertyManager.getInstance().getValue("state.MAXRUNTIME")));
    private final double MINGOODNESS = Double.parseDouble((PropertyManager.getInstance().getValue("state.MINGOODNESS")));
    private StateName name;
    private HashMap<Integer, Party> winnerParty = new HashMap<Integer, Party>();
    private Set<CDistrict> congressionalDistricts = new HashSet<CDistrict>();
    private long population;
    private HashMap<Race, Integer> race = new HashMap<Race, Integer>();
    private HashMap<Party, Integer> votes = new HashMap<Party, Integer>();
    private Preference preference = new Preference();
    private int redistrictTimes;
    private float fairness;
    private float compactness;
    private float populationVariance;
    private float currentGoodness;
    private double efficiencyGap;
    private Preference constraints;
    private int stateId;
    private String sName;
    private float populationMean;
    
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

    public float getPopulationMean() {
        return populationMean;
    }
    public void setPopulationMean(float populationMean) {
        this.populationMean = populationMean;
    }
    // setter and getter
    public StateName getName() {
        return name;
    }
    public String getsName() {
        return sName;
    }
    public void setsName(String sName) {
        this.sName = sName;
    }
    public int getStateId() {
        return stateId;
    }
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }
    public void setName(StateName name) {
        this.name = name;
    }
    public Preference getPreference() {
        return preference;
    }
    public void setPreference(Preference preference) {
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

    public Preference getConstraints() {
        return constraints;
    }

    public void setConstraints(Preference constraints) {
        this.constraints = constraints;
    }

    // methods to be implemented
    public Precinct startAlgorithm() {
        this.redistrictTimes++;
        Precinct  startedPrecinct = this.selectStartPrecinct();
        System.out.println(startedPrecinct.getName()+","+startedPrecinct.getCDistrict().getName());
        CDistrict neighborCD    = startedPrecinct.getRandomNeighborCDistrict();
        if(this.tryMove(startedPrecinct,neighborCD)){
            System.out.println("move");
            return startedPrecinct;
        }else{
            System.out.println("no move");
            return null;
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
        System.out.println(newGoodnessOCD);
        System.out.println(newGoodnessDCD);
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
        workingState.setPopulation(this.population);
        workingState.setName(this.name);
        workingState.setPopulationMean(this.getPopulation()/this.getCongressionalDistricts().size());
        workingState.setEfficiencyGap(this.efficiencyGap);
        workingState.setFairness(this.fairness);
        workingState.setCompactness(this.compactness);
        workingState.setPopulationVariance(this.populationVariance);
        workingState.setCurrentGoodness(this.currentGoodness);
        this.copyWinnerParty(this.winnerParty, workingState.getWinnerParty());
        this.copyRace(this.race, workingState.getRace());
        this.copyParty(this.votes, workingState.getVotes());
        this.copyCDistricts(this.congressionalDistricts,workingState.getCongressionalDistricts(),workingState);
        return workingState;
    }

    private void copyCDistricts(Set<CDistrict> originalCDs,Set<CDistrict> destinationCDs,State workingState) {
        for (CDistrict originalCD : originalCDs) {
            CDistrict destinationCD = new CDistrict();
            destinationCD.setState(workingState);
            destinationCD.setName(originalCD.getName());
            destinationCD.setPopulation(originalCD.getPopulation());
            destinationCD.setCurrentGoodness(originalCD.getCurrentGoodness());
            this.copyWinnerParty(winnerParty, destinationCD.getWinnerParty());
            this.copyRace(originalCD.getRace(), destinationCD.getRace());
            this.copyParty(originalCD.getVotes(), destinationCD.getVotes());
            this.copyPrecincts(originalCD.getPrecinct(),destinationCD.getPrecinct(),destinationCD,workingState);
            //ToDo  - map: Set<MapData>
            //ToDo - boundaryPrecincts: Set<Precinct>
            destinationCDs.add(destinationCD);
        }
        workingState.setCongressionalDistricts(destinationCDs);
    }

    private void copyPrecincts(Set<Precinct> originalPrecincts, Set<Precinct> destinationPrecincts,
            CDistrict destinationCD,State workingState) {
        for (Precinct originalP : originalPrecincts) {
            Precinct workingP = new Precinct();
            workingP.setName(originalP.getName());
            workingP.setState(workingState);
            workingP.setPrecinctCode(originalP.getPrecinctCode());
            workingP.setRegisteredVoters(originalP.getRegisteredVoters());
            workingP.setTotalVoters(originalP.getTotalVoters());
            workingP.setState(originalP.getState()); 
            workingP.setPopulation(originalP.getPopulation());
            workingP.setIsBorder(originalP.getIsBorder());
            workingP.setIsFixed(originalP.getIsFixed());
            workingP.setCDistrict(destinationCD);
            workingP.setMap(originalP.getMap());
            workingP.setFeature(originalP.getFeature());
            this.copyRace(originalP.getRace(), workingP.getRace());
            this.copyParty(originalP.getVotes(), workingP.getVotes());
            //Todo - neighborPrecinctList: Set<Precinct>
            //Todo - neighborCDistrictList: Set<CDistrict>
            destinationPrecincts.add(workingP);
        }
        
    }

    public void copyRace(HashMap<Race, Integer> originalRace, HashMap<Race, Integer> workingRace) {
        if(originalRace!=null){
            for (Race precinctRace : originalRace.keySet()) {
                workingRace.put(precinctRace, originalRace.get(precinctRace));
            }
        }
    }
    public void copyWinnerParty(HashMap<Integer, Party> originalParty, HashMap<Integer, Party> destinationParty) {
        if(originalParty!=null){
            for (int year : originalParty.keySet()) {
                destinationParty.put(year, originalParty.get(year));
            }
        }
    }
    public void copyParty(HashMap<Party, Integer> originalParty, HashMap<Party, Integer> workingParty) {
        if(originalParty!=null){
            for (Party precinctParty : originalParty.keySet()) {
                workingParty.put(precinctParty, originalParty.get(precinctParty));
            }
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
    public void setupNeighbors(Precinct targetPrecinct,List<String> neighbors) {
        Set<CDistrict> cds = this.getCongressionalDistricts();
        for (String neighborPrecinctCode : neighbors) {
            for (CDistrict cDistrict : cds) {
                if(cDistrict.foundNeighborPrecinct(targetPrecinct,neighborPrecinctCode)){
                    break;
                }
            }
        }
    }
    
    public void initialStateByNumOfCDs( int NumOfCDs){
        Set<CDistrict> cds = this.getCongressionalDistricts();
        for (int a = 1; a <= NumOfCDs; a++) {
            CDistrict cd = new CDistrict();
            cd.setName("cd" + a);
            cds.add(cd);
        }
        
    }
    public void setUpCdMapJson(Set<Feature> features) {
        CDistrict cd1 = getCdByName("cd1");
        CDistrict cd2 = getCdByName("cd2");
        for (Feature feature : features) {
            if(feature.getProperties().getCD115FP().equals("01")){
                feature.getProperties().setPOPULATION(cd1.getPopulation());
                feature.getProperties().setFill(PropertyManager.getInstance().getValue("color"+feature.getProperties().getCD115FP()));
                feature.getProperties().setRVOTES(cd1.getVotes().get(Party.REPUBLICAN));
                feature.getProperties().setDVOTES(cd1.getVotes().get(Party.DEMOCRATIC));
            }else{
                feature.getProperties().setPOPULATION(cd2.getPopulation());
                feature.getProperties().setFill(PropertyManager.getInstance().getValue("color"+feature.getProperties().getCD115FP()));
                feature.getProperties().setRVOTES(cd2.getVotes().get(Party.REPUBLICAN));
                feature.getProperties().setDVOTES(cd2.getVotes().get(Party.DEMOCRATIC));
            }
        }
    }
    private  CDistrict getCdByName(String name) {
        for (CDistrict cDistrict : this.congressionalDistricts) {
            if (cDistrict.getName().equals(name)) {
                return cDistrict;
            }
        }
        return null;
    }
    
    
}
