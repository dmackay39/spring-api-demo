package com.example.service;

import com.example.model.Movie;

import java.util.List;

public interface IMovieService {
    List<Movie> findAll();
    List<Movie> findByTitleContains(String filter);
}
