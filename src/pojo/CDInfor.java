package pojo;

public class CDInfor {
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
    private int houseHoldMedian;

//    private int houseHoldMean;
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
    @Override
    public String toString() {
        return "CDInfor [cdName=" + cdName + ", cdistrictId=" + cdistrictId + ", male=" + male + ", female="
                + female + ", white=" + white + ", blackAfrican=" + blackAfrican + ", asian=" + asian
                + ", americanIndian=" + americanIndian + ", others=" + others + ", houseHoldAvg="
                + houseHoldAvg + ", familyAvg=" + familyAvg + ", totalHouseHold=" + totalHouseHold
                + ", schoolEnroll=" + schoolEnroll + ", employees=" + employees + "]";
    }
    public int getHouseHoldMedian() {
        return houseHoldMedian;
    }
    public void setHouseHoldMedian(int houseHoldMedian) {
        this.houseHoldMedian = houseHoldMedian;
    }
    
}
