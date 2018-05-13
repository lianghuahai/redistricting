package pojo;

import java.io.ObjectOutputStream.PutField;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pojo.mapJson.Feature;
import utils.PropertyManager;

public class State {
    private final int MAXRUNTIME = Integer.parseInt((PropertyManager.getInstance().getValue("state.MAXRUNTIME")));
    private final double MINGOODNESS = Double.parseDouble((PropertyManager.getInstance().getValue("state.MINGOODNESS")));
//    private StateName name;
//    private HashMap<Integer, Party> winnerParty = new HashMap<Integer, Party>();
    private Set<CDistrict> congressionalDistricts = new HashSet<CDistrict>();
    private long population;
//    private HashMap<Race, Integer> race = new HashMap<Race, Integer>();
//    private HashMap<Party, Integer> votes = new HashMap<Party, Integer>();
    private HashMap<String, Integer> vote = new HashMap<String, Integer>();
    private Preference preference = new Preference();
    private int redistrictTimes;
    private float fairness;
    private double compactness;
    private double populationVariance;
    private double currentGoodness;
    private double efficiencyGap;
    private Preference constraints;
    private int stateId;
    private String sName;
    private float populationMean;
    private boolean isTerminated;
    private double aveIncome;
    private int numOfCds;
    private int numOfPrecincts;
    private double area;
    private int runningTimes;
    private double goodness; 
    private double racialFairness; 
    private double patisanFairness;
    public State(){
        super();
        vote.put("DEMOCRATIC", 0);
        vote.put("REPUBLICAN", 0);
    }
    public int getMAXRUNTIME() {
        return MAXRUNTIME;
    }

    public double getGoodness() {
        return goodness;
    }
    public void setGoodness(double goodness) {
        this.goodness = goodness;
    }
    public double getRacialFairness() {
        return racialFairness;
    }
    public void setRacialFairness(double racialFairness) {
        this.racialFairness = racialFairness;
    }
    public double getPatisanFairness() {
        return patisanFairness;
    }
    public void setPatisanFairness(double patisanFairness) {
        this.patisanFairness = patisanFairness;
    }
    public double getMINGOODNESS() {
        return MINGOODNESS;
    }

    public int getRunningTimes() {
        return runningTimes;
    }
    public void setRunningTimes(int runningTimes) {
        this.runningTimes = runningTimes;
    }
    public HashMap<String, Integer> getVote() {
        return vote;
    }
    public void setVote(HashMap<String, Integer> vote) {
        this.vote = vote;
    }
    public float getPopulationMean() {
        return populationMean;
    }
    public void setPopulationMean(float populationMean) {
        this.populationMean = populationMean;
    }
    // setter and getter
//    public StateName getName() {
//        return name;
//    }
    public String getsName() {
        return sName;
    }
    
    public double getAveIncome() {
        return aveIncome;
    }
    public void setAveIncome(double aveIncome) {
        this.aveIncome = aveIncome;
    }
    public int getNumOfCds() {
        return numOfCds;
    }
    public void setNumOfCds(int numOfCds) {
        this.numOfCds = numOfCds;
    }
    public int getNumOfPrecincts() {
        return numOfPrecincts;
    }
    public void setNumOfPrecincts(int numOfPrecincts) {
        this.numOfPrecincts = numOfPrecincts;
    }
    public double getArea() {
        return area;
    }
    public void setArea(double area) {
        this.area = area;
    }
    public boolean getIsTerminated() {
        return isTerminated;
    }
    public void setIsTerminated(boolean isTerminated) {
        this.isTerminated = isTerminated;
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
//    public void setName(StateName name) {
//        this.name = name;
//    }
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

//    public HashMap<Integer, Party> getWinnerParty() {
//        return winnerParty;
//    }
//
//    public void setWinnerParty(HashMap<Integer, Party> winnerParty) {
//        this.winnerParty = winnerParty;
//    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

//    public HashMap<Race, Integer> getRace() {
//        return race;
//    }
//
//    public void setRace(HashMap<Race, Integer> race) {
//        this.race = race;
//    }
//
//    public HashMap<Party, Integer> getVotes() {
//        return votes;
//    }
//
//    public void setVotes(HashMap<Party, Integer> votes) {
//        this.votes = votes;
//    }

    public int getRedistrictTimes() {
        return redistrictTimes;
    }

    public void setRedistrictTimes(int redistrictTimes) {
        this.redistrictTimes = redistrictTimes;
    }

    public double getFairness() {
        return fairness;
    }

    public void setFairness(float fairness) {
        this.fairness = fairness;
    }

    public double getCompactness() {
        return compactness;
    }

    public void setCompactness(double compactness) {
        this.compactness = compactness;
    }

    public double getPopulationVariance() {
        return populationVariance;
    }

    public void setPopulationVariance(double populationVariance) {
        this.populationVariance = populationVariance;
    }

    public double getCurrentGoodness() {
        return currentGoodness;
    }

    public void setCurrentGoodness(double currentGoodness) {
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
        CDistrict neighborCD    = startedPrecinct.getRandomNeighborCDistrict();
        if(this.tryMove(startedPrecinct,neighborCD)){
            System.out.println("move :"+redistrictTimes);
            return startedPrecinct;
        }else{
            System.out.println("cant move :"+redistrictTimes);
            return null;
        }
    }

    public Precinct selectStartPrecinct() {
        CDistrict cd  = this.getLowestGoodnessCDistrict();
        Precinct  startedPrecinct = cd.getRandomBoundaryPrecinct();
        return startedPrecinct;
    }

    public CDistrict getLowestGoodnessCDistrict() {
//        float minGoodness = Float.MAX_VALUE;
//        CDistrict cd = new CDistrict();
//        for (CDistrict cDistrict : congressionalDistricts) {
//            if (minGoodness > cDistrict.getCurrentGoodness()) {
//                minGoodness = cDistrict.getCurrentGoodness();
//                cd=cDistrict;
//            }
//        }
//        return cd;
        int length = this.congressionalDistricts.size();
        int index = (int) ((length) * Math.random());
        int i = 0;
        for (CDistrict cd : congressionalDistricts) {
            if (i == index) {
                return cd;
            }
            i++;
        }
        return null;
    }

    public boolean tryMove(Precinct selectedPrecinct,CDistrict destinationCD) {
        CDistrict originCD = selectedPrecinct.getCDistrict();
        originCD.removePrecinct(selectedPrecinct);
        destinationCD.addPrecinct(selectedPrecinct);
        selectedPrecinct.setCDistrict(destinationCD);
        selectedPrecinct.setOriginCDistrict(originCD);
        this.updateTwoCdsProperties(selectedPrecinct,originCD,destinationCD);
        if(!validateGoodnessImprovement(originCD,destinationCD)){
            destinationCD.removePrecinct(selectedPrecinct);
            originCD.addPrecinct(selectedPrecinct);
            selectedPrecinct.setCDistrict(originCD);
            this.updateTwoCdsProperties(selectedPrecinct,destinationCD,originCD);
            return false;
        }
        if (!isValidConstraints(selectedPrecinct)) {
            destinationCD.removePrecinct(selectedPrecinct);
            originCD.addPrecinct(selectedPrecinct);
            selectedPrecinct.setCDistrict(originCD);
            this.updateTwoCdsProperties(selectedPrecinct,destinationCD,originCD);
            return false;
        }
        this.setupGoodness();
        return true;
    }

    public void updateTwoCdsProperties(Precinct selectedPrecinct, CDistrict originCD, CDistrict destinationCD) {
        //population
        originCD.setPopulation(originCD.getPopulation()-selectedPrecinct.getPopulation());
        destinationCD.setPopulation(destinationCD.getPopulation()+selectedPrecinct.getPopulation());
        //color
        selectedPrecinct.getFeature().getProperties().setFill(destinationCD.getColor());
        //votes
        HashMap<String, Integer> originalVotes = originCD.getVote();
        HashMap<String, Integer> destinationVotes = destinationCD.getVote();
        HashMap<String, Integer> precinctVotes = selectedPrecinct.getVote();
//        if(precinctVotes==null ||precinctVotes.size()==0){
//            for (String key : precinctVotes.keySet()) {
//                originalVotes.put(key, originalVotes.get(key)-precinctVotes.get(key));
//                destinationVotes.put(key, destinationVotes.get(key)+precinctVotes.get(key));
//            }
//        }
        originalVotes.put("REPUBLICAN", originalVotes.get("REPUBLICAN")-precinctVotes.get("REPUBLICAN"));
        originalVotes.put("DEMOCRATIC", originalVotes.get("DEMOCRATIC")-precinctVotes.get("DEMOCRATIC"));
        destinationVotes.put("REPUBLICAN", destinationVotes.get("REPUBLICAN")+precinctVotes.get("REPUBLICAN"));
        destinationVotes.put("DEMOCRATIC", destinationVotes.get("DEMOCRATIC")+precinctVotes.get("DEMOCRATIC"));
        //racial
        //cd has white people : selectedPrecinct.getCDistrict().getCdInfor().getAmericanIndian()
        //updateRacial(selectedPrecinct,originCD,destinationCD);
        
    }
    private void updateRacial(Precinct selectedPrecinct, CDistrict originCD, CDistrict destinationCD) {
        int precinctRacial = (int) (selectedPrecinct.getPopulation()/selectedPrecinct.getCDistrict().getPopulation());
        int precinctWhitePeople = precinctRacial*selectedPrecinct.getCDistrict().getCdInfor().getWhite();
        int precinctAmericanIndianPeople = precinctRacial*selectedPrecinct.getCDistrict().getCdInfor().getAmericanIndian();
        int precinctOthersPeople = precinctRacial*selectedPrecinct.getCDistrict().getCdInfor().getOthers();
        int precinctBlackAfricanPeople = precinctRacial*selectedPrecinct.getCDistrict().getCdInfor().getBlackAfrican();
        int precinctAsianPeople = precinctRacial*selectedPrecinct.getCDistrict().getCdInfor().getAsian();
        int precinctFemalePeople = precinctRacial*selectedPrecinct.getCDistrict().getCdInfor().getFemale();
        int precinctMalePeople = precinctRacial*selectedPrecinct.getCDistrict().getCdInfor().getMale();
        
        originCD.getCdInfor().setWhite(originCD.getCdInfor().getWhite()-precinctWhitePeople);
        originCD.getCdInfor().setAmericanIndian(originCD.getCdInfor().getAmericanIndian()-precinctAmericanIndianPeople);
        originCD.getCdInfor().setOthers(originCD.getCdInfor().getOthers()-precinctOthersPeople);
        originCD.getCdInfor().setBlackAfrican(originCD.getCdInfor().getBlackAfrican()-precinctBlackAfricanPeople);
        originCD.getCdInfor().setAsian(originCD.getCdInfor().getAsian()-precinctAsianPeople);
        originCD.getCdInfor().setFemale(originCD.getCdInfor().getFemale()-precinctFemalePeople);
        originCD.getCdInfor().setMale(originCD.getCdInfor().getMale()-precinctMalePeople);
        
        destinationCD.getCdInfor().setWhite(destinationCD.getCdInfor().getWhite()-precinctWhitePeople);
        destinationCD.getCdInfor().setAmericanIndian(destinationCD.getCdInfor().getAmericanIndian()-precinctAmericanIndianPeople);
        destinationCD.getCdInfor().setOthers(destinationCD.getCdInfor().getOthers()-precinctOthersPeople);
        destinationCD.getCdInfor().setBlackAfrican(destinationCD.getCdInfor().getBlackAfrican()-precinctBlackAfricanPeople);
        destinationCD.getCdInfor().setAsian(destinationCD.getCdInfor().getAsian()-precinctAsianPeople);
        destinationCD.getCdInfor().setFemale(destinationCD.getCdInfor().getFemale()-precinctFemalePeople);
        destinationCD.getCdInfor().setMale(destinationCD.getCdInfor().getMale()-precinctMalePeople);
    }
    public boolean validateGoodnessImprovement(CDistrict originCD, CDistrict destinationCD) {
        double newGoodnessOCD = originCD.calculateObjectiveFunction();
        double newGoodnessDCD = destinationCD.calculateObjectiveFunction();
        double goodnessDiff= originCD.getGoodnessDiff(newGoodnessOCD) + destinationCD.getGoodnessDiff(newGoodnessDCD);
        if(goodnessDiff > 0){
            originCD.setCurrentGoodness(newGoodnessOCD);
            destinationCD.setCurrentGoodness(newGoodnessDCD);
            return true;
        }
        return false;
    }

    public boolean checkTermination() {
        if (this.checkGoodness() || this.checkRedistrictTimes()) {
            //this.isTerminated=true;
            return true;
        }
        return false;
    }

    public boolean checkGoodness() {
        return (currentGoodness > MINGOODNESS);
    }

    public boolean checkRedistrictTimes() {
        return (redistrictTimes >= MAXRUNTIME);
    }

    public State clone(Preference preference) {
        State workingState = new State();
        workingState.setPopulation(this.population);
        workingState.setsName(this.getsName());
        workingState.setPopulationMean(this.getPopulation()/this.getCongressionalDistricts().size());
        workingState.setEfficiencyGap(this.efficiencyGap);
        workingState.setFairness(this.fairness);
        workingState.setCurrentGoodness(this.currentGoodness);
        workingState.setPreference(preference);
        workingState.setNumOfCds(this.numOfCds);
        workingState.setAveIncome(this.aveIncome);
        workingState.setNumOfPrecincts(this.numOfPrecincts);
        workingState.setArea(this.area);
        workingState.setCompactness(this.compactness);
        workingState.setPopulationVariance(this.populationVariance);
        workingState.setPatisanFairness(this.patisanFairness);
        workingState.setRacialFairness(this.racialFairness);
//        this.copyWinnerParty(this.winnerParty, workingState.getWinnerParty());
//        this.copyRace(this.race, workingState.getRace());
        this.copyParty(this.vote, workingState.getVote());
        this.copyCDistricts(this.congressionalDistricts,workingState.getCongressionalDistricts(),workingState);
        return workingState;
    }

    public void copyCDistricts(Set<CDistrict> originalCDs,Set<CDistrict> destinationCDs,State workingState) {
        for (CDistrict originalCD : originalCDs) {
            CDistrict destinationCD = new CDistrict();
            destinationCD.setState(workingState);
            destinationCD.setColor(originalCD.getColor());
            destinationCD.setName(originalCD.getName());
            destinationCD.setPopulation(originalCD.getPopulation());
            destinationCD.setCurrentGoodness(originalCD.getCurrentGoodness());
//            this.copyWinnerParty(winnerParty, destinationCD.getWinnerParty());
            //this.copyRace(originalCD.getRace(), destinationCD.getRace());
            this.copyParty(originalCD.getVote(), destinationCD.getVote());
            this.copyPrecincts(originalCD.getPrecinct(),destinationCD.getPrecinct(),destinationCD,workingState);
            //copy cdInfo
            originalCD.getCdInfor().setMale(destinationCD.getCdInfor().getMale());
            originalCD.getCdInfor().setFemale(destinationCD.getCdInfor().getFemale());
            originalCD.getCdInfor().setWhite(destinationCD.getCdInfor().getWhite());
            originalCD.getCdInfor().setBlackAfrican(destinationCD.getCdInfor().getBlackAfrican());
            originalCD.getCdInfor().setAsian(destinationCD.getCdInfor().getAsian());
            originalCD.getCdInfor().setAmericanIndian(destinationCD.getCdInfor().getAmericanIndian());
            originalCD.getCdInfor().setOthers(destinationCD.getCdInfor().getOthers());
            originalCD.getCdInfor().setHouseHoldAvg(destinationCD.getCdInfor().getHouseHoldAvg());
            originalCD.getCdInfor().setFamilyAvg(destinationCD.getCdInfor().getFamilyAvg());
            originalCD.getCdInfor().setTotalHouseHold(destinationCD.getCdInfor().getTotalHouseHold());
            originalCD.getCdInfor().setSchoolEnroll(destinationCD.getCdInfor().getSchoolEnroll());
            originalCD.getCdInfor().setEmployees(destinationCD.getCdInfor().getEmployees());
            //ToDo  - map: Set<MapData>
            //ToDo - boundaryPrecincts: Set<Precinct>
            destinationCDs.add(destinationCD);
        }
        workingState.setCongressionalDistricts(destinationCDs);
    }

    public void copyPrecincts(Set<Precinct> originalPrecincts, Set<Precinct> destinationPrecincts,
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
            workingP.setCounty(originalP.getCounty());
//            this.copyRace(originalP.getRace(), workingP.getRace());
            this.copyParty(originalP.getVote(), workingP.getVote());
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
    public void copyParty(HashMap<String, Integer> originalParty, HashMap<String, Integer> workingParty) {
        if(originalParty!=null){
            for (String key : originalParty.keySet()) {
                workingParty.put(key,originalParty.get(key));
            }
        }
    }

    public boolean isValidConstraints(Precinct selectedPrecinct) {
        if (!checkContiguityConstraints(selectedPrecinct)) {
            return false;
        }
        if(this.getPreference().getIsContiguity()||this.getsName().equals("SC")){
            System.out.println("**********************");
            if (!checkCountyContiguity(selectedPrecinct)) {
                return false;
            }
        }
        if(this.getPreference().getIsNaturalBoundary()){
            if (!checkNaturalBoundaryConstraints()) {
                return false;
            }
        }
        if (!checkPreservedPrecinctConstraints()) {
            return false;
        }
        return true;
    }

    private boolean checkCountyContiguity(Precinct selectedPrecinct) {
        // return false  就是不能移动
        Set<Precinct> neighborPrecincts = selectedPrecinct.getNeighborPrecincts();
        for (Precinct precinct : neighborPrecincts) {
            if(precinct.getCounty().equals(selectedPrecinct.getCounty())){
                if(precinct.getCdistrictId()!=selectedPrecinct.getCdistrictId()){
                    return false;
                }
            }
        }
        return true;
    }
    public boolean checkNaturalBoundaryConstraints() {
        return true;
    }

    public boolean checkPreservedPrecinctConstraints() {
        return true;
    }

    public boolean checkContiguityConstraints(Precinct selectedPrecinct) {
        Set<Precinct> neighborPrecincts = selectedPrecinct.getNeighborPrecincts();
        if(neighborPrecincts==null||neighborPrecincts.size()==0){
            return false;
        }
        //moving precinct  neighbors
        for (Precinct precinct : neighborPrecincts) {
            Set<Precinct> neighborPrecincts2 = precinct.getNeighborPrecincts();
            if(neighborPrecincts2==null||neighborPrecincts2.size()==0){
                return false;
            }
//                boolean flag = true;
                  int flag = 0;
                //there is at least one precinct has the same color as moving precinct
                for (Precinct precinct2 : neighborPrecincts2) {
                    if(precinct2.getFeature().getProperties().getFill().equals(precinct.getFeature().getProperties().getFill())){
//                        flag=false;
                        flag++;
                        //break;
                    }
                }
                if(flag<=1){
                    return false;
                }
//            }
        }
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
    public void setUpSCCdMapJson(Set<Feature> features) {
        CDistrict cd1 = getCdByName("cd1");
        CDistrict cd2 = getCdByName("cd2");
        CDistrict cd3 = getCdByName("cd3");
        CDistrict cd4 = getCdByName("cd4");
        CDistrict cd5 = getCdByName("cd5");
        CDistrict cd6 = getCdByName("cd6");
        CDistrict cd7 = getCdByName("cd7");
        CDistrict cd = new CDistrict();
        for (Feature feature : features) {
            if(feature.getProperties().getCD115FP().equals("01")){
                cd=cd1;
            }else if(feature.getProperties().getCD115FP().equals("02")){
                cd=cd2;
            }else if(feature.getProperties().getCD115FP().equals("03")){
                cd=cd3;
            }else if(feature.getProperties().getCD115FP().equals("04")){
                cd=cd4;
            }else if(feature.getProperties().getCD115FP().equals("05")){
                cd=cd5;
            }else if(feature.getProperties().getCD115FP().equals("06")){
                cd=cd6;
            }else{
                cd=cd7;
            }
            //cdinfor
            feature.getProperties().setMale(cd.getCdInfor().getMale());
            feature.getProperties().setFemale(cd.getCdInfor().getFemale());
            feature.getProperties().setWhite(cd.getCdInfor().getWhite());
            feature.getProperties().setBlackAfrican(cd.getCdInfor().getBlackAfrican());
            feature.getProperties().setAsian(cd.getCdInfor().getAsian());
            feature.getProperties().setAmericanIndian(cd.getCdInfor().getAmericanIndian());
            feature.getProperties().setOthers(cd.getCdInfor().getOthers());
            feature.getProperties().setHouseHoldAvg(cd.getCdInfor().getHouseHoldAvg());
            feature.getProperties().setFamilyAvg(cd.getCdInfor().getFamilyAvg());
            feature.getProperties().setTotalHouseHold(cd.getCdInfor().getTotalHouseHold());
            feature.getProperties().setSchoolEnroll(cd.getCdInfor().getSchoolEnroll());
            feature.getProperties().setEmployees(cd.getCdInfor().getEmployees());
            //
            feature.getProperties().setPOPULATION(cd.getPopulation());
            feature.getProperties().setFill(PropertyManager.getInstance().getValue("color"+feature.getProperties().getCD115FP()));
            feature.getProperties().setRVOTES(cd.getVote().get("REPUBLICAN"));
            feature.getProperties().setDVOTES(cd.getVote().get("DEMOCRATIC"));
        }
    }
    public void setupBoundaryPrecincts() {
        for (CDistrict cDistrict : this.congressionalDistricts) {
            cDistrict.setupBoundaryPrecincts();
        }
        
    }
    public void initialStateByNumOfCDs( int NumOfCDs){
        Set<CDistrict> cds = this.getCongressionalDistricts();
        for (int a = 1; a <= NumOfCDs; a++) {
            CDistrict cd = new CDistrict();
            cd.setName("cd" + a);
            cd.setCdCode(a+9);
            cd.setStateId(3);
            cds.add(cd);
        }
        
    }
    public void setUpCdMapJson(Set<Feature> features) {
        CDistrict cd1 = getCdByName("cd1");
        CDistrict cd2 = getCdByName("cd2");
        for (Feature feature : features) {
            if(feature.getProperties().getCD115FP().equals("01")){
                //cdinfor
                feature.getProperties().setMale(cd1.getCdInfor().getMale());
                feature.getProperties().setFemale(cd1.getCdInfor().getFemale());
                feature.getProperties().setWhite(cd1.getCdInfor().getWhite());
                feature.getProperties().setBlackAfrican(cd1.getCdInfor().getBlackAfrican());
                feature.getProperties().setAsian(cd1.getCdInfor().getAsian());
                feature.getProperties().setAmericanIndian(cd1.getCdInfor().getAmericanIndian());
                feature.getProperties().setOthers(cd1.getCdInfor().getOthers());
                feature.getProperties().setHouseHoldAvg(cd1.getCdInfor().getHouseHoldAvg());
                feature.getProperties().setFamilyAvg(cd1.getCdInfor().getFamilyAvg());
                feature.getProperties().setTotalHouseHold(cd1.getCdInfor().getTotalHouseHold());
                feature.getProperties().setSchoolEnroll(cd1.getCdInfor().getSchoolEnroll());
                feature.getProperties().setEmployees(cd1.getCdInfor().getEmployees());
                //
                feature.getProperties().setPOPULATION(cd1.getPopulation());
                feature.getProperties().setFill(PropertyManager.getInstance().getValue("color"+feature.getProperties().getCD115FP()));
                feature.getProperties().setRVOTES(cd1.getVote().get("REPUBLICAN"));
                feature.getProperties().setDVOTES(cd1.getVote().get("DEMOCRATIC"));
            }else{
                feature.getProperties().setMale(cd2.getCdInfor().getMale());
                feature.getProperties().setFemale(cd2.getCdInfor().getFemale());
                feature.getProperties().setWhite(cd2.getCdInfor().getWhite());
                feature.getProperties().setBlackAfrican(cd2.getCdInfor().getBlackAfrican());
                feature.getProperties().setAsian(cd2.getCdInfor().getAsian());
                feature.getProperties().setAmericanIndian(cd2.getCdInfor().getAmericanIndian());
                feature.getProperties().setOthers(cd2.getCdInfor().getOthers());
                feature.getProperties().setHouseHoldAvg(cd2.getCdInfor().getHouseHoldAvg());
                feature.getProperties().setFamilyAvg(cd2.getCdInfor().getFamilyAvg());
                feature.getProperties().setTotalHouseHold(cd2.getCdInfor().getTotalHouseHold());
                feature.getProperties().setSchoolEnroll(cd2.getCdInfor().getSchoolEnroll());
                feature.getProperties().setEmployees(cd2.getCdInfor().getEmployees());
                //
                feature.getProperties().setPOPULATION(cd2.getPopulation());
                feature.getProperties().setFill(PropertyManager.getInstance().getValue("color"+feature.getProperties().getCD115FP()));
                feature.getProperties().setRVOTES(cd2.getVote().get("REPUBLICAN"));
                feature.getProperties().setDVOTES(cd2.getVote().get("DEMOCRATIC"));
            }
        }
    }
    public void setUpCoroladoCdMapJson(Set<Feature> features) {
        CDistrict cd1 = getCdByName("cd1");
        CDistrict cd2 = getCdByName("cd2");
        CDistrict cd3 = getCdByName("cd3");
        CDistrict cd4 = getCdByName("cd4");
        CDistrict cd5 = getCdByName("cd5");
        CDistrict cd6 = getCdByName("cd6");
        CDistrict cd7 = getCdByName("cd7");
        CDistrict cd = new CDistrict();
        for (Feature feature : features) {
            if(feature.getProperties().getCD115FP().equals("01")){
                cd=cd1;
            }else if(feature.getProperties().getCD115FP().equals("02")){
                cd=cd2;
            }else if(feature.getProperties().getCD115FP().equals("03")){
                cd=cd3;
            }else if(feature.getProperties().getCD115FP().equals("04")){
                cd=cd4;
            }else if(feature.getProperties().getCD115FP().equals("05")){
                cd=cd5;
            }else if(feature.getProperties().getCD115FP().equals("06")){
                cd=cd6;
            }else{
                cd=cd7;
            }
            //cdinfor
            feature.getProperties().setMale(cd.getCdInfor().getMale());
            feature.getProperties().setFemale(cd.getCdInfor().getFemale());
            feature.getProperties().setWhite(cd.getCdInfor().getWhite());
            feature.getProperties().setBlackAfrican(cd.getCdInfor().getBlackAfrican());
            feature.getProperties().setAsian(cd.getCdInfor().getAsian());
            feature.getProperties().setAmericanIndian(cd.getCdInfor().getAmericanIndian());
            feature.getProperties().setOthers(cd.getCdInfor().getOthers());
            feature.getProperties().setHouseHoldAvg(cd.getCdInfor().getHouseHoldAvg());
            feature.getProperties().setFamilyAvg(cd.getCdInfor().getFamilyAvg());
            feature.getProperties().setTotalHouseHold(cd.getCdInfor().getTotalHouseHold());
            feature.getProperties().setSchoolEnroll(cd.getCdInfor().getSchoolEnroll());
            feature.getProperties().setEmployees(cd.getCdInfor().getEmployees());
            //
            feature.getProperties().setPOPULATION(cd.getPopulation());
            feature.getProperties().setFill(PropertyManager.getInstance().getValue("color"+feature.getProperties().getCD115FP()));
            feature.getProperties().setRVOTES(cd.getVote().get("REPUBLICAN"));
            feature.getProperties().setDVOTES(cd.getVote().get("DEMOCRATIC"));
        }
    }
    public  CDistrict getCdByName(String name) {
        for (CDistrict cDistrict : this.congressionalDistricts) {
            if (cDistrict.getName().equals(name)) {
                return cDistrict;
            }
        }
        return null;
    }
    public void setupGoodness() {
        Set<CDistrict> cds = this.getCongressionalDistricts();
        this.currentGoodness=0;
        this.patisanFairness=0;
        this.racialFairness=0;
        this.populationVariance=0;
        this.compactness =0;
        for (CDistrict cDistrict : cds) {
            cDistrict.setCurrentGoodness(cDistrict.calculateObjectiveFunction());
            this.currentGoodness = this.currentGoodness +cDistrict.getCurrentGoodness();
            this.patisanFairness = this.patisanFairness + cDistrict.getPartisanFairness();
            this.racialFairness = this.racialFairness + cDistrict.getRacialFairness();
            this.populationVariance = this.populationVariance +  cDistrict.getPopulationVariance();
            this.compactness = this.compactness + cDistrict.getCompactness();
        }
        this.currentGoodness=this.currentGoodness/this.congressionalDistricts.size();
        this.patisanFairness=this.patisanFairness/this.congressionalDistricts.size();
        this.racialFairness=this.racialFairness/this.congressionalDistricts.size();
        this.populationVariance=this.populationVariance/this.congressionalDistricts.size();
        this.compactness = this.compactness/this.congressionalDistricts.size();
    }
    
}
