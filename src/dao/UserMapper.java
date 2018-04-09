package dao;


import pojo.User;

public interface UserMapper {
    public User queryUserByEmail(String email);

}
