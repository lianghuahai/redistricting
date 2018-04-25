package controller;

import java.io.IOException;
import java.util.List;

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
        System.out.println("ValidateEmail");
        boolean qualifiedEmail = rsService.validateEmail(email);
        res.getWriter().print(new Gson().toJson(qualifiedEmail));
    }
    @RequestMapping("getUsers")
    public void getUsers(HttpServletRequest req, HttpServletResponse res) throws IOException{
        System.out.println("getUsers");
        List<User> users = rsService.getUsers();
        res.getWriter().print(new Gson().toJson(users));
    }
    @RequestMapping("deleteUserByEmail")
    public void deleteUserByEmail(String email,HttpServletRequest req, HttpServletResponse res) throws IOException{
        System.out.println("deleteUserByEmail");
        List<User> qualifiedEmail = rsService.getUsers();
        res.getWriter().print(new Gson().toJson(qualifiedEmail));
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
    //Todo
    public boolean addUser (User user){return true;}
    public boolean getMouthlyReport (String mouth){return true;}
    public boolean addNewState (String stateName){return true;}
    public boolean getState (String stateName){return true;}
    public boolean deleteState (String stateName){return true;}
    public boolean findState (String stateID){return true;}
    @RequestMapping("logintest")
    public void logintest( HttpServletRequest req, HttpServletResponse res) throws IOException {
            User user = new User();
            user.setEmail("nihao");
            Gson gson = new Gson();
            req.getSession().setAttribute("user", gson.toJson(user));
            res.getWriter().print(gson.toJson(user));
    }
    @RequestMapping("testlogin")
    public void testlogin( HttpServletRequest req, HttpServletResponse res) throws IOException {
            String jsonUser = (String) req.getSession().getAttribute("user");
            Gson gson = new Gson();
            User user = gson.fromJson(jsonUser, User.class);
            System.out.println(user.getEmail());
    }
}
