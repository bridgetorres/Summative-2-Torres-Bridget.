package com.company.bookstore.controller;
import com.company.bookstore.repository.AuthorRepository;
import com.company.bookstore.models.Author;
import com.company.bookstore.models.Book;
import com.company.bookstore.models.Publisher;
import com.company.bookstore.repository.BookRespository;
import com.company.bookstore.repository.PublisherRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GraphController {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRespository bookRespository;

    @Autowired
    PublisherRespository publisherRespository;

    @QueryMapping
    public Author findAuthorById(@Argument int id) {
        return authorRepository.findById(id).orElse(null);

    }

    public Book findBookById(@Argument int id) {
        return bookRespository.findById(id).orElse(null);

    }

    public Publisher findPublisherById(@Argument int id) {
        return publisherRespository.findById(id).orElse(null);

    }

}

