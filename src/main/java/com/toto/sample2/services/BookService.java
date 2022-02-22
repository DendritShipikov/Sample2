package com.toto.sample2.services;

import com.toto.sample2.entities.Book;
import com.toto.sample2.entities.User;
import com.toto.sample2.repositories.BookRepository;
import com.toto.sample2.repositories.UserRepository;
import com.toto.sample2.dto.BookData;
import com.toto.sample2.dto.UserData;
import com.toto.sample2.mapper.Mapper;
import com.toto.sample2.exceptions.UserHasNoAccessException;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.ArrayList;

@Service
public class BookService {

    private BookRepository bookRepository;

    private UserRepository userRepository;

    private Mapper<Book, BookData> bookMapper;
    
    @Autowired
    public void setBookRepository(BookRepository bookRepository) { this.bookRepository = bookRepository; }

    @Autowired
    public void setUserRepository(UserRepository userRepository) { this.userRepository = userRepository; }
    
    @Autowired
    public void setBookMapper(Mapper<Book, BookData> bookMapper) { this.bookMapper = bookMapper; }

    @Transactional
    public List<BookData> findAll() {
        List<Book> books = bookRepository.findAll();
        List<BookData> bookDatas = bookMapper.toDatas(books);
        return bookDatas;
    }


    @Transactional
    public void save(BookData bookData) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserData userData = (UserData)principal;
        bookData.setUserId(userData.getId());
        Book book = bookMapper.toEntity(bookData);
        bookRepository.save(book);
    }

    @Transactional
    public void edit(BookData bookData) throws UserHasNoAccessException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserData userData = (UserData)principal;
        if (bookRepository.getById(bookData.getId()).getUser().getId() != userData.getId()) {
            throw new UserHasNoAccessException();
        }
        Book book = bookMapper.toEntity(bookData);
        bookRepository.save(book);
    }

    @Transactional
    public BookData getById(Long id) {
        Book book = bookRepository.getById(id);
        BookData bookData = bookMapper.toData(book);
        return bookData;
    }

    @Transactional
    public List<BookData> getByUser(Long userId) {
        User user = userRepository.getById(userId);
        List<Book> books = bookRepository.findByUser(user);
        List<BookData> bookDatas = bookMapper.toDatas(books);
        return bookDatas;
    }

    @Transactional
    public void buy(List<Long> ids) {
        List<Book> books = bookRepository.findAllById(ids);
        for (Book book : books) {
            book.setAmount(book.getAmount() - 1);
        }
        bookRepository.saveAll(books);
    }
    
    @Transactional
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

}