package com.example.repository;

import com.example.model.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAuthorRepository extends CrudRepository<Author, Long> {
    List<Author> findByNameContains(String filter);

    @Query("SELECT a FROM Author a WHERE SIZE(a.movies)>0 ORDER BY SIZE(a.movies)")
    List<Author> findAllScreenWriters();
}
