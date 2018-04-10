package controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pojo.User;
import service.UserService;

//@RequestMapping("/user")
@Controller
public class userController {
    @Autowired
    private UserService userService;

    @RequestMapping("login")
    public void login(User user, HttpServletRequest req, HttpServletResponse res) throws IOException {
        User existUser = userService.login(user);
        if (null == existUser) {
            res.getWriter().print(0);
        }else{
            res.getWriter().print(1);
        }
    }
    
    
    @RequestMapping("register")
    public void register(User user, HttpServletRequest req, HttpServletResponse res) throws IOException {
        User existUser = userService.register(user);
        if (null == existUser) {
            res.getWriter().print(0);
        }else{
            res.getWriter().print(1);
        }
    }
    
    
    
    
    
    
}
