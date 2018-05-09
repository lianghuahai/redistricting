package service;

import java.util.List;
import java.util.Set;

import dao.RSMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pojo.CDInfor;
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
        int i = 1;
        if(stateName.equals("CO")){
            i=i+2;
            numOfCds=numOfCds+2;
        }else if (stateName.equals("SC")){
            i=i+9;
            numOfCds=numOfCds+9;
        }
        for (; i <= numOfCds; i++) {
            CDistrict cd = rsMapper.getCdById(i);
            CDInfor cdInfor = rsMapper.getCdInforById(i);
//            System.out.println(cdInfor);
            cd.setCdInfor(cdInfor);
            state.getCongressionalDistricts().add(cd);
            cd.setState(state);
            Set<Precinct> ps = cd.getPrecinct();
            for (Precinct precinct : ps) {
                precinct.setCDistrict(cd);
            }
        }
        //rsMapper.increaseRunningTimes(state.getRunningTimes()+1,state.getsName());
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
        rsMapper.updatePrecinctField(precinct.getPrecinctCode(),precinct.getVotes().get(Party.REPUBLICAN),precinct.getVotes().get(Party.DEMOCRATIC));
    }

    public void updatePrecinctPopulation(Precinct precinct) {
        rsMapper.updatePrecinctPopulation(precinct);
    }

    public void updatePrecinctVotes(Precinct p) {
        if(p.getPrecinctCode()!=null){
            rsMapper.savePrecinctVotes(p.getPrecinctCode(),p.getVote().get("REPUBLICAN")
                    ,p.getVote().get("DEMOCRATIC"),p.getTotalVoters(),p.getRegisteredVoters(),2016);
        }
    }
    
    public void saveNeighbors(String string, String string2) {
        rsMapper.saveNeighbors(string,string2);
    }
    public void initializeNeighbors(State workingState) {
        Set<CDistrict> cds = workingState.getCongressionalDistricts();
        for (CDistrict cd : cds) {
            Set<Precinct> precincts = cd.getPrecinct();
            for (Precinct precinct : precincts) {
                List<String> neighbors = rsMapper.getNeighborsCode(precinct.getPrecinctCode());
                if(neighbors.size()>0){
                    workingState.setupNeighbors(precinct,neighbors);
                }
            }
        }
        workingState.setupBoundaryPrecincts();
    }

    public void increaseRunningTimes(int runningTimes, String getsName) {
        rsMapper.increaseRunningTimes(runningTimes+1,getsName);
    }

    public void updatePCode() {
        int code = 18001;
        String str = "a";
        for (int i = 1; i <=153; i++) {
            str="Douglas"+i;
            rsMapper.updatePCode(str,code);
            code++;
        }
    }

    public void createVotes() {
        int code = 18001;
        for (int i = 1; i <=155; i++) {
            rsMapper.createVotes(String.valueOf(code));
            code++;
        }
        
    }
}
