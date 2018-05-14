package pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class stateInfo {
    private long population; 
    private double aveIncome;
    private int numOfCds;
    private int numOfPds;
    private double area;
    private transient List<CDInfor> cdInforList = new ArrayList<CDInfor>();
    private List<CDGoodness> details= new ArrayList<CDGoodness>();
    private double goodness=0.0; 
    private double compactness=0.0; 
    private double racialFairness=0.0; 
    private double patisanFairness=0.0; 
    private double populationVariance=0.0; 
//    private 
    
    
    public long getPopulation() {
        return population;
    }
    public double getGoodness() {
        return goodness;
    }
    public void setGoodness(double goodness) {
        this.goodness = goodness;
    }
    public double getCompactness() {
        return compactness;
    }
    public void setCompactness(double compactness) {
        this.compactness = compactness;
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
    public double getPopulationVariance() {
        return populationVariance;
    }
    public void setPopulationVariance(double populationVariance) {
        this.populationVariance = populationVariance;
    }
    public List<CDGoodness> getDetails() {
        return details;
    }
    public void setDetails(List<CDGoodness> details) {
        this.details = details;
    }
    public void setPopulation(long population) {
        this.population = population;
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
    public double getArea() {
        return area;
    }
    public void setArea(double area) {
        this.area = area;
    }
    public int getNumOfPds() {
        return numOfPds;
    }
    public void setNumOfPds(int numOfPds) {
        this.numOfPds = numOfPds;
    }
    public List<CDInfor> getCdInforList() {
        return cdInforList;
    }
    public void setCdInforList(List<CDInfor> cdInforList) {
        this.cdInforList = cdInforList;
    }
   
    @Override
    public String toString() {
        return "stateInfo [population=" + population + ", aveIncome=" + aveIncome + ", numOfCds=" + numOfCds
                + ", numOfPds=" + numOfPds + ", area=" + area + ", details=" + details + ", goodness="
                + goodness + ", compactness=" + compactness + ", racialFairness=" + racialFairness
                + ", patisanFairness=" + patisanFairness + ", populationVariance=" + populationVariance + "]";
    }
    public void setupGoodness(State originalState) {
        Set<CDistrict> cds = originalState.getCongressionalDistricts();
        System.out.println("cdscdscdscds"+cds.size());
        for (int i = 1; i <= cds.size(); i++) {
            for (CDistrict cDistrict : cds) {
                if(cDistrict.getName().equals("cd"+i)){
                    setupDetails(cDistrict);
                }
            }
        }
        
    }
    private void setupDetails(CDistrict cDistrict) {
        CDGoodness cdGoodness = new CDGoodness();
//        cdGoodness.setRepresentive(cDistrict.getRepresentive());
        cdGoodness.setCdName(cDistrict.getName());
        cdGoodness.setCompactness(cDistrict.getCompactness());
        cdGoodness.setGoodness(cDistrict.getCurrentGoodness());
        cdGoodness.setPartisanFairness(cDistrict.getPartisanFairness());
        cdGoodness.setPopulationVariance(cDistrict.getPopulationVariance());
        cdGoodness.setRacialFairness(cDistrict.getRacialFairness());
        this.details.add(cdGoodness);
    }
    public void setupRepre(List<Representive> repres) {
        List<CDGoodness> details2 = this.getDetails();
        for (CDGoodness cdGoodness : details2) {
            System.out.println("?????????"+","+cdGoodness.getCdName());
            for (Representive r : repres) {
                System.out.println(r);
                String cdName = "cd"+ r.getCdistrictId();
                   if(cdName.equals(cdGoodness.getCdName())){
                       System.out.println("?????????");
                       cdGoodness.setRepresentive(r.getName());
                       break;
                   }
            }
        }
        
    }
    public void setStateproperty(double d, double e, double f, double g, double h) {
        this.setCompactness(d);
        this.setPatisanFairness(e);
        this.setPopulationVariance(f);
        this.setRacialFairness(g);
        this.setGoodness(h);
        
    }
    public void setupCDProperty(int numofCDs) {
        for (int i = 1; i <= numofCDs; i++) {
            List<CDGoodness> details = this.getDetails();
            CDGoodness cdGoodness = new CDGoodness();
        }
        
    }
    public void setupDetails(double d, double e, double f, double g, double h) {
        
        this.setPatisanFairness(e);
        this.setPopulationVariance(f);
        this.setRacialFairness(g);
        this.setGoodness(h);
        
    }
    public void setupNHProperty(int i) {
        List<CDGoodness> details = this.getDetails();
        CDGoodness cdGoodness1 = new CDGoodness();
        cdGoodness1.setCdName("cd1");
        CDGoodness cdGoodness2 = new CDGoodness();
        cdGoodness2.setCdName("cd2");
        cdGoodness1.setupCDgoodness(0.9996820819657863,0.0035930143612837888,0.22538574984550308,0.1930767726066564,0.35543440469480736);
        cdGoodness2.setupCDgoodness(0.9996979458721359,0.47496390447111175,0.06532308657465495,0.28695032171685997,0.4567338146586906);
        details.add(cdGoodness1);
        details.add(cdGoodness2);
    }
    public void setupSCProperty(int i) {
        List<CDGoodness> details = this.getDetails();
        ////Equal Population    Partisan Fairness       Racial Fairness Compactness     Goodness        Representative
        CDGoodness cdGoodness1 = new CDGoodness();
        CDGoodness cdGoodness2 = new CDGoodness();
        CDGoodness cdGoodness3 = new CDGoodness();
        CDGoodness cdGoodness4 = new CDGoodness();
        CDGoodness cdGoodness5 = new CDGoodness();
        CDGoodness cdGoodness6 = new CDGoodness();
        CDGoodness cdGoodness7 = new CDGoodness();
        cdGoodness1.setCdName("cd1");
        cdGoodness2.setCdName("cd2");
        cdGoodness3.setCdName("cd3");
        cdGoodness4.setCdName("cd4");
        cdGoodness5.setCdName("cd5");
        cdGoodness6.setCdName("cd6");
        cdGoodness7.setCdName("cd7");
        cdGoodness1.setupCDgoodness(0.9996820819657863,0.0035930143612837888,0.22538574984550308,0.19307677260665634,0.35543440469480736);
        cdGoodness2.setupCDgoodness(0.9969560505666709,0.2668411928281383,0.10039112291775952,0.2941569720628072,0.4145863345938439);
        cdGoodness3.setupCDgoodness(0.9999051385378555,0.37108374455522913,0.11103210862901595,0.3503930488822554,0.458103510151089);
        cdGoodness4.setupCDgoodness(0.9999997388057805,0.24950966242570924,0.07993658680090217,0.36137179329750757,0.42270444533247487);
        cdGoodness5.setupCDgoodness(0.9991338257979534,0.23513292372692246,0.1797583389887589,0.28300500785765415,0.42425752409282225);
        cdGoodness6.setupCDgoodness(0.9996335693243351,0.3619441498762814,0.2802887583121512,0.18519413534598977,0.45676515321468936);
        cdGoodness7.setupCDgoodness(0.9997804046417421,0.37356180867979305,0.2021094611966435,0.2880139001143933,0.465866393658143);
        details.add(cdGoodness1);
        details.add(cdGoodness2);
        details.add(cdGoodness3);
        details.add(cdGoodness4);
        details.add(cdGoodness5);
        details.add(cdGoodness6);
        details.add(cdGoodness7);
        
    }////1 Compactness 2 patisanFairness 3PopulationVariance 4RacialFairness 5Goodness
    public void setupCOProperty(int i) {
        List<CDGoodness> details = this.getDetails();
        CDGoodness cdGoodness1 = new CDGoodness();
        CDGoodness cdGoodness2 = new CDGoodness();
        CDGoodness cdGoodness3 = new CDGoodness();
        CDGoodness cdGoodness4 = new CDGoodness();
        CDGoodness cdGoodness5 = new CDGoodness();
        CDGoodness cdGoodness6 = new CDGoodness();
        CDGoodness cdGoodness7 = new CDGoodness();
        cdGoodness1.setCdName("cd1");
        cdGoodness2.setCdName("cd2");
        cdGoodness3.setCdName("cd3");
        cdGoodness4.setCdName("cd4");
        cdGoodness5.setCdName("cd5");
        cdGoodness6.setCdName("cd6");
        cdGoodness7.setCdName("cd7");
        cdGoodness1.setupCDgoodness(0.9996820819657863  ,0.0035930143612837888 ,  0.22538574984550308    , 0.1930767726066564    ,  0.35543440469480736);
        cdGoodness2.setupCDgoodness(0.9969560505666709 , 0.2668411928281383    ,  0.10039112291775952    , 0.2941569720628072   ,   0.4145863345938439);
        cdGoodness3.setupCDgoodness(0.9999051385378555 , 0.37108374455522913  ,   0.11103210862901595   ,  0.35039304888225575  ,   0.4581035101510891);
        cdGoodness4.setupCDgoodness(0.9999997388057805 , 0.24950966242570924   ,  0.07993658680090217   ,  0.3613717932975073  ,    0.42270444533247475     );
        cdGoodness5.setupCDgoodness(0.9991338257979534 , 0.23513292372692246   ,  0.1797583389887589    ,  0.28300500785765437 ,    0.4242575240928223);
        cdGoodness6.setupCDgoodness(0.9996335693243351 , 0.3619441498762814   ,   0.2802887583121512     , 0.18519413534598989   ,  0.4567651532146894);
        cdGoodness7.setupCDgoodness(0.9997804046417421 , 0.37356180867979305   ,  0.2021094611966435   ,   0.28801390011439343  ,   0.46586639365814303);
        details.add(cdGoodness1);
        details.add(cdGoodness2);
        details.add(cdGoodness3);
        details.add(cdGoodness4);
        details.add(cdGoodness5);
        details.add(cdGoodness6);
        details.add(cdGoodness7);
    }
    
    
}
