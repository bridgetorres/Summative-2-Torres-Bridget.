package com.company.bookstore.controller;

import com.company.bookstore.models.Author;
import com.company.bookstore.models.Book;
import com.company.bookstore.models.Publisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)

public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    private List<Book> bookList;

    @Before
    public void setUp() throws Exception {
        // Initialize your bookList here
    }

    @Test
    public void shouldReturnAllBooksInCollection() throws Exception {

        // ARRANGE

        // ACT
        mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookList)));
    }

    @Test
    public void shouldReturnNewBookOnPostRequest() throws Exception {

        // ARRANGE
        Book inputBook = new Book();
        inputBook.setIsbn("563323");
        inputBook.setPublishDate("1990");
        inputBook.setTitle("1984");
        inputBook.setAuthor(new Author());
        inputBook.setPublisher(new Publisher());
        inputBook.setId(0);

        // Convert Java Object to JSON
        String inputJson = mapper.writeValueAsString(inputBook);

        // ACT
        mockMvc.perform(post("/books")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(inputJson));
    }

    @Test
    public void shouldReturnRecordById() throws Exception {

        // ARRANGE
        Book outputBook = new Book();
        outputBook.setAuthor(new Author());
        outputBook.setTitle("The Stranger");
        outputBook.setPublishDate("1977");
        outputBook.setId(2);

        // Convert Java Object to JSON
        String outputJson = mapper.writeValueAsString(outputBook);

        // ACT
        mockMvc.perform(get("/books/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {

        // ARRANGE
        Book inputBook = new Book();
        inputBook.setAuthor(new Author());
        inputBook.setTitle("The Stranger");
        inputBook.setPublishDate("1977");
        inputBook.setId(2);

        // Convert Java Object to JSON
        String inputJson = mapper.writeValueAsString(inputBook);

        // ACT
        mockMvc.perform(put("/books/2")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {

        // ACT
        mockMvc.perform(delete("/books/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}