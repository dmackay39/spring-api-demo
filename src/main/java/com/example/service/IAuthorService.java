package com.example.service;

import com.example.model.Author;

import java.util.List;

public interface IAuthorService {
    List<Author> findAll();
    List<Author> findByNameContains(String filter);
    List<Author> findAllScreenWriters();
    Author findById(Long id);
    Author createAuthor(Author author);
    Author updateAuthor(Author author);
    void deleteAuthor(Long id);
}
