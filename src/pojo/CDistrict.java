package pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pojo.mapJson.Feature;
import utils.PropertyManager;

public class CDistrict {
    private String name;
    private State state = new State();
    
    private CDInfor cdInfor =  new CDInfor();
    private HashMap<Integer, Party> winnerParty = new HashMap<Integer, Party>();

    private Set<MapData> map = new HashSet<MapData>();

    private Set<Precinct> precinct = new HashSet<Precinct>();

    private long population;

    private HashMap<Race, Integer> race = new HashMap<Race, Integer>();

    private HashMap<Party, Integer> votes = new HashMap<Party, Integer>();
    private HashMap<String, Integer> vote = new HashMap<String, Integer>();

    private Set<Precinct> boundaryPrecincts = new HashSet<Precinct>();

    private float currentGoodness;

    private Feature feature = new Feature();

    private int cdCode;

    private int registerVoters;

    private int totalVoters;
    
    private float compactness;
    private float partisanFairness;
    private float populationVariance;
    private float racialFairness;
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

    public float getCompactness() {
        return compactness;
    }

    public void setCompactness(float compactness) {
        this.compactness = compactness;
    }

    public float getPartisanFairness() {
        return partisanFairness;
    }

    public void setPartisanFairness(float partisanFairness) {
        this.partisanFairness = partisanFairness;
    }

    public float getPopulationVariance() {
        return populationVariance;
    }

    public void setPopulationVariance(float populationVariance) {
        this.populationVariance = populationVariance;
    }

    public float getRacialFairness() {
        return racialFairness;
    }

    public void setRacialFairness(float racialFairness) {
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
        Map<ObjectElement, Integer> objectElementMap = this.getState().getPreference().getObjectElementMap();
        float totalWeight=(int)objectElementMap.get(ObjectElement.COMPACTNESSWEIGHT)
                +(int)objectElementMap.get(ObjectElement.PARTISANFAIRNESSWEIGHT)
                +(int)objectElementMap.get(ObjectElement.POPULATIONVARIANCEWEIGHT)
                +(int)objectElementMap.get(ObjectElement.RACIALFAIRNESSWEIGHT);
        float compactnessW = (float)objectElementMap.get(ObjectElement.COMPACTNESSWEIGHT)/(float)totalWeight;
        float partisanW = (float)objectElementMap.get(ObjectElement.PARTISANFAIRNESSWEIGHT)/(float)totalWeight;
        float populationW = (float)objectElementMap.get(ObjectElement.POPULATIONVARIANCEWEIGHT)/(float)totalWeight;
        float racialW = (float)objectElementMap.get(ObjectElement.RACIALFAIRNESSWEIGHT)/(float)totalWeight;
        //System.out.println(compactnessW+","+populationW+","+partisanW+","+racialW);
        goodness +=  compactnessW* (float)calculateCompactness();
        goodness += partisanW * (float)calculatePartisanFairness();
        goodness += populationW * (float)(1-calculatePopulationVariance());
        goodness += racialW * (float)calculateRacialFairness();
        return goodness;
    }

    public float calculateCompactness() {
        float compactness = 0;
        this.compactness = compactness;
        return compactness;
    }

    public float calculatePartisanFairness() {
        float pFairness = 0;
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
        pFairness =  ((float) Math.abs(totalVotesLosing - wastedVotes)) / totalVotes;
//        pFairness = pFairness/totalVotes;
        this.partisanFairness = pFairness;
        return pFairness;
    }

    public float calculateRacialFairness() {
        float racialFairness = 0;
        this.racialFairness = racialFairness;
        return racialFairness;
    }
    
    public float calculatePopulationVariance() {
        float numOfCDs = this.getState().getCongressionalDistricts().size();
        float mean = this.getState().getPopulation()/numOfCDs;
        float variance = (float)Math.pow((double)(this.population - mean),(double) 2);
        variance = variance/(mean*mean);
        this.populationVariance = (1-(variance/numOfCDs));
        return variance/numOfCDs;
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

    private void addRace(Precinct precinct) {
        HashMap<Race, Integer> precinctRace = precinct.getRace();
        for (Race r : this.race.keySet()) {
            precinctRace.put(r, race.get(r) + precinctRace.get(r));
        }

    }

    private void subtractVotes(Precinct precinct) {
        HashMap<String, Integer> precinctVotes = precinct.getVote();
        for (String p : this.vote.keySet()) {
            precinctVotes.put(p, vote.get(p) - precinctVotes.get(p));
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
                    f.getProperties().setVTDI10(p.getPrecinctCode());
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
    public void setUpSCPrecinctMapJson(Set<Feature> features, int colorCount) {
        Set<Precinct> ps = this.getPrecinct();
        for (Precinct p : ps) {
            for (Feature f : features) {
                if (f.getProperties().getGEOID10().equals(p.getPrecinctCode())) {
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
