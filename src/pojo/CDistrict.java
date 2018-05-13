package pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pojo.mapJson.Feature;
import pojo.mapJson.Geometry;
import pojo.mapJson.PrecinctJson;
import utils.LoadJsonData;
import utils.PropertyManager;

public class CDistrict {
    private String name;
    private transient State state = new State();
    
    private  CDInfor cdInfor =  new CDInfor();
//    private HashMap<Integer, Party> winnerParty = new HashMap<Integer, Party>();

    private  Set<MapData> map = new HashSet<MapData>();

    private   Set<Precinct> precinct = new HashSet<Precinct>();

    private long population;

//    private HashMap<Race, Integer> race = new HashMap<Race, Integer>();
//
//    private HashMap<Party, Integer> votes = new HashMap<Party, Integer>();
    private   HashMap<String, Integer> vote = new HashMap<String, Integer>();

    private  Set<Precinct> boundaryPrecincts = new HashSet<Precinct>();

    private double currentGoodness;

    private  Feature feature = new Feature();

    private int cdCode;

    private int registerVoters;

    private int totalVoters;
    
    private double compactness;
    private double partisanFairness;
    private double populationVariance;
    private double racialFairness;
    public CDInfor getCdInfor() {
        return cdInfor;
    }

    public void setCdInfor(CDInfor cdInfor) {
        this.cdInfor = cdInfor;
    }
    // setter and getter
    private int stateId;

    private String color;

    public String getName() {
        return name;
    }

    public double getCompactness() {
        return compactness;
    }

    public void setCompactness(double compactness) {
        this.compactness = compactness;
    }

    public double getPartisanFairness() {
        return partisanFairness;
    }

    public void setPartisanFairness(double partisanFairness) {
        this.partisanFairness = partisanFairness;
    }

    public double getPopulationVariance() {
        return populationVariance;
    }

    public void setPopulationVariance(double populationVariance) {
        this.populationVariance = populationVariance;
    }

    public double getRacialFairness() {
        return racialFairness;
    }

    public void setRacialFairness(double racialFairness) {
        this.racialFairness = racialFairness;
    }

    public CDistrict() {
        super();
        vote.put("DEMOCRATIC", 0);
        vote.put("REPUBLICAN", 0);
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStateId() {
        return stateId;
    }

//    public CdInformation getCdInformation() {
//        return cdInformation;
//    }
//
//    public void setCdInformation(CdInformation cdInformation) {
//        this.cdInformation = cdInformation;
//    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public State getState() {
        return state;
    }

    public String getColor() {
        return color;
    }

    public HashMap<String, Integer> getVote() {
        return vote;
    }

    public void setVote(HashMap<String, Integer> vote) {
        this.vote = vote;
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

//    public HashMap<Integer, Party> getWinnerParty() {
//        return winnerParty;
//    }
//
//    public void setWinnerParty(HashMap<Integer, Party> winnerParty) {
//        this.winnerParty = winnerParty;
//    }

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

    public Set<Precinct> getBoundaryPrecincts() {
        return boundaryPrecincts;
    }

   

    public void setBoundaryPrecincts(Set<Precinct> boundaryPrecincts) {
        this.boundaryPrecincts = boundaryPrecincts;
    }

    public double getCurrentGoodness() {
        return currentGoodness;
    }

    public void setCurrentGoodness(double currentGoodness) {
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

    public double calculateObjectiveFunction() {
        double goodness = 0;
        Preference preference = this.getState().getPreference();
        double totalWeight=preference.getCOMPACTNESSWEIGHT()
                +preference.getPARTISANFAIRNESSWEIGHT()
                +preference.getPOPULATIONVARIANCEWEIGHT()
                +preference.getRACIALFAIRNESSWEIGHT();
        double compactnessW = (double)preference.getCOMPACTNESSWEIGHT()/(double)totalWeight;
        double partisanW = (double)preference.getPARTISANFAIRNESSWEIGHT()/(double)totalWeight;
        double populationW = (double)preference.getPOPULATIONVARIANCEWEIGHT()/(double)totalWeight;
        double racialW = (double)preference.getRACIALFAIRNESSWEIGHT()/(double)totalWeight;
        //System.out.println(compactnessW+","+populationW+","+partisanW+","+racialW);
        goodness +=  compactnessW* (double)calculateCompactness();
        goodness += partisanW * (double)calculatePartisanFairness();
        goodness += populationW * (double)(1-calculatePopulationVariance());
        goodness += racialW * (double)calculateRacialFairness();
        return goodness;
    }

    public double calculateCompactness() {
        double compactness = 0;
        Set<Precinct> precincts = this.getPrecinct();
        // 先算分子， circle 的面积
        double maxX = -71.009964;
        double maxY = 44.284783;
        double minX = -71.009964;
        double minY = 44.284783;
        double sum = 0;
        int count =0;
        for (Precinct precinct : precincts) {
                Feature feature = precinct.getFeature();
                Geometry geometry = feature.getGeometry();
                List<List<List<Double>>> coordinates = geometry.getCoordinates();
                if(coordinates.size()==0){
                    count++;
                    continue;
                }
                List<List<Double>> list = coordinates.get(0);
                for (int i = 0; i < list.size(); i++) {
                    if(i==list.size()-1){
                        break;
                    }
                    List<Double> codinates = list.get(i);
                    List<Double> codinates2 = list.get(i+1);
                    sum = sum + ((codinates.get(0)*codinates2.get(1)-codinates2.get(0)*codinates.get(1)));
                    sum = Math.abs(sum);
                    if(codinates.get(0)<=minX){
                        minX = codinates.get(0);
                    }
                    if(codinates.get(0)>=maxX){
                        maxX=codinates.get(0);
                    }
                    if(codinates.get(1)<=minY){
                        minY = codinates.get(1);
                    }
                    if(codinates.get(1)>=maxY){
                        maxY = codinates.get(1);
                    }
                }
        }
        sum= sum/2;
        double circleD = Math.abs(maxX-minX);
        double circleD2 = Math.abs(maxY-minY);
        double realD = 0;
        if(circleD>circleD2){
            realD= circleD/2;
        }else{
            realD= circleD2/2;
        }
        double circleArea = 3.1415916 * (realD*realD);
        compactness = sum/circleArea;
        System.out.println("circleArea"+circleArea);
        System.out.println("sum"+sum);
        System.out.println("compactness"+compactness);
        this.compactness =  compactness;
        System.out.println("compactness"+compactness);
        System.out.println("count!!!!!!!!!!!!!!!!!!!"+count);
        return compactness;
    }

    public double calculatePartisanFairness() {
        double pFairness = 0;
        int dVOtes = this.getVote().get("DEMOCRATIC");
        int rVOtes = this.getVote().get("REPUBLICAN");
        int totalVotes = dVOtes+rVOtes;
        int totalVotesLosing= 0;
        int totalVotesWinning=0;
        if(dVOtes<=rVOtes){
            totalVotesLosing=dVOtes;
            totalVotesWinning = rVOtes;
        }else{
            totalVotesLosing=rVOtes;
            totalVotesWinning = dVOtes;
        }
        int wastedVotes = (int)Math.abs(0.5 * (totalVotes) - totalVotesWinning);
        pFairness =  ((double) Math.abs(totalVotesLosing - wastedVotes)) / totalVotes;
//        pFairness = pFairness/totalVotes;
        this.partisanFairness = pFairness;
        return pFairness;
    }

    public double calculateRacialFairness() {
        double racialFairness = 0;
        this.racialFairness = racialFairness;
        return racialFairness;
    }
    
    public double calculatePopulationVariance() {
        double numOfCDs = this.getState().getCongressionalDistricts().size();
        double mean = this.getState().getPopulation()/numOfCDs;
        double variance = (double)Math.pow((double)(this.population - mean),(double) 2);
        variance = variance/(mean*mean);
        this.populationVariance = (1-(variance/numOfCDs));
        return variance/numOfCDs;
    }

    public double getGoodnessDiff(double cGoodness) {
        return (cGoodness - this.currentGoodness);
    }

    public void removePrecinct(Precinct p) {
        this.precinct.remove(p);
        if (p.getIsBorder()) {
            this.boundaryPrecincts.remove(p);
        }
        p.getNeighborCDistricts().add(this);
        //this.population -= p.getPopulation();
        //this.subtractVotes(p);
        //this.subtractRace(p);
    }

    public void addPrecinct(Precinct p) {
        this.precinct.add(p);
        if (p.getIsBorder()) {
            this.boundaryPrecincts.add(p);
        }
        p.getNeighborCDistricts().remove(this);
        //this.population += p.getPopulation();
        //this.addVotes(p);
        //this.addRace(p);

    }

    private void addVotes(Precinct precinct) {
        HashMap<String, Integer> precinctVotes = precinct.getVote();
        for (String p : this.vote.keySet()) {
            precinctVotes.put(p, vote.get(p) + precinctVotes.get(p));
        }
    }

//    private void addRace(Precinct precinct) {
//        HashMap<Race, Integer> precinctRace = precinct.getRace();
//        for (Race r : this.race.keySet()) {
//            precinctRace.put(r, race.get(r) + precinctRace.get(r));
//        }
//
//    }

    private void subtractVotes(Precinct precinct) {
        HashMap<String, Integer> precinctVotes = precinct.getVote();
        for (String p : this.vote.keySet()) {
            precinctVotes.put(p, vote.get(p) - precinctVotes.get(p));
        }
    }

//    private void subtractRace(Precinct precinct) {
//        HashMap<Race, Integer> precinctRace = precinct.getRace();
//        for (Race r : this.race.keySet()) {
//            precinctRace.put(r, race.get(r) - precinctRace.get(r));
//        }
//    }

    public void setUpPrecinctMapJson(Set<Feature> features, int colorCount) {
        Set<Precinct> ps = this.getPrecinct();
        for (Precinct p : ps) {
            for (Feature f : features) {
                if (f.getProperties().getVTDST10().equals(p.getPrecinctCode())) {
                    f.getProperties().setVTDI10(p.getPrecinctCode());
                    f.getProperties().setPOPULATION(p.getPopulation());
                    f.getProperties().setREGISTERVOTERS(p.getRegisteredVoters());
                    f.getProperties().setTOTALVOTERS(p.getTotalVoters());
                    f.getProperties().setCounty(p.getCounty());
                    f.getProperties().setFill(PropertyManager.getInstance().getValue("color0" + colorCount));
                    p.setFeature(f);
                    this.setColor(f.getProperties().getFill());
                    break;
                }
            }
        }
    }
    public void setUpSCPrecinctMapJson(Set<Feature> features, int colorCount) {
        Set<Precinct> ps = this.getPrecinct();
        for (Precinct p : ps) {
            for (Feature f : features) {
                if (f.getProperties().getGEOID10().equals(p.getPrecinctCode())) {
                    f.getProperties().setPOPULATION(p.getPopulation());
                    f.getProperties().setVTDST10(p.getPrecinctCode());
                    f.getProperties().setREGISTERVOTERS(p.getRegisteredVoters());
                    f.getProperties().setTOTALVOTERS(p.getTotalVoters());
                    f.getProperties().setCounty(p.getCounty());
                    f.getProperties().setFill(PropertyManager.getInstance().getValue("color0" + colorCount));
                    p.setFeature(f);
                    this.setColor(f.getProperties().getFill());
                    break;
                }
            }
        }
    }
    public void setUpCoroladoPrecinctMapJson(Set<Feature> features, int colorCount) {
        Set<Precinct> ps = this.getPrecinct();
        for (Precinct p : ps) {
            for (Feature f : features) {
                if (f.getProperties().getVTDST10().equals(p.getPrecinctCode())) {
                    String geoid10 = f.getProperties().getGEOID10();
                    char charAt = geoid10.charAt(3);
                    String s = String.valueOf(charAt);
                    int cdId = Integer.parseInt(s);
                    f.getProperties().setPOPULATION(p.getPopulation());
                    f.getProperties().setREGISTERVOTERS(p.getRegisteredVoters());
                    f.getProperties().setTOTALVOTERS(p.getTotalVoters());
                    f.getProperties().setFill(PropertyManager.getInstance().getValue("color0" + cdId));
                    p.setFeature(f);
                    this.setColor(f.getProperties().getFill());
                    return ;
//                    break;
                }
            }
        }
        for (Feature f : features) {
            String geoid10 = f.getProperties().getGEOID10();
            char charAt = geoid10.charAt(3);
            String s = String.valueOf(charAt);
            int cdId = Integer.parseInt(s);
            f.getProperties().setFill(PropertyManager.getInstance().getValue("color0" + cdId));
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
