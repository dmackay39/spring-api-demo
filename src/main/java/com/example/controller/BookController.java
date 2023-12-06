package com.example.controller;

import com.example.model.Book;
import com.example.service.IBookService;
import jakarta.websocket.server.PathParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    private final IBookService bookService;

    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> getAllBooks(@PathParam("filter") String filter){
        List<Book> books;
        if (StringUtils.isNotBlank(filter)){
            books = bookService.findByTitleContains(filter);
        } else {
            books = bookService.findAll();
        }
        return books;
    }


}
