package com.example.service;

import com.example.model.Author;
import com.example.repository.IAuthorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceImpl implements IAuthorService{

    private final IAuthorRepository authorRepository;

    public AuthorServiceImpl(IAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    private List<Author> convertToList(Iterable<Author> authorItr){
        List<Author> authors = new ArrayList<>();
        authorItr.forEach(authors::add);
        return authors;
    }

    @Override
    public List<Author> findAll() {
        Iterable<Author> authorItr = authorRepository.findAll();
        return convertToList(authorItr);
    }

    @Override
    public List<Author> findByNameContains(String filter) {
        Iterable<Author> authorItr = authorRepository.findByNameContains(filter);
        return convertToList(authorItr);
    }

    @Override
    public List<Author> findAllScreenWriters() {
        return authorRepository.findAllScreenWriters();
    }

    @Override
    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow();
    }

    @Override
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }


}
