package com.toto.sample2.repositories;

import com.toto.sample2.entities.Book;
import com.toto.sample2.entities.User;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByUser(User user);

}