package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pojo.CDistrict;
import pojo.Party;
import pojo.Precinct;
import pojo.Preference;
import pojo.ObjectElement;
import pojo.PrecinctProperty;
import pojo.State;
import pojo.StateName;
import pojo.User;
import pojo.mapJson.Feature;
import pojo.mapJson.PrecinctJson;
import service.RSService;
import utils.LoadJsonData;
import utils.LoadNHData;
import utils.ReportJob;
import utils.ReportType;

import com.google.gson.Gson;

@Controller
public class RSController {
    @Autowired
    private RSService rsService;

    @RequestMapping("login")
    public void login(User user, HttpServletRequest req, HttpServletResponse res) throws IOException {
        User existedUser = rsService.login(user);
        Gson gson = new Gson();
        if (null == existedUser) {
            res.getWriter().print(gson.toJson("null"));
        } else {
            res.getWriter().print(gson.toJson(existedUser.getRole()));
        }
    }

    @RequestMapping("register")
    public void register(User user, HttpServletRequest req, HttpServletResponse res) throws IOException {
        boolean createdUser = rsService.register(user);
        Gson gson = new Gson();
        res.getWriter().print(gson.toJson(createdUser));
    }
    
    @RequestMapping("validateEmail")
    public void validateEmail(String email,HttpServletRequest req, HttpServletResponse res) throws IOException{
        boolean qualifiedEmail = rsService.validateEmail(email);
        res.getWriter().print(new Gson().toJson(qualifiedEmail));
    }
    @RequestMapping("getUsers")
    public void getUsers(HttpServletRequest req, HttpServletResponse res) throws IOException{
        List<User> users = rsService.getUsers();
        res.getWriter().print(new Gson().toJson(users));
    }
    @RequestMapping("deleteUserByEmail")
    public void deleteUserByEmail(String email,HttpServletRequest req, HttpServletResponse res) throws IOException{
        List<User> qualifiedEmail = rsService.getUsers();
        res.getWriter().print(new Gson().toJson(qualifiedEmail));
    }
    
    @RequestMapping("displayState")
    public void displayState(String stateName,String dLevel,String userEmail,HttpServletRequest req, HttpServletResponse res) throws IOException, Exception{
        State originalState = rsService.initializeState(stateName);
        PrecinctJson mapJson = new LoadJsonData().getJsonData(stateName,dLevel);
        if(dLevel.equals("PD")){
            int colorCount=1;
            for (CDistrict cd : originalState.getCongressionalDistricts()) {
                cd.setUpPrecinctMapJson(mapJson.getFeatures(),colorCount);
                colorCount++;
            }
        }else{
            originalState.setUpCdMapJson(mapJson.getFeatures());
        }
        req.getSession().setAttribute("originalState",originalState);
//        req.getSession().setAttribute(stateName+"originalState",originalState);
        res.getWriter().print(new Gson().toJson(mapJson));
    }
    
    @RequestMapping("redistrict")
    public void redistrict(String stateName,String userEmail,Preference preference,HttpServletRequest req, HttpServletResponse res) throws IOException{
        System.out.println("redistrict");
//            State originalState= (State) req.getSession().getAttribute(stateName+"originalState");
        State originalState= (State) req.getSession().getAttribute("originalState");
        State workingState= originalState.clone();
        workingState.setPreference(preference);
        Set<CDistrict> cds = workingState.getCongressionalDistricts();
        for (CDistrict cDistrict : cds) {
            float pv = cDistrict.calculatePopulationVariance();
           // System.out.println(pv);
        }
        //测试object function
            
            
//            Precinct movedPrecinct = workingState.startAlgorithm();
//            if(!workingState.checkTermination()){
//                while(movedPrecinct==null){
//                    movedPrecinct = workingState.startAlgorithm();
//                }
//                req.getSession().setAttribute("workingState",workingState);
//                res.getWriter().print(new Gson().toJson(movedPrecinct));
//            }else{
//                res.getWriter().print(new Gson().toJson("{terminated:true}"));
//            }
            System.out.println("redistrict:"+originalState.getPopulation());
            originalState.setPopulation(100);
            req.getSession().setAttribute("workingState",originalState);
            PrecinctProperty precinctProperty =  new PrecinctProperty();
            precinctProperty.setFill("#f3370f");
            precinctProperty.setVTDI10("A");
            res.getWriter().print(new Gson().toJson(precinctProperty));
    }
    @RequestMapping("process")
    public void process(String userEmail,HttpServletRequest req, HttpServletResponse res) throws IOException{
        State workingState = (State) req.getSession().getAttribute("workingState");
//        Precinct movedPrecinct = workingState.startAlgorithm();
//        if(!workingState.checkTermination()){
//            while(movedPrecinct==null){
//                movedPrecinct = workingState.startAlgorithm();
//            }
//            req.getSession().setAttribute("workingState",workingState);
//            res.getWriter().print(new Gson().toJson(movedPrecinct));
//        }else{
//            res.getWriter().print(new Gson().toJson("{terminated:true}"));
//        }
        req.getSession().setAttribute("workingState",workingState);
        
        PrecinctProperty precinctProperty =  new PrecinctProperty();
        precinctProperty.setVTDI10("A");
        Random rd = new Random(); 
        int x = rd.nextInt(3)+1;
        if(x==1){
            precinctProperty.setFill("#FF69B4");
        }else if(x==2){
            precinctProperty.setFill("#1E90FF");
        }else{
            precinctProperty.setFill("#DC143C");
        }
            res.getWriter().print(new Gson().toJson(precinctProperty));
    }
    
    
    @RequestMapping("stop")
    public void stop(String userEmail,HttpServletRequest req, HttpServletResponse res) throws IOException{
//      req.getSession().removeAttribute(userEmail+"workingState");
      req.getSession().removeAttribute("workingState");
      res.getWriter().print(new Gson().toJson("ok"));
  }
    
    
    //Todo
    public boolean addUser (User user){return true;}
    public boolean getMouthlyReport (String mouth){return true;}
    public boolean addNewState (String stateName){return true;}
    public boolean getState (String stateName){return true;}
    public boolean deleteState (String stateName){return true;}
    public boolean findState (String stateID){return true;}
    @RequestMapping("saveData")
    public void a( HttpServletRequest req, HttpServletResponse res) throws Exception {
        System.out.println("aaa");
        LoadNHData a = new LoadNHData();
        int abc=0;
        State workingState = a.getState();
//        Set<CDistrict> cds = workingState.getCongressionalDistricts();
//        for (CDistrict cDistrict : cds) {
//            System.out.println(cDistrict.getPrecinct().size());
//            System.out.println(cDistrict.getName()+cDistrict.getPopulation()+cDistrict.getStateId());
//            rsService.saveCds(cDistrict);
//            for (Precinct p : cDistrict.getPrecinct()) {
//                rsService.savePrecincts(p);
//            }
//        }
        
        //update population or votes
        Set<CDistrict> cds = workingState.getCongressionalDistricts();
        
        for (CDistrict cDistrict : cds) {
            Set<Precinct> ps = cDistrict.getPrecinct();
            for (Precinct precinct : ps) {
//                rsService.updatePrecinctField(precinct);
//                rsService.updatePrecinctPopulation(precinct);
                rsService.updatePrecinctVotes(precinct);
            }
        }
        
    }
    
}
