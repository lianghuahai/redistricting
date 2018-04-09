package controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pojo.User;
import service.UserService;

//@RequestMapping("/items")
@Controller
public class userController {
    @Autowired
    private UserService userService;

    @Test
    @RequestMapping("login")
    public void login(User user, HttpServletRequest req, HttpServletResponse res) throws IOException {
        System.out.println("coming");
        System.out.println(user);
        User cu = userService.login(user);
        if (null == cu) {
            res.getWriter().print(0);
            System.out.println("0");
        }else{
            res.getWriter().print(1);
            System.out.println("1");
        }
    }
}
