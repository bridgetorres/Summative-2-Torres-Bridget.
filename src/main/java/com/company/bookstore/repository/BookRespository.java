package com.company.bookstore.repository;
import com.company.bookstore.models.Author;
import com.company.bookstore.models.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public  interface BookRespository extends JpaRepository<Book, Integer>{
    List<Book> findAllByAuthor(Author author);

    List<Book> findByAuthorId(Integer authorId);
}

