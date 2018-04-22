package controller;

import java.io.IOException;

import javassist.expr.NewArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import pojo.State;
import pojo.User;
import service.RSService;

@Controller
public class RSController {
    @Autowired
    private RSService rsService;

    @RequestMapping("login")
    public void login(User user, HttpServletRequest req, HttpServletResponse res) throws IOException {
        User existUser = rsService.login(user);
        Gson gson = new Gson();
        if (null == existUser) {
            res.getWriter().print(gson.toJson("null"));
        } else {
            res.getWriter().print(gson.toJson(existUser.getRole()));
        }
    }

    @RequestMapping("register")
    public void register(User user, HttpServletRequest req, HttpServletResponse res) throws IOException {
        User existUser = rsService.register(user);
        Gson gson = new Gson();
        if (null == existUser) {
            res.getWriter().print(gson.toJson("false"));
        } else {
            res.getWriter().print(gson.toJson("true"));
        }
    }
    @RequestMapping("displayState")
    public void displayState(String stateName,HttpServletRequest req, HttpServletResponse res) throws IOException{
        State displayState = rsService.getStateByName();
        res.getWriter().print(new Gson().toJson(displayState));
    }
    
    @RequestMapping("redistrict")
    public void redistrict(State originalState,HttpServletRequest req, HttpServletResponse res) throws IOException{
        State workingState = originalState.clone();
        workingState.startAlgorithm();
        res.getWriter().print(new Gson().toJson(workingState));
    }
    
    
    
    public boolean addUser (User user){return true;}
    public boolean validateUser (User user){return true;}
    public boolean getMouthlyReport (String mouth){return true;}
    public boolean login (User user){return true;}
    public boolean register(User user){return true;}
    public boolean login (String email, String pw){return true;}
    public boolean addNewState (String stateName){return true;}
    public boolean getState (String stateName){return true;}
    public boolean deleteState (String stateName){return true;}
    public boolean findState (String stateID){return true;}
    @RequestMapping("validateEmail")
    public void validateEmail(String email,HttpServletRequest req, HttpServletResponse res) throws IOException{
        boolean qualifiedEmail = rsService.validateEmail(email);
        res.getWriter().print(qualifiedEmail);
    }
}
