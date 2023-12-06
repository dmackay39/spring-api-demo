package com.example.repository;


import com.example.model.Book;
import com.example.model.LendableMaterial;
import com.example.model.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILendableMaterialRepository extends CrudRepository<LendableMaterial, Long> {
    @Query("SELECT b FROM Book b")
    List<Book> findAllBooks();

    @Query("SELECT b FROM Book b WHERE b.title LIKE %:filter%")
    List<Book> findBooksByTitleContains(@Param("filter") String filter);

    @Query("SELECT m FROM Movie m")
    List<Movie> findAllMovies();

    @Query("SELECT m FROM Movie m WHERE m.title LIKE %:filter%")
    List<Movie> findMoviesByTitleContains(@Param("filter") String filter);

}
