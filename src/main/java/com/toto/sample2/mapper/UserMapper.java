package com.toto.sample2.mapper;

import com.toto.sample2.entities.User;
import com.toto.sample2.dto.UserData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User, UserData> {

    @Override
    public User toEntity(UserData userData) {
        User user = new User();
        user.setId(userData.getId());
        user.setPassword(userData.getPassword());
        user.setUsername(userData.getUsername());
        user.setRole(userData.getRole());
        return user;
    }

    @Override
    public UserData toData(User user) {
        UserData userData = new UserData();
        userData.setId(user.getId());
        userData.setPassword(user.getPassword());
        userData.setUsername(user.getUsername());
        userData.setRole(user.getRole());
        return userData;
    }
    
}