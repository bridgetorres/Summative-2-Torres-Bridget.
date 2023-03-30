package com.company.bookstore.controller;

import com.company.bookstore.models.Author;
import com.company.bookstore.models.Book;
import com.company.bookstore.models.Publisher;
import com.company.bookstore.repository.AuthorRepository;
import com.company.bookstore.repository.BookRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class AuthorController {


    @Autowired
    BookRespository bookRepository;


    @Autowired
    AuthorRepository authorRepository;



    //Use this example for creating or everything:
//    {
//        "firstName": "John",
//            "lastName": "Doe",
//            "street": "123 Main St",
//            "city": "Anytown",
//            "state": "CA",
//            "postalCode": "12345",
//            "phone": "555-123-4567",
//            "email": "johndoe@example.com"
//
//    }

    @PostMapping("/authors/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Author addAuthor(@RequestBody Author author,@PathVariable int bookId){
        // get book from the book table
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()){
            List<Book> books = new ArrayList<>();
            books.add(book.get());

            author.setBooks(books);
            return  authorRepository.save(author);
        } else {
            throw new RuntimeException();
        }

    }

    @PutMapping("/authors")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAuthors(@RequestBody Author author){
        authorRepository.save(author);
    }

    @DeleteMapping("/author/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable int id){
        authorRepository.deleteById(id);
    }

    @GetMapping("/authors/{id}")
    public Author findAuthorById(@PathVariable int id){

        Optional<Author> AuthorFromRepo = authorRepository.findById(id);
        if (AuthorFromRepo.isPresent()){
            return AuthorFromRepo.get();

        } else {
            return null;
        }
    }

    @GetMapping("/allauthors")
    public List<Author> findAllAuthors(){
        return authorRepository.findAll();
    }



}