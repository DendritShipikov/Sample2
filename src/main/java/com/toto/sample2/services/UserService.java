package com.toto.sample2.services;

import com.toto.sample2.repositories.UserRepository;
import com.toto.sample2.dto.UserData;
import com.toto.sample2.dto.LoginData;
import com.toto.sample2.entities.User;
import com.toto.sample2.mapper.Mapper;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private Mapper<User, UserData> userMapper;

    @Autowired
    public void setUserRepository(UserRepository userRepository) { this.userRepository = userRepository; }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) { this.passwordEncoder = passwordEncoder; }

    @Autowired
    public void setUserMapper(Mapper<User, UserData> userMapper) { this.userMapper = userMapper; }
    
    @Transactional
    public boolean register(UserData userData) {
        if (userRepository.findByUsername(userData.getUsername()) != null) return false;
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        User user = userMapper.toEntity(userData);
        userRepository.save(user);
        return true;
    }

    @Transactional
    public boolean login(LoginData loginData) {
        User user = userRepository.findByUsername(loginData.getUsername());
        if (user == null) return false;
        return passwordEncoder.matches(loginData.getPassword(), user.getPassword());
    }

}