package pojo.mapJson;

import pojo.CDInfor;

public class Propertys {
    private String properties;
    private String STATEFP10;
    private String COUNTYFP10;
    private String VTDST10;
    private String GEOID10;
    private String VTDI10;
    private String NAME10;
    private String NAMELSAD10;
    private String LSAD10;
    private String MTFCC10;
    private String FUNCSTAT10;
    private String ALAND10;
    private String AWATER10;
    private String INTPTLAT10;
    private String INTPTLON10;
    //cd property
    private String STATEFP;
    private String CD115FP;
    private String AFFGEOID;
    private String GEOID;
    private String LSAD;
    private String CDSESSN;
    private String ALAND;
    private String AWATER;
    private long POPULATION;
    private int TOTALVOTERS;
    private int REGISTERVOTERS;
    private int ORIGINALDISTRICT;
    private int RVOTES;
    private int DVOTES;
    private String fill;
    //private CDInfor cdInfor = new CDInfor();
    private String cdName;
    private int cdistrictId;
    private int male;
    private int female;
    private int white;
    private int blackAfrican;
    private int asian;
    private int americanIndian;
    private int others;
    private int houseHoldAvg;
    private int familyAvg;
    private int totalHouseHold;
    private int schoolEnroll;
    private int employees;
    private String county;
    public String getProperties() {
        return properties;
    }
    public void setProperties(String properties) {
        this.properties = properties;
    }
    public String getSTATEFP10() {
        return STATEFP10;
    }
    public void setSTATEFP10(String sTATEFP10) {
        STATEFP10 = sTATEFP10;
    }
    public String getCOUNTYFP10() {
        return COUNTYFP10;
    }
   
    public String getCounty() {
        return county;
    }
    public void setCounty(String county) {
        this.county = county;
    }
    public int getTOTALVOTERS() {
        return TOTALVOTERS;
    }
    public void setTOTALVOTERS(int tOTALVOTERS) {
        TOTALVOTERS = tOTALVOTERS;
    }
    public int getREGISTERVOTERS() {
        return REGISTERVOTERS;
    }
    public void setREGISTERVOTERS(int rEGISTERVOTERS) {
        REGISTERVOTERS = rEGISTERVOTERS;
    }
    public int getORIGINALDISTRICT() {
        return ORIGINALDISTRICT;
    }
    public void setORIGINALDISTRICT(int oRIGINALDISTRICT) {
        ORIGINALDISTRICT = oRIGINALDISTRICT;
    }
    public int getRVOTES() {
        return RVOTES;
    }
    public void setRVOTES(int rVOTES) {
        RVOTES = rVOTES;
    }
    public int getDVOTES() {
        return DVOTES;
    }
    public void setDVOTES(int dVOTES) {
        DVOTES = dVOTES;
    }
    public long getPOPULATION() {
        return POPULATION;
    }
    public void setPOPULATION(long pOPULATION) {
        POPULATION = pOPULATION;
    }
 
    public String getFill() {
        return fill;
    }
    public void setFill(String fill) {
        this.fill = fill;
    }
    public void setCOUNTYFP10(String cOUNTYFP10) {
        COUNTYFP10 = cOUNTYFP10;
    }
    public String getVTDST10() {
        return VTDST10;
    }
    public void setVTDST10(String vTDST10) {
        VTDST10 = vTDST10;
    }
    public String getGEOID10() {
        return GEOID10;
    }
    public void setGEOID10(String gEOID10) {
        GEOID10 = gEOID10;
    }
    public String getVTDI10() {
        return VTDI10;
    }
    public void setVTDI10(String vTDI10) {
        VTDI10 = vTDI10;
    }
    public String getNAME10() {
        return NAME10;
    }
    public void setNAME10(String nAME10) {
        NAME10 = nAME10;
    }
    public String getNAMELSAD10() {
        return NAMELSAD10;
    }
    public void setNAMELSAD10(String nAMELSAD10) {
        NAMELSAD10 = nAMELSAD10;
    }
    public String getLSAD10() {
        return LSAD10;
    }
    public void setLSAD10(String lSAD10) {
        LSAD10 = lSAD10;
    }
    public String getMTFCC10() {
        return MTFCC10;
    }
    public void setMTFCC10(String mTFCC10) {
        MTFCC10 = mTFCC10;
    }
    public String getFUNCSTAT10() {
        return FUNCSTAT10;
    }
    public void setFUNCSTAT10(String fUNCSTAT10) {
        FUNCSTAT10 = fUNCSTAT10;
    }
    public String getALAND10() {
        return ALAND10;
    }
    public void setALAND10(String aLAND10) {
        ALAND10 = aLAND10;
    }
    public String getAWATER10() {
        return AWATER10;
    }
    public void setAWATER10(String aWATER10) {
        AWATER10 = aWATER10;
    }
    public String getINTPTLAT10() {
        return INTPTLAT10;
    }
    public void setINTPTLAT10(String iNTPTLAT10) {
        INTPTLAT10 = iNTPTLAT10;
    }
    public String getINTPTLON10() {
        return INTPTLON10;
    }
    public void setINTPTLON10(String iNTPTLON10) {
        INTPTLON10 = iNTPTLON10;
    }
    //cd property
    public String getSTATEFP() {
        return STATEFP;
    }
    public void setSTATEFP(String sTATEFP) {
        STATEFP = sTATEFP;
    }
    public String getCD115FP() {
        return CD115FP;
    }
    public void setCD115FP(String cD115FP) {
        CD115FP = cD115FP;
    }
    public String getAFFGEOID() {
        return AFFGEOID;
    }
    public void setAFFGEOID(String aFFGEOID) {
        AFFGEOID = aFFGEOID;
    }
    public String getGEOID() {
        return GEOID;
    }
    public void setGEOID(String gEOID) {
        GEOID = gEOID;
    }
    public String getLSAD() {
        return LSAD;
    }
    public void setLSAD(String lSAD) {
        LSAD = lSAD;
    }
    public String getCDSESSN() {
        return CDSESSN;
    }
    public void setCDSESSN(String cDSESSN) {
        CDSESSN = cDSESSN;
    }
    public String getALAND() {
        return ALAND;
    }
    public void setALAND(String aLAND) {
        ALAND = aLAND;
    }
    public String getAWATER() {
        return AWATER;
    }
    public void setAWATER(String aWATER) {
        AWATER = aWATER;
    }
    //cd information
    public String getCdName() {
        return cdName;
    }
    public void setCdName(String cdName) {
        this.cdName = cdName;
    }
    public int getCdistrictId() {
        return cdistrictId;
    }
    public void setCdistrictId(int cdistrictId) {
        this.cdistrictId = cdistrictId;
    }
    public int getMale() {
        return male;
    }
    public void setMale(int male) {
        this.male = male;
    }
    public int getFemale() {
        return female;
    }
    public void setFemale(int female) {
        this.female = female;
    }
    public int getWhite() {
        return white;
    }
    public void setWhite(int white) {
        this.white = white;
    }
    public int getBlackAfrican() {
        return blackAfrican;
    }
    public void setBlackAfrican(int blackAfrican) {
        this.blackAfrican = blackAfrican;
    }
    public int getAsian() {
        return asian;
    }
    public void setAsian(int asian) {
        this.asian = asian;
    }
    public int getAmericanIndian() {
        return americanIndian;
    }
    public void setAmericanIndian(int americanIndian) {
        this.americanIndian = americanIndian;
    }
    public int getOthers() {
        return others;
    }
    public void setOthers(int others) {
        this.others = others;
    }
    public int getHouseHoldAvg() {
        return houseHoldAvg;
    }
    public void setHouseHoldAvg(int houseHoldAvg) {
        this.houseHoldAvg = houseHoldAvg;
    }
    public int getFamilyAvg() {
        return familyAvg;
    }
    public void setFamilyAvg(int familyAvg) {
        this.familyAvg = familyAvg;
    }
    public int getTotalHouseHold() {
        return totalHouseHold;
    }
    public void setTotalHouseHold(int totalHouseHold) {
        this.totalHouseHold = totalHouseHold;
    }
    public int getSchoolEnroll() {
        return schoolEnroll;
    }
    public void setSchoolEnroll(int schoolEnroll) {
        this.schoolEnroll = schoolEnroll;
    }
    public int getEmployees() {
        return employees;
    }
    public void setEmployees(int employees) {
        this.employees = employees;
    }
}
