package com.toto.sample2.services;

import com.toto.sample2.repositories.UserRepository;
import com.toto.sample2.dto.UserData;
import com.toto.sample2.entities.User;
import com.toto.sample2.mapper.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    private Mapper<User, UserData> userMapper;
    
    @Autowired
    public void setUserRepository(UserRepository userRepository) { this.userRepository = userRepository; }

    @Autowired
    public void setUserMapper(Mapper<User, UserData> userMapper) { this.userMapper = userMapper; }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("no username " + username);
        UserData userData = userMapper.toData(user);
        return userData;
    }

}