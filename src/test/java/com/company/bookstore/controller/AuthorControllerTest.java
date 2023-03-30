package com.company.bookstore.controller;

import com.company.bookstore.models.Author;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorController.class)

public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    private List<Author> authorList;

    @Before
    public void setUp() {

    }

    @Test
    public void shouldReturnAllAuthorsInCollection() throws Exception {
        mockMvc.perform(get("/authors"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnAuthorById() throws Exception {
        // Initialize an author object with a known ID
        Author author = new Author();
        //author.setId(1);
        author.setFirstName("Rick");
        author.setLastName("RickRiordan");

        String outputJson = mapper.writeValueAsString(author);

        mockMvc.perform(get("/authors/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldCreateNewAuthor() throws Exception {
        Author inputAuthor = new Author();
        inputAuthor.setFirstName("Edgar");
        inputAuthor.setLastName("Allan Poe");

        String inputJson = mapper.writeValueAsString(inputAuthor);

        Author outputAuthor = new Author();
        //outputAuthor.setId(2L);
        outputAuthor.setFirstName("Edgar");
        outputAuthor.setFirstName("Allan Poe");

        String outputJson = mapper.writeValueAsString(outputAuthor);

        mockMvc.perform(
                        post("/authors")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldUpdateAuthorById() throws Exception {
        Author inputAuthor = new Author();
        inputAuthor.setFirstName("Rick");
        inputAuthor.setLastName("Riordan");

        String inputJson = mapper.writeValueAsString(inputAuthor);

        mockMvc.perform(
                        put("/authors/1")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteAuthorById() throws Exception {
        mockMvc.perform(delete("/authors/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
