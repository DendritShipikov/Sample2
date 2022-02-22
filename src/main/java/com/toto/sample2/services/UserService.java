package com.toto.sample2.services;

import com.toto.sample2.repositories.UserRepository;
import com.toto.sample2.dto.UserData;
import com.toto.sample2.dto.LoginData;
import com.toto.sample2.entities.User;
import com.toto.sample2.mapper.Mapper;
import com.toto.sample2.exceptions.WrongLoginException;
import com.toto.sample2.exceptions.UserAlreadyExistsException;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Service
public class UserService {

    static private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private Mapper<User, UserData> userMapper;

    private JwtService jwtService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) { this.userRepository = userRepository; }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) { this.passwordEncoder = passwordEncoder; }

    @Autowired
    public void setUserMapper(Mapper<User, UserData> userMapper) { this.userMapper = userMapper; }

    @Autowired
    public void setJwtService(JwtService jwtService) { this.jwtService = jwtService; }
    
    @Transactional
    public String register(UserData userData) throws UserAlreadyExistsException {
        if (userRepository.findByUsername(userData.getUsername()) != null) {
            LOGGER.info("User '" + userData.getUsername() + "' is alredy exists");
            throw new UserAlreadyExistsException();
        }
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        User user = userMapper.toEntity(userData);
        userRepository.save(user);
        LOGGER.info("user '" + userData.getUsername() + "' registration");
        return jwtService.generateToken(userData.getUsername());
    }

    @Transactional
    public String login(LoginData loginData) throws WrongLoginException {
        User user = userRepository.findByUsername(loginData.getUsername());
        if (user == null || !passwordEncoder.matches(loginData.getPassword(), user.getPassword())) {
            LOGGER.info("Wrong login or password, username = " + loginData.getUsername());
            throw new WrongLoginException();
        }
        LOGGER.info("user '" + loginData.getUsername() + "' login");
        return jwtService.generateToken(loginData.getUsername());
    }

}