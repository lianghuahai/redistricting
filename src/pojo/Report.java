package pojo;

import java.util.HashMap;

public class Report {
    private String month;

    private int activeUser;

    private int activePrimeUser;

    private HashMap<String, Integer> ohio;

    private HashMap<String, Integer> colorado;

    private HashMap<String, Integer> newHampshire;

    // setter and getter
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(int activeUser) {
        this.activeUser = activeUser;
    }

    public int getActivePrimeUser() {
        return activePrimeUser;
    }

    public void setActivePrimeUser(int activePrimeUser) {
        this.activePrimeUser = activePrimeUser;
    }

    public HashMap<String, Integer> getOhio() {
        return ohio;
    }

    public void setOhio(HashMap<String, Integer> ohio) {
        this.ohio = ohio;
    }

    public HashMap<String, Integer> getColorado() {
        return colorado;
    }

    public void setColorado(HashMap<String, Integer> colorado) {
        this.colorado = colorado;
    }

    public HashMap<String, Integer> getNewHampshire() {
        return newHampshire;
    }

    public void setNewHampshire(HashMap<String, Integer> newHampshire) {
        this.newHampshire = newHampshire;
    }

    // methods to be implemented
}
