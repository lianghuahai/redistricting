package dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import pojo.CDInfor;
import pojo.CDistrict;
import pojo.Neighbors;
import pojo.Party;
import pojo.Precinct;
import pojo.Representive;
import pojo.State;
import pojo.User;

public interface RSMapper {
    public int createUser (User user);
    public boolean deleteUserByEmail (User user);
    public boolean updateUserByEmail (User user);
    public User getUserByEmail(String email);
    public List<User> getUsers();
    public State getStateByName(String stateName);
    public int getStateId(String stateName);
    public CDistrict getCdById(int id);
    public int getNumOfCDs(int id);
    //Todo
    public boolean validateUser (User user);
    public boolean getMouthlyReport (String mouth);
    public boolean validateEmail (String email);
    public boolean login (String email, String pw);
    public boolean addNewState (String stateName);
    public boolean getState (String stateName);
    public boolean deleteState (String stateName);
    public boolean redistrict(String jsonData);
    public boolean findState (String stateID);
    
    //
    public void saveCds(CDistrict cd);
    public void savePrecincts(Precinct p);
    public void updatePrecinctField(String precinctCode, Integer rVotes, Integer dVotes);
    public void updatePrecinctPopulation(Precinct precinct);
    public int getPrecinctId(String precinctCode);
    public void savePrecinctVotes(String pCode, int rVotes, int dVotes,int totalVoters,int registerVoters,int year);
    public void saveNeighbors(String string, String string2);
    public void saveToNewNeighbors(String string, String string2,String sName);
    public List<String> getNeighborsCode(String pCode,String sName);
    public List<String> getCONeighborsCode(String pCode,String sName);
    public List<String> getSCNeighborsCode(String pCode,String sName);
    public CDInfor getCdInforById(int id);
    public void increaseRunningTimes(int i,String sName);
    public void updatePCode(String str, int code);
    public void createVotes(String code);
    public void updateUser(String party, String firstName, String lastName, String email);
    public void deleteUser(String email);
    public List<Precinct> getCDsByStateId();
    public void updatePrecinctCounty(String precinctCode,String county);
    public List<Neighbors> getneighbors();
    public void updateNeiCode(int nid, String getpCode, String neighborCode);
    public void saveSCNeighbors(String string, String string2, String sName);
    public void saveCONeighbors(String string, String string2, String sName);
    public void updateArea(String pCode, double area);
    public void saveRR(String sName, int cdistrictId, String name, String party,int year);
    public List<Representive> getRepresents(String stateName, int year);
    public void savePrecinctsone(String code, String cdid,int i);
    
}
