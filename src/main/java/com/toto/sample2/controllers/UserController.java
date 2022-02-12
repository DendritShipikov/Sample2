package com.toto.sample2.controllers;

import com.toto.sample2.services.UserService;
import com.toto.sample2.services.BookService;
import com.toto.sample2.services.JwtService;
import com.toto.sample2.dto.UserData;
import com.toto.sample2.dto.BookData;
import com.toto.sample2.dto.LoginData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private JwtService jwtService;
    
    @PostMapping("/api/register")
    public String register(@RequestBody UserData userData) {
        if (userService.register(userData)) return "OK";
        return "Such username is already exists";
    }

    @PostMapping("/api/login")
    public String login(@RequestBody LoginData loginData) {
        if (!userService.login(loginData)) {
            return "Wrong password and username";
        }
        return jwtService.generateToken(loginData.getUsername());
    }

    @PostMapping("/api/buy")
    public String buyGet(@RequestBody List<Long> ids) {
        bookService.buy(ids);
        return "OK";
    }
    
}