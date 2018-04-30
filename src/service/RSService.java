package service;

import java.util.List;
import java.util.Set;

import dao.RSMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pojo.CDistrict;
import pojo.Party;
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
   
    public State initializeState(String stateName) {
        State state = rsMapper.getStateByName(stateName);
        int stateId = rsMapper.getStateId(stateName);
        int numOfCds = rsMapper.getNumOfCDs(stateId);
        for (int i = 1; i <= numOfCds; i++) {
            CDistrict cd = rsMapper.getCdById(i);
            state.getCongressionalDistricts().add(cd);
        }
        return state;
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
    
    //Todo
    public boolean addUser (User user){return true;}
    public boolean validateUser (User user){return true;}
    public boolean getMouthlyReport (String mouth){return true;}
    
    public boolean login (String email, String pw){return true;}
    public boolean addNewState (String stateName){return true;}
    public boolean getState (String stateName){return true;}
    public boolean deleteState (String stateName){return true;}
    public boolean redistrict(String jsonData){return true;}
    public boolean findState (String stateID){return true;}

    public void updatePrecinctField(Precinct precinct) {
        System.out.println(precinct.getPrecinctCode()+","+precinct.getVotes().get(Party.REPUBLICAN)+","+precinct.getVotes().get(Party.REPUBLICAN));
        rsMapper.updatePrecinctField(precinct.getPrecinctCode(),precinct.getVotes().get(Party.REPUBLICAN),precinct.getVotes().get(Party.DEMOCRATIC));
    }

    public void updatePrecinctPopulation(Precinct precinct) {
        rsMapper.updatePrecinctPopulation(precinct);
    }

    public void updatePrecinctVotes(Precinct p) {
        if(p.getPrecinctCode()==null){
            System.out.println(p);
        }
        if(p.getPrecinctCode()!=null){
            rsMapper.savePrecinctVotes(p.getPrecinctCode(),p.getVotes().get(Party.REPUBLICAN)
                    ,p.getVotes().get(Party.DEMOCRATIC),p.getTotalVoters(),p.getRegisteredVoters(),2016);
        }
    }
    
    public void saveNeighbors(String string, String string2) {
        rsMapper.saveNeighbors(string,string2);
    }
    public void findNeighbors(State workingState) {
        int count=0;
        Set<CDistrict> cds = workingState.getCongressionalDistricts();
        for (CDistrict cd : cds) {
            Set<Precinct> precincts = cd.getPrecinct();
            for (Precinct precinct : precincts) {
                List<String> neighbors = rsMapper.getNeighborsCode(precinct.getPrecinctCode());
                if(neighbors.size()>0){
                    count++;
                    workingState.setupNeighbors(precinct,neighbors);
                }
            }
        }
        System.out.println("几个precinct"+count);
    }
}
