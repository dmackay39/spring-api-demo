package com.example.controller;

import com.example.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
@Sql("classpath:test-data.sql")
@Slf4j
public class BookControllerTests {
    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper;

    @BeforeEach
    public void setUp(){
        mapper = new ObjectMapper();
    }

    @Test
    public void getAllBooks() throws Exception {
        int expectedNumber = 1;

        ResultActions resultActions = getRequest("/books");
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        log.info(contentAsString);

        Book[] books = mapper.readValue(contentAsString, Book[].class);
        Assertions.assertEquals(expectedNumber, books.length);
    }

    @Test
    public void getAllBooksWithTitle() throws Exception {
        int expectedNumber = 1;

        ResultActions resultActions = getRequest("/books?filter=t");
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        log.info(contentAsString);

        Book[] books = mapper.readValue(contentAsString, Book[].class);

        Assertions.assertEquals(expectedNumber, books.length);
        Assertions.assertEquals(expectedNumber, Arrays.stream(books).filter(c->c.getTitle().contains("t")).toList().size());
    }

    private ResultActions getRequest(String url) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
