package com.toto.sample2.controllers;

import com.toto.sample2.services.UserService;
import com.toto.sample2.services.BookService;
import com.toto.sample2.dto.UserData;
import com.toto.sample2.dto.BookData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.Valid;

import java.util.List;

@RestController
public class BookController {

    private UserService userService;

    private BookService bookService;

    @Autowired
    public void setUserService(UserService userService) { this.userService = userService; }

    @Autowired
    public void setBookService(BookService bookService) { this.bookService = bookService; }

    @GetMapping("/api/books")
    public List<BookData> booksGet() {
        List<BookData> bookDatas = bookService.findAll();
        return bookDatas;
    }

    @PostMapping("/api/addbook")
    public String addBookPost(@RequestBody @Valid BookData bookData, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Error";
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserData userData = (UserData)principal;
        bookData.setUserId(userData.getId());
        bookService.save(bookData);
        return "OK";
    }

    @PutMapping("/api/editbook")
    public String editBookPost(@RequestBody @Valid BookData bookData, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Error";
        }
        bookService.save(bookData);
        return "OK";
    }

    @GetMapping("/api/viewbook/{id}")
    public BookData viewBookGet(@PathVariable Long id) {
        BookData bookData = bookService.getById(id);
        return bookData;
    }

    @GetMapping("/api/mybooks")
    public List<BookData> myBooksGet() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserData userData = (UserData)principal;
        List<BookData> bookDatas = bookService.getByUser(userData.getId());
        return bookDatas;
    }

    @DeleteMapping("/api/deletebook/{id}")
    public String deleteBookDelete(@PathVariable Long id) {
        bookService.delete(id);
        return "OK";
    } 
    
}