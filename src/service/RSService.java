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
        User existUser = rsMapper.queryUserByEmail(currUser.getEmail());
        if (null != existUser) {
            currUser.encrptPW();
            if (currUser.getPassword().equals(existUser.getPassword())) {
                return existUser;
            }
        }
        return null;
    }

    public User register(User user) {
        user.encrptPW();
        User currUser = rsMapper.addUser(user);
        return currUser;
    }

    public boolean addUser (User user){return true;}
    public boolean validateUser (User user){return true;}
    public boolean getMouthlyReport (String mouth){return true;}
    public boolean validateEmail (String email){return true;}
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
