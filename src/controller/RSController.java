package controller;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pojo.CDistrict;
import pojo.Precinct;
import pojo.PrecinctProperty;
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
    public void displayState(String stateName,String dLevel,HttpServletRequest req, HttpServletResponse res) throws IOException, Exception{
        State displayState = rsService.initializeState(stateName);
        PrecinctJson mapJson = new LoadJsonData().getJsonData(stateName,dLevel);
        if(dLevel.equals("CD")){
            displayState.setUpCdMapJson(mapJson.getFeatures());
        }else{
            int colorCount=1;
            for (CDistrict cd : displayState.getCongressionalDistricts()) {
                cd.setUpPrecinctMapJson(mapJson.getFeatures(),colorCount);
                colorCount++;
            }
        }
        res.getWriter().print(new Gson().toJson(mapJson));
    }
    
    @RequestMapping("redistrict")
    public void redistrict(State originalState,HttpServletRequest req, HttpServletResponse res) throws IOException{
        State workingState = originalState.clone();
        workingState.startAlgorithm();
        res.getWriter().print(new Gson().toJson(workingState));
        req.getSession().setAttribute("flag",true);
        PrecinctProperty precinctProperty =  new PrecinctProperty();
        precinctProperty.setFill("#f3370f");
        precinctProperty.setVTDI10("A");
        res.getWriter().print(new Gson().toJson(precinctProperty));
    }
    
    @RequestMapping("process")
    public void process(State originalState,HttpServletRequest req, HttpServletResponse res) throws IOException{
//        State workingState = req.getSession().getAttribute("workingState");
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
    public void stop(State originalState,HttpServletRequest req, HttpServletResponse res) throws IOException{
      req.getSession().getAttribute("workingState");
      res.getWriter().print(new Gson().toJson("ok"));
  }
    
    
    //Todo
    public boolean addUser (User user){return true;}
    public boolean getMouthlyReport (String mouth){return true;}
    public boolean addNewState (String stateName){return true;}
    public boolean getState (String stateName){return true;}
    public boolean deleteState (String stateName){return true;}
    public boolean findState (String stateID){return true;}
   
  
    
    
}
