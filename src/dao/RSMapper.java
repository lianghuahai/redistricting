package dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import pojo.CDistrict;
import pojo.Precinct;
import pojo.State;
import pojo.User;

public interface RSMapper {
    public int createUser (User user);
    public boolean deleteUserByEmail (User user);
    public boolean updateUserByEmail (User user);
    public User getUserByEmail(String email);
    public List<User> getUsers();
    public State getStateByName(String stateName);
    public CDistrict getCdById(int id);
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
    
    
    
}
