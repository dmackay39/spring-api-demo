package com.example.controller;

import com.example.model.Author;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
@Sql("classpath:test-data.sql")
@Slf4j
public class AuthorControllerTests {

    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper;

    @BeforeEach
    public void setUp() throws JsonProcessingException {
        mapper = new ObjectMapper();
    }

    @Test
    public void testCreateAuthor() throws Exception {
        Author testAuthor = new Author();
        testAuthor.setName("Creator test");
        String authorAsString = mapper.writeValueAsString(testAuthor);

        ResultActions resultActions = postRequest("/author", authorAsString);
        String contentAsString = stringifyResult(resultActions);
        Author resultAuthor = mapper.readValue(contentAsString, Author.class);

        Assertions.assertEquals(testAuthor.getName(), resultAuthor.getName());
    }

    @Test
    public void testGetAllAuthors() throws Exception {
        int expectedNumber = 3;

        ResultActions resultActions = getRequest("/authors");
        MvcResult result = resultActions.andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        Author[] authors = mapper.readValue(contentAsString, Author[].class);

        Assertions.assertEquals(expectedNumber, authors.length);
    }

    @Test
    public void testGetAllAuthorsWithFilter() throws Exception{
        int expectedNumber = 1;
        String testFilter = "t";

        ResultActions resultActions = getRequest("/authors?filter=" + testFilter);
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Author[] authors = mapper.readValue(contentAsString, Author[].class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedNumber, authors.length),
                () -> Assertions.assertEquals(expectedNumber, Arrays.stream(authors).filter(c->c.getName().contains(testFilter)).toList().size())
        );

    }

    @Test
    public void testGetAllScreenWriters() throws Exception{
        int expectedNumber = 1;

        ResultActions resultActions = getRequest("/screenwriters");
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Author[] screenwriters = mapper.readValue(contentAsString, Author[].class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedNumber, screenwriters.length),
                () -> Assertions.assertEquals(expectedNumber, Arrays.stream(screenwriters).filter(c-> !c.getMovies().isEmpty()).toList().size()));
    }

    @Test
    public void testGetOneAuthor() throws Exception {
        int id = 10;
        String testName = "Test";

        ResultActions resultActions = getRequest("/author/"+id);
        String contentAsString = stringifyResult(resultActions);

        Author resultAuthor = mapper.readValue(contentAsString, Author.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(id, resultAuthor.getId()),
                () -> Assertions.assertEquals(testName, resultAuthor.getName())
        );
    }

    private ResultActions getRequest(String url) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private ResultActions postRequest(String url, String content) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String stringifyResult(ResultActions resultActions) throws UnsupportedEncodingException {
        MvcResult result = resultActions.andReturn();
        return result.getResponse().getContentAsString();
    }
}
