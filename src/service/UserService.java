package service;

import dao.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pojo.User;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User login(User user) {
        // TODO Auto-generated method stub
        User currUser = userMapper.queryUserByEmail(user.getEmail());
        return currUser;
    }
    

}
