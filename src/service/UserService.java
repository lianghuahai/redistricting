package service;

import dao.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pojo.User;
import utils.MD5Util;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User login(User user) {
        User currUser = userMapper.queryUserByEmail(user.getEmail());
        String encrptPW = MD5Util.Encrypt(user.getPassword());

        if (null != currUser) {
            if (encrptPW.equals(currUser.getPassword())) {
                return currUser;
            }
        }
        return null;
    }

    public User register(User user) {
        User currUser = userMapper.saveUser(user);
        return currUser;
    }

}
