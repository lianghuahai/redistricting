package controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import pojo.State;
import pojo.User;
import service.RSService;

//@RequestMapping("/user")
@Controller
public class RSController {
    @Autowired
    private RSService rsService;

    @RequestMapping("login")
    public void login(User user, HttpServletRequest req, HttpServletResponse res) throws IOException {
        User existUser = rsService.login(user);
        if (null == existUser) {
            res.getWriter().print(0);
        } else {
            res.getWriter().print(1);
        }
    }

    @RequestMapping("register")
    public void register(User user, HttpServletRequest req, HttpServletResponse res) throws IOException {
        User existUser = rsService.register(user);
        if (null == existUser) {
            res.getWriter().print(0);
        } else {
            res.getWriter().print(1);
        }
    }
    
    @RequestMapping("redistrict")
    public void redistrict(String jsonData,HttpServletRequest req, HttpServletResponse res) throws IOException{
        State originalState =  new State();//???
        State workingState = originalState.clone();
        workingState.redistrict();
        res.getWriter().print(new Gson().toJson(workingState));
    }
    
    
    
    
    public boolean addUser (User user){return true;}
    public boolean validateUser (User user){return true;}
    public boolean getMouthlyReport (String mouth){return true;}
    public boolean login (User user){return true;}
    public boolean validateEmail (String email){return true;}
    public boolean register(User user){return true;}
    public boolean login (String email, String pw){return true;}
    public boolean addNewState (String stateName){return true;}
    public boolean getState (String stateName){return true;}
    public boolean deleteState (String stateName){return true;}
    public boolean findState (String stateID){return true;}
}
