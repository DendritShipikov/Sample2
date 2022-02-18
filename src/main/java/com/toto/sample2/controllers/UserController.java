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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ui.Model;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    private BookService bookService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    public void setUserService(UserService userService) { this.userService = userService; }

    @Autowired
    public void setBookService(BookService bookService) { this.bookService = bookService; }

    @Autowired
    public void setJwtService(JwtService jwtService) { this.jwtService = jwtService; }
    
    @PostMapping("/register")
    public String register(@RequestBody UserData userData) {
        if (userService.register(userData)) return "OK";
        return "Such username is already exists";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginData loginData) {
        if (!userService.login(loginData)) {
            return "Wrong password and username";
        }
        return jwtService.generateToken(loginData.getUsername());
    }

    @GetMapping("/books")
    public List<BookData> myBooksGet() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserData userData = (UserData)principal;
        List<BookData> bookDatas = bookService.getByUser(userData.getId());
        return bookDatas;
    }
    
}