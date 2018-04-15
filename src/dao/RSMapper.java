package dao;

import pojo.User;

public interface RSMapper {
    public User queryUserByEmail(String email);
    public User saveUser(User user);
    
    public boolean addUser (User user);
    public boolean validateUser (User user);
    public boolean getMouthlyReport (String mouth);
    public boolean validateEmail (String email);
    public boolean login (String email, String pw);
    public boolean addNewState (String stateName);
    public boolean getState (String stateName);
    public boolean deleteState (String stateName);
    public boolean redistrict(String jsonData);
    public boolean findState (String stateID);
}
