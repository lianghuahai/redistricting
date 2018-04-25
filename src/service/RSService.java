package service;

import java.util.List;
import java.util.Set;

import dao.RSMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pojo.CDistrict;
import pojo.Precinct;
import pojo.State;
import pojo.User;
import pojo.UserRole;
import utils.MD5Util;

@Service
public class RSService {
    @Autowired
    private RSMapper rsMapper;

    public User login(User currUser) {
        User existedUser = rsMapper.getUserByEmail(currUser.getEmail());
        if (null != existedUser) {
            currUser.encrptPW();
            if (currUser.getPassword().equals(existedUser.getPassword())) {
                return existedUser;
            }
        }
        return null;
    }

    public boolean register(User user) {
        user.encrptPW();
        user.setRole(UserRole.USER);
        int successCode = rsMapper.createUser(user);
        if(successCode==1){
            return true;
        }else{
            return false;
        }
    }
    public boolean validateEmail (String email){
        User existedUser = rsMapper.getUserByEmail(email);
        if(null == existedUser){
            return true;
        }else{
            return false;
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

    public State getStateByName(String stateName) {
        
        return rsMapper.getStateByName(stateName);
    }
    public CDistrict getCdById(int id) {
        
        return rsMapper.getCdById(id);
    }
    public List<User> getUsers() {
        return rsMapper.getUsers();
    }
    
    public void saveCds(CDistrict cds){
        rsMapper.saveCds(cds);
    }
    public void savePrecincts(Precinct p){
        rsMapper.savePrecincts(p);
    }
}
