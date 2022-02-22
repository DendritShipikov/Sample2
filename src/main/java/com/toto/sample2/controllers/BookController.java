package com.toto.sample2.controllers;

import com.toto.sample2.services.UserService;
import com.toto.sample2.services.BookService;
import com.toto.sample2.dto.UserData;
import com.toto.sample2.dto.BookData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.security.core.context.SecurityContextHolder;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    static private final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    private UserService userService;

    private BookService bookService;

    @Autowired
    public void setUserService(UserService userService) { this.userService = userService; }

    @Autowired
    public void setBookService(BookService bookService) { this.bookService = bookService; }

    @GetMapping
    public List<BookData> booksGet() {
        LOGGER.info("/api/book GET request");
        List<BookData> bookDatas = bookService.findAll();
        return bookDatas;
    }

    @PostMapping
    public void addBookPost(@RequestBody @Valid BookData bookData) {
        LOGGER.info("/api/book POST request");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserData userData = (UserData)principal;
        bookData.setUserId(userData.getId());
        bookService.save(bookData);
    }

    @PutMapping
    public void editBookPost(@RequestBody @Valid BookData bookData) {
        LOGGER.info("/api/book PUT request");
        bookService.save(bookData);
    }
    
    @GetMapping("/{id}")
    public BookData viewBookGet(@PathVariable Long id) {
        LOGGER.info(String.format("/api/book/%d GET request", id));
        BookData bookData = bookService.getById(id);
        return bookData;
    }

    @PostMapping("/buy")
    public void buyGet(@RequestBody List<Long> ids) {
        LOGGER.info("/api/book/buy POST request");
        bookService.buy(ids);
    }

    @DeleteMapping("/{id}")
    public void deleteBookDelete(@PathVariable Long id) {
        LOGGER.info(String.format("/api/book/%d DELETE request", id));
        bookService.delete(id);
    } 
    
}