package com.example.service;

import com.example.model.Book;
import com.example.repository.ILendableMaterialRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements IBookService{

    private final ILendableMaterialRepository lendableMaterialRepository;

    public BookServiceImpl(ILendableMaterialRepository lendableMaterialRepository) {
        this.lendableMaterialRepository = lendableMaterialRepository;
    }

    @Override
    public List<Book> findAll() {
        Iterable<Book> bookItr = lendableMaterialRepository.findAllBooks();
        return convertToList(bookItr);
    }

    @Override
    public List<Book> findByTitleContains(String filter) {
        Iterable<Book> bookItr = lendableMaterialRepository.findBooksByTitleContains(filter);
        return convertToList(bookItr);
    }

    private List<Book> convertToList(Iterable<Book> bookItr){
        List<Book> books = new ArrayList<>();
        bookItr.forEach(books::add);
        return books;
    }
}
