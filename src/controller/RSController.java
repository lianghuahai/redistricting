package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import pojo.ObjectElement;
import pojo.Precinct;
import pojo.PrecinctProperty;
import pojo.Preference;
import pojo.State;
import pojo.User;
import pojo.mapJson.PrecinctJson;
import service.RSService;
import utils.LoadJsonData;
import utils.LoadNHData;

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
        rsService.setUpNeighbors(workingState);
        /*
        preference= new Preference();
        preference.getObjectElementMap().put(ObjectElement.COMPACTNESSWEIGHT, 0);
        preference.getObjectElementMap().put(ObjectElement.POPULATIONVARIANCEWEIGHT, 1);
        preference.getObjectElementMap().put(ObjectElement.PARTISANFAIRNESSWEIGHT, 0);
        preference.getObjectElementMap().put(ObjectElement.RACIALFAIRNESSWEIGHT, 0);
        workingState.setPreference(preference);
        //测试object function
        Set<CDistrict> cds = workingState.getCongressionalDistricts();
        CDistrict cd1 = new CDistrict();
        CDistrict cd2 = new CDistrict();
        for (CDistrict cDistrict : cds) {
            if(cDistrict.getName().equals("cd1")){
                cd1 = cDistrict;
            }else{
                cd2 = cDistrict;
              }
        }
        for (CDistrict cDistrict : cds) {
            Set<Precinct> precinct = cDistrict.getPrecinct();
            if(cDistrict.getName().equals("cd1")){
                for (Precinct p : precinct) {
                    cDistrict.getBoundaryPrecincts().add(p);
                    p.getNeighborCDistrictList().add(cd2);
                    break;
                }
            }else{
                for (Precinct p : precinct) {
                    cDistrict.getBoundaryPrecincts().add(p);
                    p.getNeighborCDistrictList().add(cd1);
                    break;
                }
            }
            break;
        }
        
        Precinct movedPrecinct = workingState.startAlgorithm();
        if(!workingState.checkTermination()){
            while(movedPrecinct==null){
                movedPrecinct = workingState.startAlgorithm();
            }
            PrecinctProperty precinctProperty =  new PrecinctProperty();
            precinctProperty.setFill(movedPrecinct.getFeature().getProperties().getFill());
            precinctProperty.setVTDI10(movedPrecinct.getPrecinctCode());
            req.getSession().setAttribute("workingState",workingState);
            res.getWriter().print(new Gson().toJson(precinctProperty));
        }else{
            System.out.println("null----------------");
           // res.getWriter().print(new Gson().toJson("{terminated:true}"));
        }
            
            
        
            req.getSession().setAttribute("workingState",originalState);          */
//            PrecinctProperty precinctProperty =  new PrecinctProperty();
//            precinctProperty.setFill("#f3370f");
//            precinctProperty.setVTDI10("A");
//            res.getWriter().print(new Gson().toJson(precinctProperty));
    }
    @RequestMapping("process")
    public void process(String userEmail,HttpServletRequest req, HttpServletResponse res) throws IOException{
        State workingState = (State) req.getSession().getAttribute("workingState");
        Precinct movedPrecinct = workingState.startAlgorithm();
        if(!workingState.checkTermination()){
            while(movedPrecinct==null){
                movedPrecinct = workingState.startAlgorithm();
            }
            System.out.println(movedPrecinct.getName());
            Set<CDistrict> cdss = workingState.getCongressionalDistricts();
            for (CDistrict cDistrict : cdss) {
                System.out.println(cDistrict.getName()+":"+cDistrict.getPopulation());
            }
            System.out.println("move--------------");
            System.out.println(movedPrecinct.getName()+","+movedPrecinct.getCDistrict().getName());
            PrecinctProperty precinctProperty =  new PrecinctProperty();
            precinctProperty.setFill(movedPrecinct.getFeature().getProperties().getFill());
            System.out.println(movedPrecinct.getFeature().getProperties().getFill());
            System.out.println(movedPrecinct.getPrecinctCode());
            precinctProperty.setVTDI10(movedPrecinct.getPrecinctCode());
            req.getSession().setAttribute("workingState",workingState);
            res.getWriter().print(new Gson().toJson(precinctProperty));
        }else{
            System.out.println("null----------------");
           // res.getWriter().print(new Gson().toJson("{terminated:true}"));
        }
            req.getSession().setAttribute("workingState",workingState);
   }
    
    
    
    
    
    //Todo
    public boolean addUser (User user){return true;}
    public boolean getMouthlyReport (String mouth){return true;}
    public boolean addNewState (String stateName){return true;}
    public boolean getState (String stateName){return true;}
    public boolean deleteState (String stateName){return true;}
    public boolean findState (String stateID){return true;}
    @RequestMapping("test")
    public void test( HttpServletRequest req, HttpServletResponse res) throws Exception {
    }
    @RequestMapping("neighbor")
    public void neighbor( HttpServletRequest req, HttpServletResponse res) throws Exception {
        ArrayList<ArrayList<String>> list = new LoadNHData().loadNeighbors();
        ArrayList<String> pList = list.get(0);
        ArrayList<String> nList = list.get(1);
        System.out.println(pList.size());
        System.out.println(nList.size());
        System.out.println(pList.get(0));
        System.out.println(nList.get(0));
        Map<String,String> map = new HashMap<String,String>();
        for (int i = 0; i < pList.size(); i++) {
                String str = nList.get(i);
                String[] strs=str.split(",");
                for (String string : strs) {
                    rsService.saveNeighbors(pList.get(i),string);
                }
        }
        res.getWriter().print(new Gson().toJson("ok"));
        
    }
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
