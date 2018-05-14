package pojo;

public class Representive {
    private int year;
    private String sName ;
    private int cdistricId ;
    private String name ;
    private String party;
    public int getYear() {
        return year;
    }
    
    @Override
    public String toString() {
        return "Representive [year=" + year + ", sName=" + sName + ", cdistrictId=" + cdistricId + ", name="
                + name + ", party=" + party + "]";
    }

    public void setYear(int year) {
        this.year = year;
    }
    public String getsName() {
        return sName;
    }
    public void setsName(String sName) {
        this.sName = sName;
    }
    public int getCdistrictId() {
        return cdistricId;
    }
    public void setCdistrictId(int cdistrictId) {
        this.cdistricId = cdistrictId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getParty() {
        return party;
    }
    public void setParty(String party) {
        this.party = party;
    }
    
    
}
