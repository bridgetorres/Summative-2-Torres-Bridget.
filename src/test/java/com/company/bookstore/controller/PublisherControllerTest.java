package com.company.bookstore.controller;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PublisherController.class)

public class PublisherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    private List<Publisher> publisherList;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void shouldReturnAllPublishersInCollection() throws Exception {


        mockMvc.perform(get("/publishers"))                // Perform the GET request
                .andDo(print())                          // Print results to console
                .andExpect(status().isOk());              // ASSERT (status code is 200)
    }


    @Test
    public void shouldReturnNewPublisherOnPostRequest() throws Exception {

        // ARRANGE
        Publisher inputPublisher = new Publisher();
        inputPublisher.setName("Random House");

        // Convert Java Object to JSON
        String inputJson = mapper.writeValueAsString(inputPublisher);

        Publisher outputPublisher = new Publisher();
        outputPublisher.setId(1);
        outputPublisher.setName("Random House");

        String outputJson = mapper.writeValueAsString(outputPublisher);

        // ACT
        mockMvc.perform(
                        post("/publishers")                            // Perform the POST request
                                .content(inputJson)                       // Set the request body
                                .contentType(MediaType.APPLICATION_JSON)  // Tell the server it's in JSON format
                )
                .andDo(print())                                // Print results to console
                .andExpect(status().isCreated())              // ASSERT (status code is 201)
                .andExpect(content().json(outputJson));       // ASSERT (response body is correct)
    }

    @Test
    public void shouldReturnPublisherById() throws Exception {

        Publisher outputPublisher = new Publisher();
        outputPublisher.setId(1);
        outputPublisher.setName("Penguin Books");

        String outputJson = mapper.writeValueAsString(outputPublisher);

        mockMvc.perform(get("/publishers/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    // Testing PUT /publishers/{id}
    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {


        Publisher inputPublisher = new Publisher();
        inputPublisher.setId(1);
        inputPublisher.setName("Penguin Classics");

        String inputJson = mapper.writeValueAsString(inputPublisher);

        mockMvc.perform(
                        put("/publishers/1")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}



