package service;

import dao.RSMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pojo.State;
import pojo.User;
import utils.MD5Util;

@Service
public class RSService {
    @Autowired
    private RSMapper rsMapper;

    public User login(User currUser) {
        User existedUser = rsMapper.queryUserByEmail(currUser.getEmail());
        if (null != existedUser) {
            currUser.encrptPW();
            if (currUser.getPassword().equals(existedUser.getPassword())) {
                return existedUser;
            }
        }
        return null;
    }

    public User register(User user) {
        user.encrptPW();
        User currUser = rsMapper.createUser(user);
        return currUser;
    }
    public boolean validateEmail (String email){
        User existedUser = rsMapper.queryUserByEmail(email);
        if(null == existedUser){
            return false;
        }else{
            return true;
        }
    }
    public boolean addUser (User user){return true;}
    public boolean validateUser (User user){return true;}
    public boolean getMouthlyReport (String mouth){return true;}
    
    public boolean login (String email, String pw){return true;}
    public boolean addNewState (String stateName){return true;}
    public boolean getState (String stateName){return true;}
    public boolean deleteState (String stateName){return true;}
    public boolean redistrict(String jsonData){return true;}
    public boolean findState (String stateID){return true;}

    public State getStateByName() {
        // TODO Auto-generated method stub
        return null;
    }
    
    
}
