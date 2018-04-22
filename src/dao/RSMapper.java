package dao;

import pojo.User;

public interface RSMapper {
    public User createUser (User user);
    public boolean deleteUserByEmail (User user);
    public boolean updateUserByEmail (User user);
    public User queryUserByEmail(String email);
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
}
