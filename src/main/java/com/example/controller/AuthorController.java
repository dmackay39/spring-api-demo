package com.example.controller;

import com.example.model.Author;
import com.example.service.IAuthorService;
import jakarta.websocket.server.PathParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {
    private final IAuthorService authorService;

    public AuthorController(IAuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/author")
    public Author createAuthor(@RequestBody Author author){
        return authorService.createAuthor(author);
    }

    @GetMapping("/authors")
    public List<Author> getAllAuthors(@PathParam("filter") String filter){
        List<Author> authors;
        if (StringUtils.isNotBlank(filter)){
            authors = authorService.findByNameContains(filter);
        } else {
            authors = authorService.findAll();
        }
        return authors;
    }

    @GetMapping("/screenwriters")
    public List<Author> getScreenWriters(){
        return authorService.findAllScreenWriters();
    }

    @GetMapping("/author/{id}")
    public Author getAuthorById(@PathVariable Long id){
        return authorService.findById(id);
    }

    @PutMapping("/author")
    public Author updateAuthor(@RequestBody Author author){
        return authorService.updateAuthor(author);
    }

    @DeleteMapping("/author/{id}")
    public void deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
    }

}
