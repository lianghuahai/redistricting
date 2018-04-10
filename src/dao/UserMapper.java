package dao;

import pojo.User;

public interface UserMapper {
    public User queryUserByEmail(String email);
    public User saveUser(User user);
}
